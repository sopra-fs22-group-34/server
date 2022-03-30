package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;


public class Game  {

    private ArrayList<Player> players;

    private int roundCount;

    private int currentPlayerIndex;

    private ArrayList<Factory> factories;

    private Middle middle;


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public ArrayList<Factory> getFactories() {
        return factories;
    }

    public void setFactories(ArrayList<Factory> factories) {
        this.factories = factories;
    }

    public Middle getMiddle() {
        return middle;
    }

    public void setMiddle(Middle middle) {
        this.middle = middle;
    }

    //these are placeholders -> not yet implemented
    public void setupGame() {

    }

    public void playGame() {

    }

    public void startNextRound() {

    }

    public void endGame() {

    }

    //just returned some score so no error is thrown
    public int countScore() {
        return 420;
    }

    public void takeTileFromFactory() {

    }

    public void takeTileFromMiddle() {

    }

    public void takeMinusStone() {

    }

    public void placeTileOnStairs() {

    }

    public void moveTileToWall() {

    }

    public void placeTileOnFloorLine() {

    }

    public void nextPlayerTurn() {

    }

    public void viewPlayerBoard() {

    }
}
