package ch.uzh.ifi.hase.soprafs22.rest.dto;

public class LobbyPostDTO {
    private Long host_id;

    private String name;

    private Boolean is_public;

    private Long total_players;

    private Long timer;

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

    public Long getTotal_players() {
        return total_players;
    }

    public void setTotal_players(Long total_players) {
        this.total_players = total_players;
    }

    public Long getTimer() { return timer; }

    public void setTimer(Long timer) { this.timer = timer; }
}