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
        return false;
    }

    //Attempt to empty the PatternLine indicated by row
    public int emptyFullPatternLine(int row){
        return patternLines[row].emptyFullPatternLine();
    }
}
