package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.PatternLine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatternLineTest {

    @Test
    public void constructorCorrect() {
        PatternLine testPatternLine = new PatternLine(3);
        assertEquals(testPatternLine.getLength(),3);
        assertEquals(testPatternLine.getTilesAmount(),0);
        assertEquals(testPatternLine.getColorIndex(),-1);
    }

    @Test
    public void notFullPatternLine() {
        PatternLine testPatternLine = new PatternLine(4);
        testPatternLine.setTilesAmount(3);
        testPatternLine.setColorIndex(2);

        assertEquals(testPatternLine.emptyFullPatternLine(), -1);
    }

    @Test
    public void fullPatternLine() {
        PatternLine testPatternLine = new PatternLine(4);
        testPatternLine.setTilesAmount(4);
        testPatternLine.setColorIndex(2);

        assertEquals(testPatternLine.emptyFullPatternLine(), 2);
        assertEquals(testPatternLine.getTilesAmount(),0);
        assertEquals(testPatternLine.getColorIndex(),-1);
        assertEquals(testPatternLine.getLength(),4);
    }



}
