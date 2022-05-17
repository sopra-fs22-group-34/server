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

import static org.junit.jupiter.api.Assertions.*;

class LobbyServiceTest {

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LobbyService lobbyService;

    private Lobby testLobby;

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
        assertThrows(ResponseStatusException.class, () -> lobbyService.getLobbyByLobbyName("wrongLobbyName"));
        // find given lobby
        Mockito.when(lobbyRepository.findLobbyByName(Mockito.any())).thenReturn(testLobby);
        assertEquals(lobbyService.getLobbyByLobbyName("testLobby"), testLobby);
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

    @Test
    public void calculateRankingTest() {
        int[] scores1 = new int[]{50, 60, 20, 35};
        int[] ranking1 = lobbyService.calculateRanking(scores1);
        assertArrayEquals(new int[]{2, 1, 4, 3}, ranking1);

        int[] scores2 = new int[]{50, 60, 20, 60};
        int[] ranking2 = lobbyService.calculateRanking(scores2);
        assertArrayEquals(new int[]{3, 1, 4, 1}, ranking2);

        int[] scores3 = new int[]{50, 60, 60, 50};
        int[] ranking3 = lobbyService.calculateRanking(scores3);
        assertArrayEquals(new int[]{3, 1, 1, 3}, ranking3);

        int[] scores4 = new int[]{50, 60, 20};
        int[] ranking4 = lobbyService.calculateRanking(scores4);
        assertArrayEquals(new int[]{2, 1, 3}, ranking4);
    }

    @Test
    public void isTiedTest(){
        int[] ranking1 = new int[]{1,2,3,4};
        assertEquals(1, lobbyService.isTied(ranking1,0));

        int[] ranking2 = new int[]{1,2,2,4};
        assertEquals(2, lobbyService.isTied(ranking2,2));

        int[] ranking3 = new int[]{1,1,1,4};
        assertEquals(3, lobbyService.isTied(ranking3,1));

        int[] ranking4 = new int[]{1,1,1,1};
        assertEquals(4, lobbyService.isTied(ranking4,3));
    }

    @Test
    public void calculateRewardTest() {
        int[] scores1 = new int[]{60, 40, 32, 20};
        int[] ranking1 = lobbyService.calculateRanking(scores1);
        assertEquals(90, lobbyService.calculateReward(0, 60, ranking1, 4));
        assertEquals(60, lobbyService.calculateReward(1, 40, ranking1, 4));
        assertEquals(36, lobbyService.calculateReward(2, 32, ranking1, 4));
        assertEquals(-15, lobbyService.calculateReward(3, 20, ranking1, 4));

        int[] scores2 = new int[]{60, 40, 20, 20};
        int[] ranking2 = lobbyService.calculateRanking(scores2);
        assertEquals(90, lobbyService.calculateReward(0, 60, ranking2, 4));
        assertEquals(60, lobbyService.calculateReward(1, 40, ranking2, 4));
        assertEquals(15, lobbyService.calculateReward(2, 20, ranking2, 4));
        assertEquals(15, lobbyService.calculateReward(3, 20, ranking2, 4));

        int[] scores3 = new int[]{60, 40, 20};
        int[] ranking3 = lobbyService.calculateRanking(scores3);
        assertEquals(67, lobbyService.calculateReward(0, 60, ranking3, 3));
        assertEquals(45, lobbyService.calculateReward(1, 40, ranking3, 3));
        assertEquals(-10, lobbyService.calculateReward(2, 20, ranking3, 3));

        int[] scores4 = new int[]{60, 40, 40};
        int[] ranking4 = lobbyService.calculateRanking(scores4);
        assertEquals(67, lobbyService.calculateReward(0, 60, ranking4, 3));
        assertEquals(22, lobbyService.calculateReward(1, 40, ranking4, 3));
        assertEquals(22, lobbyService.calculateReward(2, 40, ranking4, 3));

        int[] scores5 = new int[]{40, 40, 40};
        int[] ranking5 = lobbyService.calculateRanking(scores5);
        assertEquals(20, lobbyService.calculateReward(0, 40, ranking5, 3));
        assertEquals(20, lobbyService.calculateReward(1, 40, ranking5, 3));
        assertEquals(20, lobbyService.calculateReward(2, 40, ranking5, 3));

        int[] scores6 = new int[]{60, 40};
        int[] ranking6 = lobbyService.calculateRanking(scores6);
        assertEquals(45, lobbyService.calculateReward(0, 60, ranking6, 2));
        assertEquals(0, lobbyService.calculateReward(1, 40, ranking6, 2));

        int[] scores7 = new int[]{60, 60};
        int[] ranking7 = lobbyService.calculateRanking(scores7);
        assertEquals(22, lobbyService.calculateReward(0, 60, ranking7, 2));
        assertEquals(22, lobbyService.calculateReward(1, 60, ranking7, 2));
    }
}