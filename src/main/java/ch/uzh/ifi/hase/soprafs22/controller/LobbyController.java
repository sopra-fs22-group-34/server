package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/lobbies")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<LobbyGetDTO> getAllLobbies() {
        // fetch all lobbies in the internal representation
        List<Lobby> lobbies = lobbyService.getLobbies();
        List<LobbyGetDTO> lobbyGetDTOs = new ArrayList<>();

        // convert each lobby to the API representation
        for (Lobby lobby : lobbies) {
            lobbyGetDTOs.add(DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(lobby));
        }
        return lobbyGetDTOs;
    }

    @GetMapping("/lobbies/{lobbyId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public LobbyGetDTO getLobbyById(@PathVariable String lobbyId) {
        Lobby lobby = new Lobby();

        // fetch lobby by specified ID
        try { lobby = lobbyService.getLobbyById(Long.parseLong(lobbyId)); }
        // if the path cannot be converted into an ID, it must be a lobbyname. Therefore, fetch by username
        catch (NumberFormatException notID){ lobby = lobbyService.getLobbyByLobbyname(lobbyId); }

        return DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(lobby);
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

    @PutMapping("/lobbies/{lobbyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void updateLobby(@PathVariable long lobbyId, @RequestBody Lobby updatedLobby){
        lobbyService.getLobbyById(lobbyId);

        // check if new name already belongs to another Lobby
        lobbyService.checkIfTaken(lobbyId,updatedLobby.getLobbyName());

        // update the Lobby
        Lobby lobby = lobbyService.updateLobby(lobbyId, updatedLobby);
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
