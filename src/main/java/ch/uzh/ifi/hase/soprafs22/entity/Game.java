package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.List;


public class Game  {

    private List<Long> players;

    private int roundCount;

    private int currentPlayerIndex;

    private ArrayList<Factory> factories;

    private Middle middle;

    public List<Long> getPlayers() {
        return players;
    }

    public void setPlayers(List<Long> players) { this.players = players; }

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

    //assigns playerID from 1-4 when Game starts
    public List<Long> playersIndex(List<Long> players){
        List<Long> playersIndexEqualsTurnOrder = new ArrayList<>();
        //access the users in players and give them a Long which should be used as their playerID and for knowing which turn it is
        //hence every game has playerIDs from 1-4
        try{
            Long player1 = players.set(1, 1L);
            playersIndexEqualsTurnOrder.add(player1);
            Long player2 = players.set(2, 2L);
            playersIndexEqualsTurnOrder.add(player2);
            Long player3 = players.set(3, 3L);
            playersIndexEqualsTurnOrder.add(player3);
            Long player4 = players.set(4, 4L);
            playersIndexEqualsTurnOrder.add(player4);
        //if players is out of bounds because not 4 players are in there return the already finished playersIndex
        } catch (Exception e){return playersIndexEqualsTurnOrder;}
        return playersIndexEqualsTurnOrder;
    }
}
