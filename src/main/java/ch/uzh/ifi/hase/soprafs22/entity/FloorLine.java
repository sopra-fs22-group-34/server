package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;

public class FloorLine {

    private ArrayList<Integer> tiles;

    private int length;

    private boolean isOccupied;

    //This variable keeps track of minus points, however it is always positive. e.g 9 means 9 minus points.
    private int minusCount;


    FloorLine() {
        tiles = new ArrayList<Integer>();
        length = 0;
        isOccupied = false;
        minusCount = 0;
    }


    public ArrayList<Integer> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Integer> tiles) {
        this.tiles = tiles;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public int getMinusCount() {
        return minusCount;
    }

    public void setMinusCount(int minusCount) {
        this.minusCount = minusCount;
    }


    public void placeTile(int colorIndex) {

        //TODO: discuss how scoring works exactly
        tiles.add(colorIndex);
        isOccupied = true;
        length = tiles.size();

        if (length > 0 && length <= 2) {
            minusCount += 1;
        }

        else if (length > 2 && length <= 5) {
            minusCount += 2;
        }

        else {
            minusCount += 3;
        }

    }


    public int processEndOfRound() {
        int minusPoints = getMinusCount();
        tiles.clear();
        setLength(0);
        isOccupied = false;
        setMinusCount(0);
        return minusPoints;
    }

    public void occupy() {

    }
}
