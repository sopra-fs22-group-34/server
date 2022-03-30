package ch.uzh.ifi.hase.soprafs22.entity;

public class Lobby  {

    private Long lobbyId;

    private Player host;

    private String lobbyName;

    private Boolean isPublic;

    private Boolean isPrivate;


    public Long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Long lobbyId) {
        this.lobbyId = lobbyId;
    }

    public Player getHost() {
        return host;
    }

    public void setHost(Player host) {
        this.host = host;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    //these are placeholders -> not yet implemented
    public void startGame() {

    }

    public void setNumberOfPlayers() {

    }

    public void setColorScheme() {

    }

    public void kickPlayer() {

    }


}
