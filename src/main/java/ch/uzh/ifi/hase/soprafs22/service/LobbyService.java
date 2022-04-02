package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyGetDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

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

    public Lobby createLobby(Lobby lobby) {
        Lobby newLobby = lobbyRepository.save(lobby);
        lobbyRepository.flush();

        log.debug("Created Information for User: {}", newLobby);
        return newLobby;
    }

    public Lobby getLobbyById(long id) {
        Lobby lobbyById = lobbyRepository.findLobbyByLobbyId(id);
        if (lobbyById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No lobby with this ID exists.");
        }
        return lobbyById;
    }

    public Lobby getLobbyByLobbyname(String lobbyname) {
        Lobby lobbyByLobbyname = lobbyRepository.findLobbyBylobbyName(lobbyname);
        if (lobbyByLobbyname == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No lobby with this lobbyname exists.");
        }
        return lobbyByLobbyname;
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
