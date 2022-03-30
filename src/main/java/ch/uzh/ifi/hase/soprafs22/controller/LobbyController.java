package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class LobbyController {



    @PostMapping("/lobbies")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createLobby() {
        //PLACEHOLDER
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
