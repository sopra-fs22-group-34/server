package ch.uzh.ifi.hase.soprafs22.entity;

import java.io.Serializable;
import ch.uzh.ifi.hase.soprafs22.entity.Game;


public class Player {

    private Long playerId;

    private PlayerBoard playerBoard;

    private int score;

    public String playerName;

    private boolean playersTurn;

    Player(Long playerId, String playerName) {
        this.playerId = playerId;
        this.playerBoard = new PlayerBoard();
        this.playerName = playerName;
        this.score = 0;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public int getScore(){
        return score;
    }

    public boolean getPlayersTurn(){
        return playersTurn;
    }

    public boolean checkIfMoveValid(Move attemptedMove) {
        return playerBoard.checkIfMoveValid(attemptedMove);
    }

    public void executeMove(Move move) {
        playerBoard.executeMove(move);
    }

    public void processEndOfRound() {
        score += playerBoard.processEndOfRound();
        if (score < 0) {
            score = 0;
        }
    }




}
