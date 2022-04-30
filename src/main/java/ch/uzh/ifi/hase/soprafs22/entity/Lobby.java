package ch.uzh.ifi.hase.soprafs22.entity;


import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Internal Lobby Representation
 * This class composes the internal representation of the Lobby and defines how
 * the Lobby is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unique across the database -> composes
 * the primary key
 */

@Entity
@Table(name="LOBBY")
public class Lobby  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
/*
    @OneToOne
    @JoinColumn
    private User host;
*/
    @Column
    private Game game;

    @Column(nullable = false)
    private Long host_id;

    @Column(nullable = false)
    private String host_name;

    @Column(nullable = false)
    private Long current_players;

    @Column(nullable = false)
    private Long total_players;

    @Column(nullable = false)
    private Boolean is_open;

    @Column(nullable = false)
    private Boolean is_public;


    @ElementCollection
    //@JoinColumn(nullable = false) //Go read les docs in case this does not work we just used @JoinColumn.
    //private List<User> users = new ArrayList<>(); //IMPORTANT: define a list always like this! It will not give you specific errors.
    private List<Long> players = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long lobbyId) {
        this.id = lobbyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String lobbyName) {
        this.name = lobbyName;
    }

    public Boolean getIs_public() {
        return is_public;
    }

    public void setIs_public(Boolean aPublic) {
        is_public = aPublic;
    }

    public Boolean getIs_open() {
        return is_open;
    }

    public void setIs_open(Boolean open) {
        is_open = open;
    }

    public Long getHost_id() {
        return host_id;
    }

    public void setHost_id(Long hostId) {
        this.host_id = hostId;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public Long getTotal_players() {
        return total_players;
    }

    public void setTotal_players(Long total_players) {
        this.total_players = total_players;
    }

    public Long getCurrent_players() {
        return current_players;
    }

    public void setCurrent_players(Long current_players) {
        this.current_players = current_players;
    }

    public List<Long> getPlayers() {
        return players;
    }

    public void setPlayers(List<Long> players) {
        this.players = players;
    }

    public void addPlayer(Long id){
        this.players.add(id);
    }

    public void removePlayer(Long id){
        this.players.remove(id);
    }

    // is user in lobby
    public boolean isUserInLobby(Long id){
        return this.players.contains(id);
    }

    public void startGame(int playerCount){
        game = new Game(playerCount);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
