package ch.uzh.ifi.hase.soprafs22.entity;


import org.springframework.beans.factory.annotation.Configurable;

import javax.annotation.Resource;
import javax.persistence.*;

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
    private Long lobbyId;

    @OneToOne
    @JoinColumn(nullable = false)
    private User host;

    @Column(nullable = false)
    private String lobbyName;

    @Column(nullable = false)
    private Boolean isPublic;

    @Column(nullable = false)
    private Boolean isPrivate;


    public Long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Long lobbyId) {
        this.lobbyId = lobbyId;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
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

    // these are placeholders -> not yet implemented;
    // If Lobby is stored in a JPA Repo they need to get moved in LobbyService anyway

    /**
    public void startGame() {

    }

    public void setNumberOfPlayers() {

    }

    public void setColorScheme() {

    }

    public void kickPlayer() {

    }
    **/

}
