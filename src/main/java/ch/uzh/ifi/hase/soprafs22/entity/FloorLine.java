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

        length = tiles.size();

        if (length > -1 && length <= 1) {
            minusCount += 1;
            tiles.add(colorIndex);
            isOccupied = true;
            length = tiles.size();
        }

        else if (length > 1 && length <= 4) {
            minusCount += 2;
            tiles.add(colorIndex);
            isOccupied = true;
            length = tiles.size();
        }

        else if (length > 4 && length <= 6){
            minusCount += 3;
            tiles.add(colorIndex);
            isOccupied = true;
            length = tiles.size();
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
