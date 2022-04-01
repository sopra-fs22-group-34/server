package ch.uzh.ifi.hase.soprafs22.entity;

import org.dom4j.rule.Pattern;

import java.util.ArrayList;

public class Stairs {

    private PatternLine[] patternLines = new PatternLine[5];


    public int emptyFullPatternLine(int row){
        return patternLines[row].emptyFullPatternLine();
    }
}
