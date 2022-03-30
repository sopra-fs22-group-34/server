package ch.uzh.ifi.hase.soprafs22.entity;

import java.io.Serializable;


public class Player {

    private Long playerId;

    private PlayerBoard playerBoard;

    public int score;

    public String playerName;

    public boolean loggedIn;

    public boolean playersTurn;


    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(PlayerBoard playerBoard) {
        this.playerBoard = playerBoard;
    }
}
