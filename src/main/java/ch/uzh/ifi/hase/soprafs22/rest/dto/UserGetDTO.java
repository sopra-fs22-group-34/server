package ch.uzh.ifi.hase.soprafs22.rest.dto;

import java.util.Date;

public class UserGetDTO {

  private Long id;
  private String username;
  private Date creation_date;
  private boolean logged_in;
  private Long lobby;
  private int score;
  private int games;
  private boolean is_public;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public boolean getLogged_in() {
    return logged_in;
  }

  public void setLogged_in(boolean logged_in) {
    this.logged_in = logged_in;
  }

  public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }
  public Date getCreation_date() {
        return creation_date;
    }

    public void setLobby(Long lobby) {
        this.lobby = lobby;
    }
    public Long getLobby() {
        return lobby;
    }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public int getGames() { return games; }

    public void setGames(int games) { this.games = games; }

    public boolean getIs_public() { return is_public; }

    public void setIs_public(boolean is_public) { this.is_public = is_public; }
}
