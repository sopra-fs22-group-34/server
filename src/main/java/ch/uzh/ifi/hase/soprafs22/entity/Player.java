package ch.uzh.ifi.hase.soprafs22.entity;

import java.io.Serializable;
import ch.uzh.ifi.hase.soprafs22.entity.Game;


public class Player {

    private int playerId;

    private PlayerBoard playerBoard;

    private int score;

    Player(int playerId) {
        this.playerId = playerId;
        this.playerBoard = new PlayerBoard();
        this.score = 0;
    }


    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public int getScore(){
        return score;
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

    public boolean hasFullWallRow() {
        return playerBoard.hasFullWallRow();
    }




}
