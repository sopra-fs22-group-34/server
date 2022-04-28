package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import com.sun.xml.bind.v2.TODO;
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

    // no fking idea what this does
    private final Logger log = LoggerFactory.getLogger(LobbyService.class);

    private final LobbyRepository lobbyRepository;

    private final UserRepository userRepository;

    // no idea what this does
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

    public void joinLobby(Long lobbyId, Long id){
        List<Lobby> allLobbies = getLobbies();
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        //check if id is already in players
        if (lobby.getPlayers().contains(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is already in this Lobby");
        }
        for (Lobby lobby1: allLobbies
             ) {
            if (lobby1.isUserInLobby(id)){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Player is already in a Lobby");
            }

        }
        lobby.addPlayer(id); //add User into players
        lobby.setCurrent_players(lobby.getCurrent_players()+1); //adjust player count
        lobby = updateLobby(lobbyId); //update the lobby via its id
        this.lobbyRepository.flush();
    }

    public void leaveLobby(Long lobbyId, String id){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        try {
            if (lobby.getHost_id() == Long.parseLong(id)){
                lobbyRepository.deleteById(lobbyId);
                this.lobbyRepository.flush();
                }
            else if (lobby.getPlayers().contains(Long.parseLong(id))) { //if User is in players remove item from List
                lobby.removePlayer(Long.parseLong(id));
                lobby.setCurrent_players(lobby.getCurrent_players()-1); //adjust player count
                updateLobby(lobbyId); //update the lobby via its id
                this.lobbyRepository.flush();}
            else{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Player with this Id exists.");} //If Player is not in players throw Error and don't go further
        }
        catch (NumberFormatException notID) { // if input cannot be converted into an ID, it must be a username
            Long newId = userRepository.findByUsername(id).getId();
            if (lobby.getHost_id().equals(newId)){
                lobbyRepository.deleteById(lobbyId);
                this.lobbyRepository.flush();
            }
            else if (lobby.getPlayers().contains(newId)) { //if User is in players remove item from List
                lobby.removePlayer(newId);
                lobby.setCurrent_players(lobby.getCurrent_players()-1); //adjust player count
                updateLobby(lobbyId); //update the lobby via its id
                this.lobbyRepository.flush();
            }
            else{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Player with this Id exists.");} //If Player is not in players throw Error and don't go further
        }
    }

    public void kickUserFromLobby(Long lobbyId, Long hostId, Long userToKickId){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        if (lobby.getHost_id() == hostId && lobby.getPlayers().contains(userToKickId)){
            lobby.removePlayer(userToKickId);
            lobby.setCurrent_players(lobby.getCurrent_players()-1); //adjust player count
            updateLobby(lobbyId); //update the lobby via its id
            this.lobbyRepository.flush();
        }
        else if (lobby.getHost_id() != hostId) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Only the host can kick players. Make sure you are the host of the lobby before trying to kick someone.");
        }

        else if (!lobby.getPlayers().contains(userToKickId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Player to kick with this Id exists.");
        }

        else {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Something went wrong while kicking the Player. Make sure you are in the right Lobby as the host and the Player you want to kick exists.");} //If Player is not in players throw Error and don't go further
    }

    //As a host I want to have a private lobby in order to only play with friends
    public void updatePrivacy(Long lobbyId, Long hostId){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        if (lobby.getHost_id() == hostId){
            lobby.setIs_public(false);
            updateLobby(lobbyId);
            this.lobbyRepository.flush();
        }
        else {throw new ResponseStatusException(HttpStatus.CONFLICT, "Only the host can change the Privacy.");}
    }

    //Host can change the lobby name
    public void changeLobbyName(Long lobbyId, Long hostId){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        if (lobby.getHost_id() == hostId) {
            lobby.getName();
            updateLobby(lobbyId);
            this.lobbyRepository.flush();
        }
        else {throw new ResponseStatusException(HttpStatus.CONFLICT, "Only the host can change the Name of the Lobby.");}
    }

    public void changeLobbySize(Long lobbyId, Long hostId){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        if (lobby.getHost_id() == hostId){
            lobby.getTotal_players();
            updateLobby(lobbyId);
            this.lobbyRepository.flush();
        }
        else {throw new ResponseStatusException(HttpStatus.CONFLICT, "Only the host can change the maximum number of players.");}
    }

    public Lobby updateLobby(Long lobbyId){
        // checks if lobby is full or completely empty and if true sets is_open to false in order to close the lobby
        Lobby updatedLobby = getLobbyById(lobbyId);
        if (updatedLobby.getCurrent_players() == updatedLobby.getTotal_players() || updatedLobby.getCurrent_players() == null) {updatedLobby.setIs_open(false);}
        return updatedLobby;
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



    //TODO: write the methods which are needed in the LobbyController

}
