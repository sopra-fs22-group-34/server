package ch.uzh.ifi.hase.soprafs22.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class LobbyGetDTO {

    private Long id;

    private String secret_url;
    private Long host_id;
    private String name;
    private Boolean is_public;
    private Boolean is_open;
    private Long total_players;
    private Long current_players;
    private List<Long> players;
    private String host_name;
    private int timer;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHost_id() {
        return host_id;
    }

    public void setHost_id(Long host_id) {
        this.host_id = host_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIs_public() {
        return is_public;
    }

    public void setIs_public(Boolean is_public) {
        this.is_public = is_public;
    }

    public Boolean getIs_open() {
        return is_open;
    }

    public void setIs_open(Boolean open) {
        this.is_open = open;
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

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public int getTimer() { return timer; }

    public void setTimer(int timer) { this.timer = timer; }

    public String getSecret_url() {
        return secret_url;
    }

    public void setSecret_url(String secret_url) {
        this.secret_url = secret_url;
    }
}
