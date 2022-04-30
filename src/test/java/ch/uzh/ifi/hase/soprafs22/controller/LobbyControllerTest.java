package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.LobbyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;


import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * LobbyControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the UserController works.
 */
@WebMvcTest(LobbyController.class)
public class LobbyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LobbyService lobbyService;


    // create a new user
    @Test
    public void createLobby_validInput_lobbyCreated() throws Exception {
        // given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);

        LobbyPostDTO lobbyPostDTO = new LobbyPostDTO();
        lobbyPostDTO.setHost_id(7L);
        lobbyPostDTO.setName("ToxicMW2Lobby");
        lobbyPostDTO.setIs_public(false);

        given(lobbyService.createLobby(Mockito.any())).willReturn(lobby);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/lobbies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(lobbyPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.host_id", is(lobby.getHost_id().intValue())))
                .andExpect(jsonPath("$.name", is(lobby.getName())))
                .andExpect(jsonPath("$.is_public", is(lobby.getIs_public())));
    }

//    @Test
//    public void createLobby_invalidInput() throws Exception{
//    }

    // is this valid? because the get request does not send anything with it in ints body -> notign to compare
    @Test
    public void getLobby_validInput() throws Exception {
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        System.out.println("This is the lobby's ID " + lobby.getId());

        given(lobbyService.getLobbyById(7L)).willReturn(lobby);

        // when
        MockHttpServletRequestBuilder getRequest = get("/lobbies/"+lobby.getId())
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void getLobbyById_invalidInput() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        System.out.println("This is the lobby's ID " + lobby.getId());

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(lobbyService)
                .getLobbyById(Mockito.any());

        // when
        MockHttpServletRequestBuilder getRequest = get("/lobbies/"+7)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isNotFound());

    }

    @Test
    public void getUserInThisLobby_validInput() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        // user
        User user = new User();
        user.setId(7L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setLogged_in(true);

        given(lobbyService.isInThisLobby(1L, 7L)).willReturn(Boolean.TRUE);

        // when
        MockHttpServletRequestBuilder getRequest = get("/lobbies/"+lobby.getId()+"/users/"+user.getId())
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk());

    }

    @Test
    public void getUserInThisLobby_invalidInput() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        // user
        User user = new User();
        user.setId(7L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setLogged_in(true);

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(lobbyService)
                .getLobbyById(Mockito.any());

        // when
        MockHttpServletRequestBuilder getRequest = get("/lobbies/"+lobby.getId()+"/users/"+8L)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void getUserInAnyLobby_validInput() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        // user
        User user = new User();
        user.setId(7L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setLogged_in(true);

        given(lobbyService.isInAnyLobby(7L)).willReturn(Boolean.TRUE);

        // when
        MockHttpServletRequestBuilder getRequest = get("/users/"+user.getId()+"/lobbies/")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk());

    }

    @Test
    public void getUserInAnyLobby_invalidInput() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        // user
        User user = new User();
        user.setId(7L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setLogged_in(true);

        given(lobbyService.isInAnyLobby(8L)).willReturn(Boolean.FALSE);

        // when
        MockHttpServletRequestBuilder getRequest = get("/users/"+8L+"/lobbies/")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk());

    }

    @Test
    public void joinLobby_withUser_validInput() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        // user
        User user = new User();
        user.setId(8L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setLogged_in(true);


        doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
                .when(lobbyService)
                .joinLobby(1L, 8L);

        // when
        MockHttpServletRequestBuilder getRequest = put("/lobbies/"+lobby.getId()+"/users/"+user.getId()+"/join")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isNoContent());

    }



    @Test
    public void joinLobby_withUser_alreadyInThis() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        // user
        User user = new User();
        user.setId(7L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setLogged_in(true);


        doThrow(new ResponseStatusException(HttpStatus.FORBIDDEN))
                .when(lobbyService)
                .joinLobby(1L, 7L);

        // when
        MockHttpServletRequestBuilder getRequest = put("/lobbies/"+lobby.getId()+"/users/"+user.getId()+"/join")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isForbidden());

    }

    @Test
    public void joinLobby_withUser_alreadyInAny() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        // user
        User user = new User();
        user.setId(7L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setLogged_in(true);


        doThrow(new ResponseStatusException(HttpStatus.CONFLICT))
                .when(lobbyService)
                .joinLobby(1L, 7L);

        // when
        MockHttpServletRequestBuilder getRequest = put("/lobbies/"+lobby.getId()+"/users/"+user.getId()+"/join")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isConflict());

    }

    @Test
    public void leaveLobby_withUser_validInput() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        // user
        User user = new User();
        user.setId(8L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setLogged_in(true);


        doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
                .when(lobbyService)
                .leaveLobby(1L, "8L");

        // when
        MockHttpServletRequestBuilder getRequest = put("/lobbies/"+lobby.getId()+"/users/"+user.getId()+"/leave")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isNoContent());

    }

    @Test
    public void leaveLobby_withUser_invalidInput() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        // user
        User user = new User();
        user.setId(8L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setLogged_in(true);


        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(lobbyService)
                .leaveLobby(Mockito.any(), Mockito.any());
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(lobbyService)
                .playerLeaves(Mockito.any(), Mockito.any());

        // when
        MockHttpServletRequestBuilder getRequest = put("/lobbies/"+lobby.getId()+"/users/"+99L+"/leave")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isNotFound());

    }

    @Test
    public void kickUser_validInput() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        // user
        User user = new User();
        user.setId(8L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setLogged_in(true);


        doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
                .when(lobbyService)
                .kickUserFromLobby(lobby.getId(), lobby.getHost_id(), user.getId());

        // when
        MockHttpServletRequestBuilder getRequest = put("/lobbies/"+lobby.getId()+"/host/"+lobby.getHost_id()+"/users/"+user.getId()+"/kick")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isNoContent());

    }

    @Test
    public void kickUser_notTheHost_unsuccessful() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        // user
        User user = new User();
        user.setId(8L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setLogged_in(true);


        doThrow(new ResponseStatusException(HttpStatus.CONFLICT))
                .when(lobbyService)
                .kickUserFromLobby(lobby.getId(), 99L, user.getId());

        // when
        MockHttpServletRequestBuilder getRequest = put("/lobbies/"+lobby.getId()+"/host/"+99L+"/users/"+user.getId()+"/kick")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isConflict());

    }

    @Test
    public void kickUser_playerNotFound_unsuccessful() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        // user
        User user = new User();
        user.setId(8L);
        user.setUsername("testUsername");
        user.setToken("1");
        user.setLogged_in(true);


        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(lobbyService)
                .kickUserFromLobby(lobby.getId(), lobby.getHost_id(), 99L);

        // when
        MockHttpServletRequestBuilder getRequest = put("/lobbies/"+lobby.getId()+"/host/"+lobby.getHost_id()+"/users/"+99L+"/kick")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isNotFound());

    }

    @Test
    public void updateLobbyPrivacy_validInput() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        boolean privacy = true;

        doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
                .when(lobbyService)
                .updatePrivacy(Mockito.any(), Mockito.anyBoolean());

        MockHttpServletRequestBuilder putRequest = put("/lobbies/"+lobby.getId()+"/privacy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(privacy));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent());
    }


    @Test
    public void put_updateLobbyName_validInput() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        String name = "12pN00bZ";

        doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
                .when(lobbyService)
                .changeLobbyName(Mockito.any(), Mockito.any());

        MockHttpServletRequestBuilder putRequest = put("/lobbies/"+lobby.getId()+"/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(name));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent());

    }


    @Test
    public void put_updateLobbySize_validInput() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        long size = 4L;

        doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
                .when(lobbyService)
                .changeLobbySize(lobby.getId(), size);

        MockHttpServletRequestBuilder putRequest = put("/lobbies/"+lobby.getId()+"/size")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(size));

        // then
        mockMvc.perform(putRequest).andExpect(status().isNoContent());
    }

    @Test
    public void put_updateLobbySize_invalidInput() throws Exception{
        //given
        Lobby lobby = new Lobby();
        lobby.setHost_id(7L);
        lobby.setName("ToxicMW2Lobby");
        lobby.setIs_public(false);
        lobby.setId(1L);
        long size = 44L;

        doThrow(new ResponseStatusException(HttpStatus.CONFLICT))
                .when(lobbyService)
                .changeLobbySize(lobby.getId(), size);

        MockHttpServletRequestBuilder putRequest = put("/lobbies/"+lobby.getId()+"/size")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(size));

        // then
        mockMvc.perform(putRequest).andExpect(status().isConflict());

    }



    /**
     * Helper Method to convert userPostDTO into a JSON string such that the input
     * can be processed
     * Input will look like this: {"username": "testUsername"}
     *
     * @param object
     * @return string
     */
    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}