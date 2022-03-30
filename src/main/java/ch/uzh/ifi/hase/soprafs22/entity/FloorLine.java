package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;

public class FloorLine {

    private ArrayList<Integer> tiles;

    private int length;

    private boolean isOccupied;

    private int minusCount;


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
}
