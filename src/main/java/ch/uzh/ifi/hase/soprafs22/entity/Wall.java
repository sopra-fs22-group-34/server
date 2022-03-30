package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;

public class Wall {

    private ArrayList<ArrayList<Boolean>> colorIsOccupiedArray;

    private ArrayList<ArrayList<Boolean>> positionIsOccupiedArray;

    public ArrayList<ArrayList<Boolean>> getColorIsOccupiedArray() {
        return colorIsOccupiedArray;
    }

    public void setColorIsOccupiedArray(ArrayList<ArrayList<Boolean>> colorIsOccupiedArray) {
        this.colorIsOccupiedArray = colorIsOccupiedArray;
    }

    public ArrayList<ArrayList<Boolean>> getPositionIsOccupiedArray() {
        return positionIsOccupiedArray;
    }

    public void setPositionIsOccupiedArray(ArrayList<ArrayList<Boolean>> positionIsOccupiedArray) {
        this.positionIsOccupiedArray = positionIsOccupiedArray;
    }
}
