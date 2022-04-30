package ch.uzh.ifi.hase.soprafs22.entity;

import org.json.*;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.*;

public class Wall implements Serializable {

    private Boolean[][] colorIsOccupiedArray = new Boolean[5][5];

    private Boolean[][] positionIsOccupiedArray = new Boolean[5][5];

    //initialize both arrays to all false
    Wall() {
        for (int row = 0; row < 5; row++) {
            for (int colorOrColumn = 0; colorOrColumn < 5; colorOrColumn++) {
                colorIsOccupiedArray[row][colorOrColumn] = false;
                positionIsOccupiedArray[row][colorOrColumn] = false;
            }
        }
    }


    public boolean isColorOccupied(int row, int colorIndex) {
        return colorIsOccupiedArray[row][colorIndex];
    }

    public boolean isColumnOccupied(int row, int column) {
        return positionIsOccupiedArray[row][column];
    }


    public int placeTileInRowAndColor(int row, int color) {

        colorIsOccupiedArray[row][color] = true;

        //need to offset the position one to the left for each row down from the top one, due to how the color pattern
        //is in Azul. Also need to use modulo, to ensure values going out of bounds are wrapped around

        int column = (color + row) % 5;

        positionIsOccupiedArray[row][column] = true;

        return gainedScoreFromTilePlacement(row, column);
    }


    public int gainedScoreFromTilePlacement(int row, int column) {

        //calculate score as sum of points from row formation and column formation
        int gainedScore = pointsFromFormedRow(row, column) + pointsFromFormedColumn(row, column);

        //if there are no bonus points from either row or column formation, grant base score of 1
        if (gainedScore == 0) {
            gainedScore = 1;
        }

        return gainedScore;
    }


    public int pointsFromFormedRow(int row, int column) {
        //amount of points gained from forming a row
        int rowScore = 1;

        //amount of spaces on the wall that are left of the placed tile and need to be checked for placed tiles
        int spacesNumberToLeft = 0 + column;

        //iteratively step to the left of the placed tile, if a tile is placed to the left, increment score and keep
        //going further left, otherwise stop
        for (int leftSteps = 1; leftSteps < spacesNumberToLeft + 1; leftSteps++){
            if (positionIsOccupiedArray[row][column - leftSteps]) {
                rowScore += 1;
            }
            else {
                break;
            }
        }


        //amount of spaces on the wall that are right of the placed tile and need to be checked for placed tiles
        int spacesNumberToRight = 4 - column;

        //iteratively step to the right of the placed tile, if a tile is placed to the right, increment score and keep
        //going further right, otherwise stop
        for (int rightSteps = 1; rightSteps < spacesNumberToRight + 1; rightSteps++){
            if (positionIsOccupiedArray[row][column + rightSteps]) {
                rowScore += 1;
            }
            else {
                break;
            }
        }


        //if no row was formed, no special points for row formation are given
        if (rowScore == 1) {
            rowScore = 0;
        }

        //if full horizontal row was formed, grant 2 bonus points
        if (rowScore == 5) {
            rowScore = 7;
        }

        return rowScore;
    }


    public int pointsFromFormedColumn(int row, int column) {
        //amount of points gained from forming a column
        int columnScore = 1;

        //amount of spaces on the wall that are upward of the placed tile and need to be checked for placed tiles
        int spacesNumberUpward = 0 + row;

        //iteratively step upward of the placed tile, if a tile is placed upward, increment score and keep
        //going further up, otherwise stop
        for (int upSteps = 1; upSteps < spacesNumberUpward + 1; upSteps++){
            if (positionIsOccupiedArray[row - upSteps][column]) {
                columnScore += 1;
            }
            else {
                break;
            }
        }


        //amount of spaces on the wall that are downward of the placed tile and need to be checked for placed tiles
        int spacesNumberDownward = 4 - row;

        //iteratively step upward of the placed tile, if a tile is placed upward, increment score and keep
        //going further up, otherwise stop
        for (int downSteps = 1; downSteps < spacesNumberDownward + 1; downSteps++){
            if (positionIsOccupiedArray[row + downSteps][column]) {
                columnScore += 1;
            }
            else {
                break;
            }
        }

        //if no column was formed, no special points for column formation are given
        if (columnScore == 1) {
            columnScore = 0;
        }

        //if full vertical row was formed, grant 7 bonus points
        if (columnScore == 5) {
            columnScore = 12;
        }

        return columnScore;
    }

    public boolean hasFullWallRow() {
        for (int row = 0; row < 5; row++) {

            //for every individual row, check whether all positions are occupied
            boolean rowIsFull = true;
            for (int column = 0; column < 5; column++) {
                if (positionIsOccupiedArray[row][column] == false) {
                    rowIsFull = false;
                }
            }

            //if a row is full, return true
            if (rowIsFull) {
                return true;
            }
        }

        //if loop was fully iterated through and no full row found, return false
        return false;
    }

    public JSONObject jsonify() {
        JSONObject json = new JSONObject();
        json.put("colorsOccupied", colorIsOccupiedArray);
        json.put("positionsOccupied", positionIsOccupiedArray);
        return json;
    }
}
