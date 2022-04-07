package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LobbyServiceTest {

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LobbyService lobbyService;

    private Lobby testLobby;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testLobby = new Lobby();
        testLobby.setId(1L);
        testLobby.setName("testLobby");
        testLobby.setHost_name("Sam");
        testLobby.setTotal_players(4L);
        // when -> any object is being save in the lobbyRepository -> return the dummy
        // testLobby
        Mockito.when(lobbyRepository.save(Mockito.any())).thenReturn(testLobby);
    }

    public void startGameTest(){
        lobbyService.createLobby(testLobby);
        testLobby.setCurrent_players(4L);
        assertEquals(testLobby.getCurrent_players(), testLobby.getTotal_players());
    }

//    TODO update this updateLobbyTest so that it tests if the current_player count and also if it is_open will be closed if its full updates correctly
//     @Test
//    public void updateLobbyTest() {
//        lobbyService.createLobby(testLobby);
//        Mockito.when(lobbyRepository.findLobbyById(Mockito.any())).thenReturn(testLobby);
//        //create new data
//        Lobby updatedLobby = testLobby;
//        updatedLobby.setCurrent_players(3L);
//        lobbyService.updateLobby(1L);
//
//        //check that current players count of the updated lobby has changed
//        assertEquals(testLobby.getCurrent_players(), updatedLobby.getCurrent_players());
//        assertEquals(testLobby.setIs_open(false), updatedLobby.setIs_open(false));
//    }

}

