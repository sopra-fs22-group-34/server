package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Move;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Lobby Service
 * This class is the "worker" and responsible for all functionality related to
 * the lobby
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */

@Service
@Transactional
public class LobbyService {

    private final Logger log = LoggerFactory.getLogger(LobbyService.class);

    private final LobbyRepository lobbyRepository;

    private final UserRepository userRepository;

    @Autowired
    public LobbyService(@Qualifier("lobbyRepository") LobbyRepository lobbyRepository,
                        @Qualifier("userRepository") UserRepository userRepository) {
        this.lobbyRepository = lobbyRepository;
        this.userRepository = userRepository;
    }

    public List<Lobby> getLobbies() {
        return this.lobbyRepository.findAll();
    }

    public Lobby getLobbyById(Long id) {
        Lobby lobbyById = lobbyRepository.findLobbyById(id);
        if (lobbyById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Lobby with this ID exists.");
        }
        return lobbyById;
    }

    public Lobby getLobbyByLobbyName(String lobbyName) {
        Lobby lobbyByLobbyName = lobbyRepository.findLobbyByName(lobbyName);
        if (lobbyByLobbyName == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No lobby with this lobbyname exists.");
        }
        return lobbyByLobbyName;
    }

    public Lobby createLobby(Lobby newLobby) {
        new Lobby();
        User host = userRepository.findUserById(newLobby.getHost_id());
        newLobby.setIs_open(true);
        newLobby.setIs_public(newLobby.getIs_public());
        if (newLobby.getIs_public() == false) {
            newLobby.setSecret_url(generate_URL());
        }
        newLobby.setCurrent_players(1L);
        newLobby.addPlayer(newLobby.getHost_id());
        newLobby.setHost_name(host.getUsername());
        newLobby = this.lobbyRepository.save(newLobby);
        lobbyRepository.flush();
        host.setLobby(newLobby.getId());
        log.debug("Created Information for User: {}", newLobby);
        return newLobby;
    }

    public String generate_URL() {
        Random random = new Random();
        String characterPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String URL = "";

        for (int i = 0; i < 8; i++) {
            int randomInt = random.nextInt(62);
            URL += characterPool.charAt(randomInt);
        }

        return URL;
    }

    public boolean isInThisLobby(Long lobbyId, Long id){
        User player = userRepository.findUserById(id);
        return player.getLobby() != null && player.getLobby().equals(lobbyId);
    }

    public boolean isInAnyLobby(Long id){
        List<Lobby> allLobbies = getLobbies();
        for (Lobby lobby: allLobbies) {
            if (isInThisLobby(lobby.getId(), id)) { return true; }
        }
        return false;
    }
    
    public boolean isUserInGame(Long id){
        Lobby lobby = getLobbyOfUser(id);
        // return true if the user is in a lobby, the lobby is closed, and the user didn't leave
        return lobby != null && !lobby.getIs_open() &&  userRepository.findUserById(id).getLobby() != null && userRepository.findUserById(id).getLobby() != 0L;
    }

    public Lobby getLobbyOfUser(Long id){
        List<Lobby> allLobbies = getLobbies();
        for (Lobby lobby: allLobbies) {
            if (isInThisLobby(lobby.getId(), id)) { return lobby; }
        }
        return null;
    }

    public JSONObject getGameOfLobby(Long id) {
        Lobby lobby = getLobbyById(id);
        if (lobby.getGame() == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no game running");
        }
        return lobby.getGame().jsonify().put("lobbyData", getLobbyData(id));
    }

    public void joinLobby(Long lobbyId, Long id){
        User user = userRepository.findUserById(id);
        Lobby lobby = getLobbyById(lobbyId);
        if (isInAnyLobby(id) && !isInThisLobby(lobbyId, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You are already in a different Lobby");
        } else if (!isInThisLobby(lobbyId, id)) {
            user.setLobby(lobbyId);
            lobby.addPlayer(id); //add User into players
            lobby.setCurrent_players(lobby.getCurrent_players()+1); //adjust player count
            updateLobby(lobby);
            this.lobbyRepository.flush();
        }
    }

    public void updateLobby(Lobby lobby){
        if (lobby.getCurrent_players().equals(lobby.getTotal_players())) {startGame(lobby.getId());}
    }

    public void deleteLobby(Long lobbyId){
        Lobby lobby = getLobbyById(lobbyId);
        for (Long playerId: lobby.getPlayers()) {
            resetPlayer(playerId);
        }
        if (!lobby.getIs_open()) rewardPlayers(lobby);
        lobbyRepository.deleteById(lobbyId);
        this.lobbyRepository.flush();
    }

    public void playerLeaves(Lobby lobby, Long id){
        if (lobby.getPlayers().contains(id)) {
            resetPlayer(id);
            lobby.removePlayer(id);
            lobby.setCurrent_players(lobby.getCurrent_players() - 1); //adjust player count
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player is not in this lobby.");
        }
    }

    public void resetPlayer(Long id){
        User player = userRepository.findUserById(id);
        player.setLobby(0L);
    }

    public void leaveLobby(Long lobbyId, String id){
        Lobby lobby = getLobbyById(lobbyId);
        try {
            if (lobby.getHost_id() == Long.parseLong(id)){ deleteLobby(lobbyId); } // delete the lobby if the host leaves
            else { playerLeaves(lobby, Long.parseLong(id)); }// remove player from the list of joined players
        } catch (NumberFormatException notID) { // if input cannot be converted into an ID, it must be a username
            Long newId = userRepository.findByUsername(id).getId(); // generate ID from the username
            if (lobby.getHost_id().equals(newId)){ deleteLobby(lobbyId); }
            else { playerLeaves(lobby, newId); }
        }
    }

    public void kickUserFromLobby(Long lobbyId, Long hostId, Long userToKickId){
        Lobby lobby = getLobbyById(lobbyId);
        if (lobby.getHost_id().equals(hostId) && isInThisLobby(lobbyId, userToKickId)){
            playerLeaves(lobby, userToKickId); }
        else if (!lobby.getHost_id().equals(hostId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Only the host can kick players. Make sure you are the host of the lobby before trying to kick someone."); }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Player to kick with this Id exists."); }
    }

    public void updatePrivacy(Long lobbyId,  boolean privacy){
        Lobby lobby = getLobbyById(lobbyId);
        lobby.setIs_public(privacy);
    }

    public void changeLobbyName(Long lobbyId, String name){
        Lobby lobby = getLobbyById(lobbyId);
        lobby.setName(name);
    }

    public void changeLobbySize(Long lobbyId, Long size){
        Lobby lobby = getLobbyById(lobbyId);
        if (lobby.getCurrent_players() < size){
            lobby.setTotal_players(size);
            updateLobby(lobby);
            this.lobbyRepository.flush();
        }
        else {throw new ResponseStatusException(HttpStatus.CONFLICT, "You can't make the lobby smaller since all slots are full!");}
    }

    public String getPlayerUsername(Long id, int playerIndex){
        Lobby lobby = getLobbyById(id);
        return userRepository.findUserById(lobby.getPlayers().get(playerIndex)).getUsername();
    }

    public String getSpectatorUsername(Long id, int spectatorIndex){
        Lobby lobby = getLobbyById(id);
        return userRepository.findUserById(lobby.getSpectators().get(spectatorIndex)).getUsername();
    }

    public void spectateGame(Long lobbyId, Long id) {
        Lobby lobby = getLobbyById(lobbyId);
        User spectator = userRepository.findUserById(id);
        spectator.setLobby(lobbyId);
        if (!lobby.getSpectators().contains(id)) lobby.addSpectator(id);
    }

    public void stopSpectating(Long lobbyId, Long id) {
        Lobby lobby = getLobbyById(lobbyId);
        lobby.removeSpectator(id);
        resetPlayer(id);
    }

    public JSONObject getLobbyData(Long id){
        Lobby lobby = getLobbyById(id);
        JSONObject json = new JSONObject();
        json.put("current_players", lobby.getCurrent_players());
        json.put("current_spectators", lobby.getSpectators().size());
        json.put("timer", lobby.getTimer());
        JSONArray playersArray = new JSONArray();
        for (int i = 0; i < lobby.getPlayers().size(); i++) {
            JSONObject player = new JSONObject();
            player.put("id", lobby.getPlayers().get(i));
            player.put("name", getPlayerUsername(id, i));
            playersArray.put(player);
        }
        json.put("players", playersArray);
        JSONArray spectatorsArray = new JSONArray();
        for (int i = 0; i < lobby.getSpectators().size(); i++) {
            JSONObject spectator = new JSONObject();
            spectator.put("id", lobby.getSpectators().get(i));
            spectator.put("name", getSpectatorUsername(id, i));
            spectatorsArray.put(spectator);
        }
        json.put("spectators", spectatorsArray);
        return json;
    }

    public void startGame(Long lobbyId){
        Lobby lobby = getLobbyById(lobbyId);
        if (lobby.getGame() == null) {
            lobby.setGame(new Game(lobby.getCurrent_players().intValue(),getIdSum(lobby)));
            lobby.setActivePlayers(initializeActivePlayers(lobby));
            lobby.setIs_open(false);
        }
    }

    public int getIdSum(Lobby lobby){
        int sum = 0;
        for (int i = 0; i < lobby.getPlayers().size(); i++) sum += lobby.getPlayers().get(i).intValue() * i;
        return sum + lobby.getId().intValue();
    }

    public List<Long> initializeActivePlayers(Lobby lobby) {
        List<Long> activePlayers = new ArrayList<>(lobby.getPlayers().size());
        activePlayers.addAll(lobby.getPlayers());
        return activePlayers;
    }

    public boolean executeMove(Move move, Long lobbyId) {
        Lobby lobby = getLobbyById(lobbyId);
        if (!lobby.checkIfMoveValid(move)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This move is not valid.");
        lobby.executeMove(move);
        return lobby.checkIfMoveValid(move);
    }

    public void skipTurn(Long lobbyId) {
        Lobby lobby = getLobbyById(lobbyId);
        lobby.nextTurn();
    }

    public void leaveGame(Long lobbyId, Long id){
        Lobby lobby = getLobbyById(lobbyId);
        lobby.leaveGame(id);
        resetPlayer(id);
    }

    public boolean checkIfMoveValid(Move attemptedMove, Long lobbyId) {
        Lobby lobby = getLobbyById(lobbyId);
        return lobby.checkIfMoveValid(attemptedMove);
    }

    public int[] getPlayerScores(Lobby lobby) {
        int[] scores = new int[lobby.getCurrent_players().intValue()];
        for (int i = 0; i < lobby.getCurrent_players(); i++) {
            scores[i] = (lobby.getGame().getPlayerScore(i));
        }
        return scores;
    }

    public void rewardPlayers(Lobby lobby){
        int[] scores = getPlayerScores(lobby);
        int[] ranking = calculateRanking(scores);
        for (int i = 0; i < lobby.getPlayers().size(); i++) {
            User player = userRepository.findUserById(lobby.getPlayers().get(i));
            int reward = calculateReward(i, scores[i], ranking, scores.length);
            if (isValidGame(lobby) && isActivePlayer(lobby, player)) {
                player.addScore(reward);
                player.finishedGame();
            } else if (isActivePlayer(lobby, player)){
                player.addScore(reward/2);
            } else {
                if (reward > 0) player.addScore(reward/2);
                else player.addScore(reward*2);
            }
        }
    }

    public int[] calculateRanking(int[] scores){
        int[] ranking = new int[scores.length];
        for (int i = 0; i < scores.length; i++) {
            ranking[i] = 1;
            for (int score: scores){
                if (score > scores[i]) ranking[i] += 1;
            }
        }
        return ranking;
    }

    public int calculateReward(int playerIndex, int score, int[] ranking, int players) {
        if (ranking[playerIndex] == 1) return (30 + score/4) * players/2 / isTied(ranking, playerIndex);
        else if (ranking[playerIndex] == 2 && players > 2) return (20 + score/4) * players/2 / isTied(ranking, playerIndex);
        else if (ranking[playerIndex] == 2 && players == 2) return (-10 + score/4);
        else if (ranking[playerIndex] == 3 && players > 3) return (10 + score/4) * players/2 / isTied(ranking, playerIndex);
        else if (ranking[playerIndex] == 3 && players == 3) return (-15 + score/4);
        return (-20 + score/4);
    }

    public int isTied(int[] ranking, int playerIndex) {
        int rank = ranking[playerIndex];
        int counted = 0;
        for (int r: ranking) if (r == rank) counted += 1;
        return counted;
    }

    public boolean isValidGame(Lobby lobby) {
        return lobby.getActivePlayers().size() > 1;
    }

    public boolean isActivePlayer(Lobby lobby, User player){
        return lobby.getActivePlayers().contains(player.getId());
    }
}
