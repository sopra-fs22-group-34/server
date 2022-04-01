package ch.uzh.ifi.hase.soprafs22.entity;


import ch.uzh.ifi.hase.soprafs22.entity.Stairs;
import ch.uzh.ifi.hase.soprafs22.entity.PatternLine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StairsTest {

    @Test
    public void constructorCorrect() {
        Stairs testStairs = new Stairs();
        assertEquals(testStairs.emptyFullPatternLine(0),-1);
        assertEquals(testStairs.emptyFullPatternLine(1),-1);
        assertEquals(testStairs.emptyFullPatternLine(2),-1);
        assertEquals(testStairs.emptyFullPatternLine(3),-1);
        assertEquals(testStairs.emptyFullPatternLine(4),-1);
    }

    @Test
    public void notFullPatternLine() {
        Stairs testStairs = new Stairs();
        assertEquals(testStairs.emptyFullPatternLine(0),-1);
    }

    //TODO: add test for emptying full pattern line, need methods to place tiles in PatternLine first though
}
