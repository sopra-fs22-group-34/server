package ch.uzh.ifi.hase.soprafs22.entity;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public int getLength() {
        return length;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public int getMinusCount() {
        return minusCount;
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
        length = 0;
        isOccupied = false;
        minusCount = 0;
        return minusPoints;
    }

    public JSONArray jsonify() {
        JSONArray array = new JSONArray();
        for(int i = 0; i < tiles.size(); i++) {
            array.put(tiles.get(i));
        }
        return array;
    }
}
