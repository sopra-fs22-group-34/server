package ch.uzh.ifi.hase.soprafs22.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how
 * the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unique across the database -> composes
 * the primary key
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;

  @Column()
  private String name;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private Date creation_date;

  @Column(nullable = false)
  private boolean logged_in;

  @Column()
  private Long lobby;

  @Column()
  private int score;

  @Column()
  private int games;

  @Column()
  private boolean is_public;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String token;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public boolean getLogged_in() {
    return logged_in;
  }

  public void setLogged_in(boolean logged_in) {
    this.logged_in = logged_in;
  }

  public Long getLobby() { return lobby; }

  public void setLobby(Long lobby) { this.lobby = lobby; }

  public String getPassword() { return password; }

  public void setPassword(String password) { this.password = password; }

  public void setCreation_date(Date creation_date) { this.creation_date = creation_date; }

  public Date getCreation_date() { return creation_date; }

  public int getScore() { return score; }

  public void setScore(int score) { this.score = score; }

  public int getGames() { return games; }

  public void setGames(int games) { this.games = games; }

  public boolean getIs_public() { return is_public; }

  public void setIs_public(boolean is_public) { this.is_public = is_public; }
}
