package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@RestController
public class LobbyController {


    private final LobbyService lobbyService;

    LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @PostMapping("/lobbies")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public LobbyGetDTO createLobby(@RequestBody LobbyPostDTO lobbyPostDTO) {
        // convert API lobby to internal representation
        Lobby lobbyInput = DTOMapper.INSTANCE.convertLobbyPostDTOtoEntity(lobbyPostDTO);

        // create the lobby
        Lobby createdLobby = lobbyService.createLobby(lobbyInput);

        //convert the internal representation of lobby back to API
        return DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(createdLobby);
    }

    @GetMapping("/lobbies/{lobbyId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void getLobbyById(@PathVariable long lobbyId) {
        //PLACEHOLDER
    }

    @PutMapping("/lobbies/{lobbyId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void joinLobbyWithUser(@PathVariable long lobbyId, @PathVariable long userId){
        //PLACEHOLDER
    }

    @GetMapping("/lobbies/{lobbyId}/game")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void getGameOfLobby(@PathVariable long lobbyId) {
        //PLACEHOLDER
    }

    //TODO: Discuss how to implement checking if a move is possible

    @PutMapping("/lobbies/{lobbyId}/game/players/{playerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void executeGameMove(@PathVariable long lobbyId, @PathVariable long playerId){
        //TODO: Add @RequestBody function parameter once Move class is implemented
        //PLACEHOLDER
    }
}
