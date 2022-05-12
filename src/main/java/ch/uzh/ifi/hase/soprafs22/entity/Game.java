package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.service.LobbyService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Game implements Serializable {

    boolean gameOver;

    int playerCount;
    int factoryCount;

    Player[] players;
    Player[] activePlayers;

    Factory[] factories;

    Middle middle = new Middle();

    int playerTurn;
    int firstNextTurn;

    public Game(int playerNumber) {

        gameOver = false;

        playerCount = playerNumber;
        factoryCount = 2*playerCount+1;

        players = new Player[playerCount];

        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player(i);
        }

        activePlayers = players.clone();

        factories = new Factory[factoryCount];

        for (int i = 0; i < factoryCount; i++) {
            factories[i] = new Factory();
        }

        playerTurn = 0;
        // TODO: make this random instead of always 0
        // (Using getRandomNumber(playerCount) causes some tests to fail)

    }

    public int getRandomNumber(int max) {
        Random generator = new Random();
        return generator.nextInt(max);
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
            boolean minusTilePickedUp = middle.executeMove(move);
            if (minusTilePickedUp){
                firstNextTurn = move.getPlayerIndex();
                players[move.getPlayerIndex()].placeMinusTile();
            }
        }
        else {
            Integer[] leftoverTiles = factories[move.getOriginIndex()].executeMove(move);
            middle.placeLeftoverTiles(leftoverTiles);
        }

        players[move.getPlayerIndex()].executeMove(move);

        if (isRoundOver()){
            processEndOfRound();
        }

        else {
            nextTurn();
        }
    }

    public void nextTurn(){
        do {
            playerTurn = (playerTurn+1)%playerCount;
        } // repeat process if player is no longer in game
        while (activePlayers[playerTurn] == null);
    }

    public void leaveGame(int id){
        activePlayers[id] = null;
        if (activePlayersCount() < 2) processEndOfGame();
        if (playerTurn == id) nextTurn();
    }

    public int activePlayersCount(){
        int activePlayersAmount = 0;
        for (int i = 0; i < playerCount; i++){
            if (activePlayers[i] == null) activePlayersAmount++;
        }
        return activePlayersAmount;
    }

    public boolean isRoundOver() {
        boolean result = middle.isEmpty();

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
        playerTurn = firstNextTurn;

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

        JSONArray activePlayersArray = new JSONArray();
        for (int i = 0; i < playerCount; i++) {
            if (activePlayers[i] != null) {
                activePlayersArray.put(i);
            } else { activePlayersArray.put("x"); }
        }
        json.put("activePlayers", activePlayersArray);

        json.put("playerTurnId", playerTurn);

        json.put("gameOver", gameOver);

        return json;
    }
}
