package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.Move;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import com.sun.xml.bind.v2.TODO;
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
import java.util.concurrent.TimeUnit;

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
        newLobby.setIs_open(true);
        newLobby.setIs_public(true); //public by default if one wants to change to private host has to change with updatePrivacy
        newLobby.setCurrent_players(1L);
        newLobby.addPlayer(newLobby.getHost_id());
        newLobby.setHost_name(userRepository.findUserById(newLobby.getHost_id()).getUsername());
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
        for (Lobby lobby1: allLobbies) {
            if (lobby1.isUserInLobby(id)) { return true; }
        }
        return false;
    }

    public Lobby getLobbyOfUser(Long id){
        List<Lobby> allLobbies = getLobbies();
        for (Lobby lobby1: allLobbies) {
            if (lobby1.isUserInLobby(id)) { return lobby1; }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not in any lobby");
    }

    public JSONObject getGameOfUser(Long id) {
        Lobby lobby = getLobbyOfUser(id);
        if (lobby.getGame() == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no game running");
        }
        return lobby.getGame().jsonify();

    }

    public void joinLobby(Long lobbyId, Long id){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        if (isInThisLobby(lobbyId, id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are already in this Lobby"); }
        else if (isInAnyLobby(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You are already in a Lobby"); }
        lobby.addPlayer(id); //add User into players
        lobby.setCurrent_players(lobby.getCurrent_players()+1); //adjust player count
        updateLobby(lobbyId); //update the lobby via its id
        this.lobbyRepository.flush();
    }

    public void hostLeaves(Long lobbyId){
        lobbyRepository.deleteById(lobbyId);
        this.lobbyRepository.flush();
    }

    public void playerLeaves(Lobby lobby, Long id){
        if (lobby.getPlayers().contains(id)) {
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

    public void updatePrivacy(Long lobbyId, boolean privacy){
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

    public void checkIfTaken(Long id, String un){
        Lobby lobbyByUN = lobbyRepository.findLobbyByName(un);
        if (lobbyByUN != null && lobbyRepository.findLobbyById(id) != lobbyByUN) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This LobbyName is already taken.");
        }
    }

    //these are placeholders
    public void setNumberOfPlayers() {

    }

    public void startGame(Long lobbyId){
        Lobby lobby = lobbyRepository.findLobbyById(lobbyId);
        lobby.setGame(new Game(lobby.getCurrent_players().intValue()));
        lobby.setIs_open(false);
    }

    public void executeMove(Move move, Long lobbyId) {
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        lobby.executeMove(move);
    }

    public boolean checkIfMoveValid(Move attemptedMove, Long lobbyId) {
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        return lobby.checkIfMoveValid(attemptedMove);
    }

    //TODO: write the methods which are needed in the LobbyController

}
