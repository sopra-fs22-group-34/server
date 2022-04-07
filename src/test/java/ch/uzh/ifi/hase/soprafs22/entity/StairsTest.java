package ch.uzh.ifi.hase.soprafs22.entity;


import ch.uzh.ifi.hase.soprafs22.entity.Stairs;
import ch.uzh.ifi.hase.soprafs22.entity.PatternLine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StairsTest {

    @Test
    public void constructorCorrect() {
        Stairs testStairs = new Stairs();
        assertEquals(-1,testStairs.emptyFullPatternLine(0));
        assertEquals(-1,testStairs.emptyFullPatternLine(1));
        assertEquals(-1,testStairs.emptyFullPatternLine(2));
        assertEquals(-1,testStairs.emptyFullPatternLine(3));
        assertEquals(-1,testStairs.emptyFullPatternLine(4));
    }

    @Test
    public void notFullPatternLine() {
        Stairs testStairs = new Stairs();
        assertEquals(-1,testStairs.emptyFullPatternLine(0));
    }

    //TODO: add test for emptying full pattern line, need methods to place tiles in PatternLine first though
}
