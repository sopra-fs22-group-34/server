package ch.uzh.ifi.hase.soprafs22.entity;


import org.springframework.beans.factory.annotation.Configurable;

import javax.annotation.Resource;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
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

    @OneToMany
    @JoinColumn(nullable = false) //Go read les docs in case this does not work we just used @JoinColumn.
    private List<User> users = new ArrayList<>(); //IMPORTANT: define a list always like this! It will not give you specific errors.


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


}
