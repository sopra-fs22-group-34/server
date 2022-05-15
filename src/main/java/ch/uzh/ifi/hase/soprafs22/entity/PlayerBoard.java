package ch.uzh.ifi.hase.soprafs22.entity;


import org.json.JSONObject;

import java.io.Serializable;

public class PlayerBoard implements Serializable {

    private Stairs stairs;

    private Wall wall;

    private FloorLine floorLine;

    PlayerBoard() {
        stairs = new Stairs();
        wall = new Wall();
        floorLine = new FloorLine();
    }


    public Stairs getStairs() {
        return stairs;
    }

    public Wall getWall() {
        return wall;
    }

    public FloorLine getFloorLine() {
        return floorLine;
    }

    public void placeMinusTile() { floorLine.placeMinusTile(); }

    public int processEndOfRound(){
        int scoreDifference = 0;

        for (int row = 0; row < 5; row++) {
            int returnedColor = stairs.emptyFullPatternLine(row);
            if (returnedColor != -1){
                scoreDifference += wall.placeTileInRowAndColor(row, returnedColor);
            }
        }

        scoreDifference -= floorLine.processEndOfRound();

        return scoreDifference;
    }

    public boolean checkIfMoveValid(Move attemptedMove) {
        if (attemptedMove.getTargetRowIndex() == -1) {
            return true;
        }
        boolean wallSpotOccupied = wall.isColorOccupied(attemptedMove.getTargetRowIndex(), attemptedMove.getColorIndex());
        if (!wallSpotOccupied) {
            return stairs.checkIfMoveValid(attemptedMove);
        }

        else {
            return false;
        }

    }

    public void executeMove(Move move) {
        if (move.getTargetRowIndex()==-1) {
            for (int i = 0; i < move.getTileAmount(); i++) {
                floorLine.placeTile(move.getColorIndex());
            }
        }
        else {
            int excessTiles = stairs.executeMove(move);
            //place excess tiles in FloorLine

            for (int i = 0; i < excessTiles; i++) {
                floorLine.placeTile(move.getColorIndex());
            }
        }

    }

    public boolean hasFullWallRow() {
        return wall.hasFullWallRow();
    }

    public JSONObject jsonify() {
        JSONObject json = new JSONObject();
        json.put("wall", wall.jsonify());
        json.put("stairs", stairs.jsonify());
        json.put("floorLine", floorLine.jsonify());
        return json;
    }

}
