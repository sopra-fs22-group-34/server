package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
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

//    TODO update these tests as soon as a new put rekwest is there
//    @Test
//    public void validInput_whenPutLobbyId_thenReturnNoContent() throws Exception {
//        //204 no content update lobby name
//        Lobby lobby = new Lobby();
//        lobby.setId(1L);
//        lobby.setName("NoobsOnly");
//
//        doThrow(new ResponseStatusException(HttpStatus.NO_CONTENT))
//                .when(lobbyService)
//                .updateLobby(1L, lobby);
//
//        // when/then -> do the request + validate the result
//        MockHttpServletRequestBuilder putRequest = put("/lobbies/{lobbyId}", 1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(lobby));
//
//        // then
//        mockMvc.perform(putRequest)
//                .andExpect(status().isNoContent());
//
//    }
//
//    @Test
//    public void invalidId_whenPutUserId_thenReturnNotFound() throws Exception {
//        // given put 404
//        Lobby lobby = new Lobby();
//        lobby.setId(1L);
//        lobby.setName("Pros");
//
//        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
//                .when(lobbyService)
//                .updateLobby(Mockito.any(), Mockito.any());
//
//        // when/then -> do the request + validate the result
//        MockHttpServletRequestBuilder putRequest = put("/lobbies/{lobbyId}", 2)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(lobby));
//
//        // then
//        mockMvc.perform(putRequest)
//                .andExpect(status().isNotFound());
//    }

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