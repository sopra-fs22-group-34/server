package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.entity.Player;
import ch.uzh.ifi.hase.soprafs22.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyGetDTO;
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

    // no fking idea what this does
    private final Logger log = LoggerFactory.getLogger(LobbyService.class);

    private final LobbyRepository lobbyRepository;

    // no idea what this does
    @Autowired
    public LobbyService(@Qualifier("lobbyRepository") LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    public List<Lobby> getLobbies() {
        return this.lobbyRepository.findAll();
    }

    public Lobby getLobbyById(Long ID) {
        Lobby lobbyByID = lobbyRepository.findLobbyByLobbyId(ID);
        if (lobbyByID == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Lobby with this ID exists.");
        }
        return lobbyByID;
    }

    public Lobby getLobbyByLobbyname(String lobbyname) {
        Lobby lobbyByLobbyname = lobbyRepository.findLobbyByLobbyName(lobbyname);
        if (lobbyByLobbyname == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No lobby with this lobbyname exists.");
        }
        return lobbyByLobbyname;
    }

    public Lobby createLobby(Lobby newLobby) {
        new Lobby();
        newLobby.addPlayer(newLobby.getHostId());
        newLobby = this.lobbyRepository.save(newLobby);
        lobbyRepository.flush();
        log.debug("Created Information for User: {}", newLobby);
        return newLobby;
    }

    public void joinLobby(Long lobbyId, Long id){
        Lobby lobby = this.lobbyRepository.findLobbyByLobbyId(lobbyId);
        lobby.addPlayer(id);
        this.lobbyRepository.flush();
    }

    public Lobby updateLobby(Long ID, Lobby updatedLobby){

        Lobby lobbyToEdit = getLobbyById(ID);
        if (updatedLobby.getLobbyName() != null) { lobbyToEdit.setLobbyName(updatedLobby.getLobbyName()); }
        //if (updatedLobby.getPlayerAmount() != null) { lobbyToEdit.setPlayerAmount(updatedLobby.getPlayerAmount()); }

        return lobbyToEdit;
    }

    public void checkIfTaken(Long id, String un){
        Lobby lobbyByUN = lobbyRepository.findLobbyByLobbyName(un);
        if (lobbyByUN != null && lobbyRepository.findLobbyByLobbyId(id) != lobbyByUN) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This LobbyName is already taken.");
        }
    }

    //these are placeholders
    public void startGame() {

    }

    public void setNumberOfPlayers() {

    }

    public void kickPlayer() {

    }

    //TODO: write the methods which are needed in the LobbyController

}
