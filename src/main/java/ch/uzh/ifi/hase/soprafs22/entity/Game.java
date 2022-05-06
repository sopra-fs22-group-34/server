package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.service.LobbyService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Game implements Serializable {

    int playerCount;
    int factoryCount;

    Player[] players;

    Factory[] factories;

    Middle middle = new Middle();

    int playerTurn = 0;

    public Game(int playerNumber) {
        playerCount = playerNumber;
        factoryCount = 2*playerCount+1;

        players = new Player[playerCount];

        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player(i);
        }

        factories = new Factory[factoryCount];

        for (int i = 0; i < factoryCount; i++) {
            factories[i] = new Factory();
        }

        int playerTurn = 0;

    }

    public boolean checkIfMoveValid(Move attemptedMove) {
        if (playerTurn != attemptedMove.getPlayerIndex()) {
            return false;
        }
        if (players[attemptedMove.getPlayerIndex()].checkIfMoveValid(attemptedMove) == false) {
            return false;
        }

        if (attemptedMove.getOriginIndex() == -1) {
            if (middle.checkIfMoveValid(attemptedMove) == false) {
                return false;
            }
        }

        else if (attemptedMove.getOriginIndex() < factoryCount) {
            if (factories[attemptedMove.getOriginIndex()].checkIfMoveValid(attemptedMove) == false) {
                return false;
            }
        }

        else {
            return false;
        }

        return true;
    }

    public void executeMove(Move move) {
        if (move.getOriginIndex() == -1) {
            middle.executeMove(move);
        }

        else {
            Integer[] leftoverTiles = factories[move.getOriginIndex()].executeMove(move);
            System.out.println(leftoverTiles);
            for (int i = 0; i < 5; i++) {
                System.out.println(leftoverTiles[i]);
            }

            middle.placeLeftoverTiles(leftoverTiles);
        }

        players[move.getPlayerIndex()].executeMove(move);

        if (isRoundOver()){
            processEndOfRound();
        }

        else {
            playerTurn = (playerTurn+1)%playerCount;
        }
    }

    public boolean isRoundOver() {
        boolean result = true;

        if (!middle.isEmpty()) {
            result = false;
        }

        for (int i = 0; i < factoryCount; i++) {
            if (!factories[i].isEmpty()) {
                result = false;
            }
        }

        return result;
    }

    public void processEndOfRound() {
        for (int i = 0; i < playerCount; i++) {
            players[i].processEndOfRound();
        }

        if (isGameOver()) {
            processEndOfGame();
        }

        for (int i = 0; i < factoryCount; i++) {
            factories[i] = new Factory();
        }

        middle = new Middle();

    }

    public boolean isGameOver() {
        for (int i = 0; i < playerCount; i++) {
            if (players[i].hasFullWallRow()) {
                return true;
            }
        }

        return false;
    }

    public void processEndOfGame() {

    }

    public JSONObject jsonify() {
        JSONObject json = new JSONObject();
        json.put("middle",middle.jsonify());
        json.put("factoryAmount",factoryCount);

        JSONArray factoryArray = new JSONArray();
        for (int i = 0; i < factoryCount; i++) {
            factoryArray.put(factories[i].jsonify());
        }
        json.put("factories", factoryArray);

        json.put("playerAmount", playerCount);

        JSONArray playersArray = new JSONArray();
        for (int i = 0; i < playerCount; i++) {
            playersArray.put(players[i].jsonify());
        }
        json.put("players", playersArray);

        json.put("playerTurnId", playerTurn);

        return json;
    }
}
