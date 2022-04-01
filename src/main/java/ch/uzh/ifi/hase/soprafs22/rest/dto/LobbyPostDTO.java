package ch.uzh.ifi.hase.soprafs22.rest.dto;

import ch.uzh.ifi.hase.soprafs22.entity.User;

public class LobbyPostDTO {

    private Long hostId;

    private String lobbyName;

    private Boolean isPublic;

    private Boolean isPrivate;

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public Boolean getisPublic() {
        return isPublic;
    }

    public void setisPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getisPrivate() {
        return isPrivate;
    }

    public void setisPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

}
