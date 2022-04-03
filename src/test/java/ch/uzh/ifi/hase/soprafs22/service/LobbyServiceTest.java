package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.repository.LobbyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

class LobbyServiceTest {

    @Mock
    private LobbyRepository lobbyRepository;

    @InjectMocks
    private LobbyService lobbyService;

    private Lobby testLobby;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testLobby = new Lobby();
        testLobby.setLobbyId(1L);
        testLobby.setLobbyName("testLobby");


        // when -> any object is being save in the lobbyRepository -> return the dummy
        // testLobby
        Mockito.when(lobbyRepository.save(Mockito.any())).thenReturn(testLobby);
    }

    @Test
    public void updateLobbyTest() {
        lobbyService.createLobby(testLobby);
        Mockito.when(lobbyRepository.findLobbyByLobbyId(Mockito.any())).thenReturn(testLobby);
        //create new data
        Lobby updatedLobby = new Lobby();
        updatedLobby.setLobbyName("newLobbyName");

        assertNotEquals(testLobby.getLobbyName(), updatedLobby.getLobbyName());
        // update test for total play limit
        //assertNotEquals(testLobby.getTotalCount(), updatedLobby.getTotalCount());

        lobbyService.updateLobby(1L, updatedLobby);

        assertEquals(testLobby.getLobbyName(), updatedLobby.getLobbyName());
        // update test for total play limit
        // assertEquals(testLobby.getTotalCount(), updatedLobby.getTotalCount());
    }

}

