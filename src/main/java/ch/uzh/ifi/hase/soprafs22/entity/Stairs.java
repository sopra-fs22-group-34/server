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

    public void executeMove(Move move) {

        //TODO: add excess tiles to FloorLine
        patternLines[move.getTargetRowIndex()].setColorIndex(move.getColorIndex());
        patternLines[move.getTargetRowIndex()].setTilesAmount(move.getTileAmount());
    }

    //Attempt to empty the PatternLine indicated by row
    public int emptyFullPatternLine(int row){
        return patternLines[row].emptyFullPatternLine();
    }
}
