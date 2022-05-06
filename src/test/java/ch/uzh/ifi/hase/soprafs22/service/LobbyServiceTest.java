package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LobbyServiceTest {

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LobbyService lobbyService;

    private Lobby testLobby;

    private UserService userService;

    private User testUser;
    private User testUser2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testLobby = new Lobby();
        testLobby.setId(1L);
        testLobby.setName("testLobby");
        testLobby.setHost_name("Sam");
        testLobby.setTotal_players(4L);
        testLobby.setHost_id(2L);
        // when -> any object is being save in the lobbyRepository -> return the dummy
        // testLobby
        Mockito.when(lobbyRepository.save(Mockito.any())).thenReturn(testLobby);

        // given
        testUser = new User();
        testUser.setId(2L);
        testUser.setUsername("Sam");
        testUser.setPassword("testPassword");

        testUser2 = new User();
        testUser2.setId(3L);
        testUser2.setUsername("Joiner");
        testUser2.setPassword("testPassword");

        // when -> any object is being save in the userRepository -> return the dummy
        // testUser
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
        Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(testUser);
        Mockito.when(userRepository.findByUsername("Sam")).thenReturn(testUser);
        Mockito.when(userRepository.findByUsername("Joiner")).thenReturn(testUser2);
    }


    @Test
    public void createLobbyValidInput() {
        Lobby createdLobby = lobbyService.createLobby(testLobby);

        // then
        Mockito.verify(lobbyRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testLobby.getId(), createdLobby.getId());
        assertEquals(testLobby.getName(), createdLobby.getName());
        assertEquals(testLobby.getHost_name(), createdLobby.getHost_name());
        assertEquals(testLobby.getHost_id(), createdLobby.getHost_id());
        assertEquals(1, createdLobby.getCurrent_players());
        assertTrue(createdLobby.getIs_open());

    }

    @Test
    public void getLobbyById() {
        lobbyService.createLobby(testLobby);
        // throw error for nonexistent lobby
        assertThrows(ResponseStatusException.class, () -> lobbyService.getLobbyById(2L));
        // find given lobby
        Mockito.when(lobbyRepository.findLobbyById(Mockito.any())).thenReturn(testLobby);
        assertEquals(lobbyService.getLobbyById(1L), testLobby);
    }

    @Test
    public void getLobbyByLobbyName() {
        lobbyService.createLobby(testLobby);
        // throw error for nonexistent lobby
        assertThrows(ResponseStatusException.class, () -> lobbyService.getLobbyByLobbyname("wrongLobbyName"));
        // find given lobby
        Mockito.when(lobbyRepository.findLobbyByName(Mockito.any())).thenReturn(testLobby);
        assertEquals(lobbyService.getLobbyByLobbyname("testLobby"), testLobby);
    }


    @Test
    public void joinLobbyValidInput() {
        lobbyService.createLobby(testLobby);
        Mockito.when(lobbyRepository.findLobbyById(Mockito.any())).thenReturn(testLobby);
        Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(testUser2);
        lobbyService.joinLobby(testLobby.getId(), testUser2.getId());

        //check that current players count of the updated lobby has changed
        assertTrue(testLobby.getPlayers().contains(testUser2.getId()));
        assertEquals(2, testLobby.getCurrent_players());
        assertEquals(1L, testUser2.getLobby());
    }

    @Test
    public void joinLobbyTwiceDoesNothing() {
        lobbyService.createLobby(testLobby);
        Mockito.when(lobbyRepository.findLobbyById(Mockito.any())).thenReturn(testLobby);
        Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(testUser2);
        lobbyService.joinLobby(testLobby.getId(), testUser2.getId());

        // joining once
        assertTrue(testLobby.getPlayers().contains(testUser2.getId()));
        assertEquals(2, testLobby.getCurrent_players());
        assertEquals(1L, testUser2.getLobby());

        // joining a second time
        lobbyService.joinLobby(testLobby.getId(), testUser2.getId());
        assertEquals(2, testLobby.getCurrent_players());
        assertEquals(1L, testUser2.getLobby());
    }

    @Test
    public void leaveLobbyAsUser() {
        lobbyService.createLobby(testLobby);
        Mockito.when(lobbyRepository.findLobbyById(Mockito.any())).thenReturn(testLobby);
        Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(testUser2);

        lobbyService.joinLobby(testLobby.getId(), testUser2.getId());
        assertTrue(testLobby.getPlayers().contains(testUser2.getId()));
        assertEquals(2, testLobby.getCurrent_players());
        assertEquals(1L, testUser2.getLobby());

        lobbyService.leaveLobby(testLobby.getId(), testUser2.getUsername());
        assertFalse(testLobby.getPlayers().contains(testUser2.getId()));
        assertEquals(1, testLobby.getCurrent_players());
        assertEquals(0L, testUser2.getLobby());
    }

    @Test
    public void leaveLobbyAsHost() {
        Lobby lobbyToDelete = lobbyService.createLobby(testLobby);
        Mockito.when(lobbyRepository.findLobbyById(Mockito.any())).thenReturn(lobbyToDelete);

        assertTrue(lobbyToDelete.getPlayers().contains(testUser.getId()));
        assertEquals(1, lobbyToDelete.getCurrent_players());
        assertEquals(1L, testUser.getLobby());

        lobbyService.leaveLobby(lobbyToDelete.getId(), testUser.getUsername());
        Mockito.verify(lobbyRepository, Mockito.times(1)).deleteById(Mockito.any());
        assertEquals(0L, testUser.getLobby());

    }

    @Test
    public void leaveLobbyWithInvalidData() {
        lobbyService.createLobby(testLobby);
        Mockito.when(lobbyRepository.findLobbyById(Mockito.any())).thenReturn(testLobby);

        assertThrows(ResponseStatusException.class, () -> lobbyService.leaveLobby(testLobby.getId(), "69"));

    }

    @Test
    public void kickUserValidInput() {
        lobbyService.createLobby(testLobby);
        Mockito.when(lobbyRepository.findLobbyById(Mockito.any())).thenReturn(testLobby);
        Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(testUser2);

        lobbyService.joinLobby(testLobby.getId(), testUser2.getId());
        assertTrue(testLobby.getPlayers().contains(testUser2.getId()));
        assertEquals(2, testLobby.getCurrent_players());
        assertEquals(1L, testUser2.getLobby());

        lobbyService.kickUserFromLobby(testLobby.getId(), testLobby.getHost_id(), testUser2.getId());
        assertFalse(testLobby.getPlayers().contains(testUser2.getId()));
        assertEquals(1, testLobby.getCurrent_players());
        assertEquals(0L, testUser2.getLobby());

    }

    @Test
    public void kickUserInvalidInput() {
        lobbyService.createLobby(testLobby);
        Mockito.when(lobbyRepository.findLobbyById(Mockito.any())).thenReturn(testLobby);

        // trying to kick user that is not in lobby
        assertThrows(ResponseStatusException.class, () -> lobbyService.kickUserFromLobby(testLobby.getId(), testLobby.getHost_id(), testUser2.getId()));
        // trying to kick user with invalid host
        assertThrows(ResponseStatusException.class, () -> lobbyService.kickUserFromLobby(testLobby.getId(), testUser2.getId(), testLobby.getHost_id()));
        // trying to kick user from a non existing lobby
        assertThrows(ResponseStatusException.class, () -> lobbyService.kickUserFromLobby(70L, testLobby.getHost_id(), testUser2.getId()));

    }

    @Test
    public void updateLobbyValidInput() {
        lobbyService.createLobby(testLobby);
        testLobby.setTotal_players(2L);
        Mockito.when(lobbyRepository.findLobbyById(Mockito.any())).thenReturn(testLobby);
        Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(testUser2);
        assertTrue(testLobby.getIs_open());
        lobbyService.joinLobby(testLobby.getId(), testUser2.getId());

        //check that current players count of the updated lobby has changed
        assertTrue(testLobby.getPlayers().contains(testUser2.getId()));
        assertEquals(2, testLobby.getCurrent_players());
        assertFalse(testLobby.getIs_open());

    }
}