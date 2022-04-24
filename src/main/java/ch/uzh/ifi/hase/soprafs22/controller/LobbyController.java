package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.entity.User;
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

    @PutMapping("/lobbies/{lobbyId}/users/{userId}/join")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void joinLobbyWithUser(@PathVariable long lobbyId, @PathVariable long userId){
        lobbyService.joinLobby(lobbyId, userId);
    }

    @PutMapping("/lobbies/{lobbyId}/users/{userId}/leave")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void leaveLobbyWithUser(@PathVariable long lobbyId, @PathVariable long userId){
        lobbyService.leaveLobby(lobbyId, userId);
    }

    @PutMapping("/lobbies/{lobbyId}/host/{hostId}/users/{userToKickId}/kick")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void kickUserFromLobbyWithHost(@PathVariable long lobbyId, @PathVariable long hostId, @PathVariable long userToKickId){
        lobbyService.kickUserFromLobby(lobbyId, hostId, userToKickId);
    }

    @PutMapping("/lobbies/{lobbyId}/host/{hostId}/users/{updatePrivacy}/changePrivacy")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void updatePrivacyAsHost(@PathVariable long lobbyId, @PathVariable long hostId){
        lobbyService.updatePrivacy(lobbyId, hostId);
    }

    @PutMapping("/lobbies/{lobbyId}/{changeName}/changeName")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void changeLobbyNameAsHost(@PathVariable long lobbyId, long hostId){
        lobbyService.changeLobbyName(lobbyId, hostId);
    }

    @PutMapping("/lobbies/{lobbyId}/{changeSize}/changeSize")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void changeLobbySizeAsHost(@PathVariable long lobbyId, long hostId){
        lobbyService.changeLobbySize(lobbyId, hostId);
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
