package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Move;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public Lobby getLobbyById(Long ID) {
        Lobby lobbyByID = lobbyRepository.findLobbyById(ID);
        if (lobbyByID == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Lobby with this ID exists.");
        }
        return lobbyByID;
    }

    public Lobby getLobbyByLobbyname(String lobbyname) {
        Lobby lobbyByLobbyname = lobbyRepository.findLobbyByName(lobbyname);
        if (lobbyByLobbyname == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No lobby with this lobbyname exists.");
        }
        return lobbyByLobbyname;
    }

    //TODO add function to frontend which asks user how to set up PrivacySettings
    public Lobby createLobby(Lobby newLobby) {
        new Lobby();
        User host = userRepository.findUserById(newLobby.getHost_id());
        host.setLobby(newLobby.getId());
        newLobby.setIs_open(true);
        newLobby.setIs_public(true); //public by default if one wants to change to private host has to change with updatePrivacy
        newLobby.setCurrent_players(1L);
        newLobby.addPlayer(newLobby.getHost_id());
        newLobby.setHost_name(host.getUsername());
        newLobby = this.lobbyRepository.save(newLobby);
        lobbyRepository.flush();
        log.debug("Created Information for User: {}", newLobby);
        return newLobby;
    }

    public boolean isInThisLobby(Long lobbyId, Long id){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        return lobby.getPlayers().contains(id);
    }

    public boolean isInAnyLobby(Long id){
        List<Lobby> allLobbies = getLobbies();
        for (Lobby lobby: allLobbies) {
            if (lobby.isUserInLobby(id)) { return true; }
        }
        return false;
    }

    public Lobby getLobbyOfUser(Long id){
        List<Lobby> allLobbies = getLobbies();
        for (Lobby lobby: allLobbies) {
            if (lobby.isUserInLobby(id)) { return lobby; }
        }
        return null;
    }

    public JSONObject getGameOfUser(Long id) {
        Lobby lobby = getLobbyOfUser(id);
        if (lobby.getGame() == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no game running");
        }
        return lobby.getGame().jsonify().put("lobbyData", getLobbyData(id));
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
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        if (isInAnyLobby(id) && !isInThisLobby(lobbyId, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You are already in a different Lobby");
        } else if (!isInThisLobby(lobbyId, id)) {
            user.setLobby(lobbyId);
            lobby.addPlayer(id); //add User into players
            lobby.setCurrent_players(lobby.getCurrent_players()+1); //adjust player count
            updateLobby(lobbyId); //update the lobby via its id
            this.lobbyRepository.flush();
        }
    }

    public void hostLeaves(Long lobbyId){
        Lobby lobby = lobbyRepository.findLobbyById(lobbyId);
        for (Long playerId: lobby.getPlayers()) {
            User player = userRepository.findUserById(playerId);
            player.setLobby(0L);
        }
        lobbyRepository.deleteById(lobbyId);
        this.lobbyRepository.flush();
    }

    public void playerLeaves(Lobby lobby, Long id){
        User player = userRepository.findUserById(id);
        if (lobby.getPlayers().contains(id)) {
            player.setLobby(0L);
            lobby.removePlayer(id);
            lobby.setCurrent_players(lobby.getCurrent_players() - 1); //adjust player count
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player is not in this lobby.");
        }
    }

    public void leaveLobby(Long lobbyId, String id){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        try {
            if (lobby.getHost_id() == Long.parseLong(id)){ hostLeaves(lobbyId); } // delete the lobby if the host leaves
            else { playerLeaves(lobby, Long.parseLong(id)); }// remove player from the list of joined players
        } catch (NumberFormatException notID) { // if input cannot be converted into an ID, it must be a username
            Long newId = userRepository.findByUsername(id).getId(); // generate ID from the username
            if (lobby.getHost_id().equals(newId)){ hostLeaves(lobbyId); }
            else { playerLeaves(lobby, newId); }
        }
    }

    public void kickUserFromLobby(Long lobbyId, Long hostId, Long userToKickId){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        if (lobby.getHost_id().equals(hostId) && isInThisLobby(lobbyId, userToKickId)){
            playerLeaves(lobby, userToKickId); }
        else if (!lobby.getHost_id().equals(hostId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Only the host can kick players. Make sure you are the host of the lobby before trying to kick someone."); }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Player to kick with this Id exists."); }
    }

    public void updatePrivacy(Long lobbyId,  boolean privacy){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        lobby.setIs_public(privacy);
    }

    public void changeLobbyName(Long lobbyId, String name){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        lobby.setName(name);
    }

    public void changeLobbySize(Long lobbyId, Long size){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        if (lobby.getCurrent_players() < size){
            lobby.setTotal_players(size);
            updateLobby(lobbyId);
            this.lobbyRepository.flush();
        }
        else {throw new ResponseStatusException(HttpStatus.CONFLICT, "You can't make the lobby smaller since all slots are full!");}
    }

    public void updateLobby(Long lobbyId){
        // checks if lobby is full and if true sets is_open to false in order to close the lobby
        Lobby updatedLobby = getLobbyById(lobbyId);
        if (updatedLobby.getCurrent_players().equals(updatedLobby.getTotal_players())) {updatedLobby.setIs_open(false);}
    }

    public String getPlayerUsername(Long id, int playerIndex){
        Lobby lobby = getLobbyById(id);
        return userRepository.findUserById(lobby.getPlayers().get(playerIndex)).getUsername();
    }

    public JSONObject getLobbyData(Long id){
        Lobby lobby = getLobbyById(id);
        JSONObject json = new JSONObject();
        json.put("current_players", lobby.getCurrent_players());
        json.put("timer", lobby.getTimer());
        for (int i = 0; i < lobby.getPlayers().size(); i++) {
            if (i == 0) {
                json.put("one", lobby.getPlayers().get(i));
                json.put("nameOne", getPlayerUsername(id, i));
            }
            else if (i == 1) {
                json.put("two", lobby.getPlayers().get(i));
                json.put("nameTwo", getPlayerUsername(id, i));
            }
            else if (i == 2) {
                json.put("three", lobby.getPlayers().get(i));
                json.put("nameThree", getPlayerUsername(id, i));
            }
            else if (i == 3) {
                json.put("four", lobby.getPlayers().get(i));
                json.put("nameFour", getPlayerUsername(id, i));
            }
        }
        return json;
    }

    public void startGame(Long lobbyId){
        Lobby lobby = lobbyRepository.findLobbyById(lobbyId);
        if (lobby.getGame() == null) {
            lobby.setGame(new Game(lobby.getCurrent_players().intValue()));
            lobby.setIs_open(false);
        }
    }

    public void executeMove(Move move, Long lobbyId) {
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        if (!lobby.checkIfMoveValid(move)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This move is not valid.");
        lobby.executeMove(move);
    }

    public boolean checkIfMoveValid(Move attemptedMove, Long lobbyId) {
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        return lobby.checkIfMoveValid(attemptedMove);
    }
}
