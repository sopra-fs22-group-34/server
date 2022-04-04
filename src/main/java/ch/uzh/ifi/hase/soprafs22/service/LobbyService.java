package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
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

    public Lobby createLobby(Lobby newLobby) {
        new Lobby();
        newLobby.setIs_open(true);
        newLobby.setCurrent_players(1L);
        newLobby.addPlayer(newLobby.getHost_id());
        newLobby.setHost_name(userRepository.findUserById(newLobby.getHost_id()).getUsername());
        newLobby = this.lobbyRepository.save(newLobby);
        lobbyRepository.flush();
        log.debug("Created Information for User: {}", newLobby);
        return newLobby;
    }

    public void joinLobby(Long lobbyId, Long id){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        lobby.addPlayer(id);
        lobby.setCurrent_players(lobby.getCurrent_players()+1);
        lobby = updateLobby(lobby.getId(), lobby);
        this.lobbyRepository.flush();
    }

    public void leaveLobby(Long lobbyId, Long id){
        Lobby lobby = this.lobbyRepository.findLobbyById(lobbyId);
        long longNum = id;
        List<Long> lobbyList = lobby.getPlayers();
        lobbyList.remove(longNum);
        lobby.setCurrent_players(lobby.getCurrent_players()-1);
        lobby = updateLobby(lobby.getId(), lobby);
        this.lobbyRepository.flush();
    }



    public Lobby updateLobby(Long ID, Lobby updatedLobby){
        // checks if lobby is full and if lobby is full it sets is_open to false
        Lobby lobbyToEdit = getLobbyById(ID);
        if (updatedLobby.getCurrent_players() == updatedLobby.getTotal_players() || updatedLobby.getCurrent_players() ==0) { lobbyToEdit.setIs_open(false); }

        return lobbyToEdit;
    }

    public void checkIfTaken(Long id, String un){
        Lobby lobbyByUN = lobbyRepository.findLobbyByName(un);
        if (lobbyByUN != null && lobbyRepository.findLobbyById(id) != lobbyByUN) {
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
