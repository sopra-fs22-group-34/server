package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Lobby;
import ch.uzh.ifi.hase.soprafs22.entity.Move;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.LobbyService;
import org.json.JSONObject;
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

    @GetMapping("/lobbies/{lobbyId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean isUserInLobby(@PathVariable long lobbyId, @PathVariable long userId){
        return lobbyService.isInThisLobby(lobbyId, userId);
    }

    @GetMapping("/users/{userId}/lobbies")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean isUserInAnyLobby(@PathVariable long userId){
        return lobbyService.isInAnyLobby(userId);
    }


    @PostMapping("/lobbies/{lobbyId}/game")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void startGame(@PathVariable long lobbyId){
        lobbyService.startGame(lobbyId);
    }

    @GetMapping("/users/{userId}/game")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getGameOfUser(@PathVariable long userId){
        return lobbyService.getGameOfUser(userId).toString();
    }

    @GetMapping("/lobbies/{lobbyId}/game/players")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getPlayersOfLobby(@PathVariable long lobbyId){
        return lobbyService.getPlayersOfLobby(lobbyId).toString();
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
    public void leaveLobbyWithUser(@PathVariable long lobbyId, @PathVariable String userId){
        lobbyService.leaveLobby(lobbyId, userId);
    }

    @PutMapping("/lobbies/{lobbyId}/host/{hostId}/users/{userToKickId}/kick")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void kickUserFromLobbyWithHost(@PathVariable long lobbyId, @PathVariable long hostId, @PathVariable long userToKickId){
        lobbyService.kickUserFromLobby(lobbyId, hostId, userToKickId);
    }

    @PutMapping("/lobbies/{lobbyId}/privacy")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void updatePrivacyAsHost(@PathVariable long lobbyId, @RequestBody boolean privacy){
        lobbyService.updatePrivacy(lobbyId, privacy);
    }

    @PutMapping("/lobbies/{lobbyId}/name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void changeLobbyNameAsHost(@PathVariable long lobbyId, @RequestBody String name){
        lobbyService.changeLobbyName(lobbyId, name);
    }

    @PutMapping("/lobbies/{lobbyId}/size")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void changeLobbySizeAsHost(@PathVariable long lobbyId, @RequestBody Long size){
        lobbyService.changeLobbySize(lobbyId, size);
    }

    @GetMapping("/lobbies/{lobbyId}/game")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void getGameOfLobby(@PathVariable long lobbyId) {
        //PLACEHOLDER
    }


    //TODO: Discuss how to implement checking if a move is possible

    @PutMapping("/lobbies/{lobbyId}/game/moves")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void executeGameMove(@PathVariable long lobbyId, @RequestBody Move move) {
        lobbyService.executeMove(move, lobbyId);
    }

    @GetMapping("/lobbies/{lobbyId}/game/moves")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean checkIfMoveValid(@PathVariable long lobbyId, @RequestBody Move attemptedMove) {
        return lobbyService.checkIfMoveValid(attemptedMove, lobbyId);
    }
}
