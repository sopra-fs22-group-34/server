package ch.uzh.ifi.hase.soprafs22.entity;

import org.dom4j.rule.Pattern;

import java.util.ArrayList;

public class Stairs {

    private PatternLine[] patternLines = new PatternLine[5];

    //Initialize the 5 PatternLines, each with correct length
    Stairs(){
        for (int row = 0; row < 5; row++) {
            patternLines[row] = new PatternLine(row+1);
        }
    }

    public boolean checkIfMoveValid(Move attemptedMove) {
        PatternLine targetLine = patternLines[attemptedMove.getTargetRowIndex()];

        //if targetLine is empty, move is valid
        if (targetLine.getColorIndex() == -1) {
            return true;
        }

        //if targetLine is not empty, but not full either, and colors match, move is valid
        else if (targetLine.getColorIndex() == attemptedMove.getColorIndex()) {
            int spaceLeft = targetLine.getLength() - targetLine.getTilesAmount();
            if (spaceLeft > 0) {
                return true;
            }
            else {
                return false;
            }
        }

        //if targetLine not empty and colors don't match, move invalid
        else {
            return false;
        }
    }

    public int executeMove(Move move) {
        //place tiles in PatternLine, returns number of excess tiles that need to go into the FloorLine

        int targetRow = move.getTargetRowIndex();
        patternLines[targetRow].setColorIndex(move.getColorIndex());
        int length = patternLines[targetRow].getLength();
        int tilesAmount = patternLines[targetRow].getTilesAmount();
        int spaceLeft = length - tilesAmount;

        if (move.getTileAmount() <= spaceLeft) {
            patternLines[targetRow].setTilesAmount(tilesAmount + move.getTileAmount());
            return 0;
        }

        else {
            patternLines[targetRow].setTilesAmount(length);
            return move.getTileAmount() - spaceLeft;
        }
    }

    //Attempt to empty the PatternLine indicated by row
    public int emptyFullPatternLine(int row){
        return patternLines[row].emptyFullPatternLine();
    }
}
