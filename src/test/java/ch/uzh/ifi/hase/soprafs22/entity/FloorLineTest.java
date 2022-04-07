package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.FloorLine;
import org.dom4j.rule.Pattern;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class FloorLineTest {

    @Test
    public void constructorCorrect() {
        FloorLine testFloorLine = new FloorLine();
        assertEquals(0,testFloorLine.getLength());
        assertEquals(false,testFloorLine.isOccupied());
        assertEquals(0,testFloorLine.getMinusCount());
    }

    @Test
    public void tilePlacement() {
        FloorLine testFloorLine = new FloorLine();
        testFloorLine.placeTile(2);
        assertEquals(1,testFloorLine.getLength());
        assertEquals(true,testFloorLine.isOccupied());
        assertEquals(1,testFloorLine.getMinusCount());
    }

    @Test
    public void scoring() {
        FloorLine testFloorLine = new FloorLine();
        testFloorLine.placeTile(2);
        assertEquals(1,testFloorLine.processEndOfRound());
        assertEquals(0,testFloorLine.getLength());
        assertEquals(false,testFloorLine.isOccupied());
        assertEquals(0,testFloorLine.getMinusCount());
    }
}
