package ch.uzh.ifi.hase.soprafs22.entity;

import java.io.Serializable;
import ch.uzh.ifi.hase.soprafs22.entity.Game;
import org.json.JSONObject;


public class Player implements Serializable {

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

    public JSONObject jsonify() {
        JSONObject json = new JSONObject();
        json.put("playerId", playerId);
        json.put("score", score);
        json.put("playerBoard", playerBoard.jsonify());
        return json;
    }



}
