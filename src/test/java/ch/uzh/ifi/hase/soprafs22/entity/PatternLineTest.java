package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.PatternLine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatternLineTest {

    @Test
    public void constructorCorrect() {
        PatternLine testPatternLine = new PatternLine(3);
        assertEquals(3,testPatternLine.getLength());
        assertEquals(0,testPatternLine.getTilesAmount());
        assertEquals(-1,testPatternLine.getColorIndex());
    }

    @Test
    public void notFullPatternLine() {
        PatternLine testPatternLine = new PatternLine(4);
        testPatternLine.setTilesAmount(3);
        testPatternLine.setColorIndex(2);

        assertEquals(-1,testPatternLine.emptyFullPatternLine());
    }

    @Test
    public void fullPatternLine() {
        PatternLine testPatternLine = new PatternLine(4);
        testPatternLine.setTilesAmount(4);
        testPatternLine.setColorIndex(2);

        assertEquals(2,testPatternLine.emptyFullPatternLine());
        assertEquals(0,testPatternLine.getTilesAmount());
        assertEquals(-1,testPatternLine.getColorIndex());
        assertEquals(4,testPatternLine.getLength());
    }



}
