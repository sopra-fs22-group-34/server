package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
public class LobbyServiceIntergrationTest {

    @Qualifier("lobbyRepository")
    @Autowired
    private LobbyRepository lobbyRepository;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LobbyService lobbyService;

    @Autowired
    private UserService userService;

    /*@BeforeEach
    public void setup() {
        //a user to work with
        // (these are all the properties a UserPostDTO object has to have.
        // the other properties get set in the userService. -> in createUser)
        User hostUser = new User();
        hostUser.setName("Till");
        hostUser.setUsername("LIFAD");
        hostUser.setPassword("Zeit");
        userService.createUser(hostUser);
    }

    @Test
    public void createLobby_validInputs_success() {
        //this is given
        assertNull(lobbyRepository.findLobbyById(1L));

        Lobby testLobby = new Lobby();
        //add all the attributes of a LobbyPostDTO
        testLobby.setHost_id(userRepository.findUserById(1L).getId());
        testLobby.setName("Lobby");
        testLobby.setIs_public(true);
        testLobby.setIs_open(true);
        testLobby.setTotal_players(4L);

        //when
        Lobby createdLobby = lobbyService.createLobby(testLobby);

        //then
        assertEquals(testLobby.getHost_id(), createdLobby.getHost_id());
        assertEquals(testLobby.getHost_name(), createdLobby.getHost_name());
        assertEquals(testLobby.getCurrent_players(),createdLobby.getCurrent_players());
        assertEquals(testLobby.getTotal_players(), createdLobby.getTotal_players());
        assertEquals(testLobby.getIs_open(), createdLobby.getIs_open());
        assertEquals(testLobby.getIs_public(), createdLobby.getIs_public());

    }*/

    @Test
    public void startGameInLobby() {

        User hostUser = new User();
        hostUser.setName("Till");
        hostUser.setUsername("LIFAD");
        hostUser.setPassword("Zeit");
        userService.createUser(hostUser);

        Lobby testLobby = new Lobby();
        //add all the attributes of a LobbyPostDTO

        testLobby.setHost_id(userRepository.findUserById(1L).getId());
        testLobby.setName("Lobby");
        testLobby.setIs_public(true);
        testLobby.setIs_open(true);
        testLobby.setTotal_players(4L);

        //when
        Lobby createdLobby = lobbyService.createLobby(testLobby);
        createdLobby.startGame(4);

        assertNotNull(createdLobby.getGame());
        System.out.println(createdLobby.getGame().jsonify());

    }

}
