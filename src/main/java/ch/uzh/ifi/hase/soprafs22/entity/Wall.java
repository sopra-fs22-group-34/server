package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;

public class Wall {

    private Boolean[][] colorIsOccupiedArray = new Boolean[5][5];

    private Boolean[][] positionIsOccupiedArray = new Boolean[5][5];
    

    public boolean isColorOccupied(int row, int colorIndex){
        //PLACEHOLDER
        return true;
    }


    public void placeTileInRowColumn(int row, int column){
        positionIsOccupiedArray[row][column] = true;
    }

    public int getScoreFromTilePlacement(int row, int column){
        return 0;
    }
}
