package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.FloorLine;
import org.dom4j.rule.Pattern;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class FloorLineTest {

    @Test
    public void constructorCorrect() {
        FloorLine testFloorLine = new FloorLine();
        assertEquals(testFloorLine.getLength(),0);
        assertEquals(testFloorLine.isOccupied(),false);
        assertEquals(testFloorLine.getMinusCount(),0);
    }

    @Test
    public void tilePlacement() {
        FloorLine testFloorLine = new FloorLine();
        testFloorLine.placeTile(2);
        assertEquals(testFloorLine.getLength(),1);
        assertEquals(testFloorLine.isOccupied(),true);
        assertEquals(testFloorLine.getMinusCount(),1);
    }

    @Test
    public void scoring() {
        FloorLine testFloorLine = new FloorLine();
        testFloorLine.placeTile(2);
        assertEquals(testFloorLine.processEndOfRound(),1);
        assertEquals(testFloorLine.getLength(),0);
        assertEquals(testFloorLine.isOccupied(),false);
        assertEquals(testFloorLine.getMinusCount(),0);
    }
}
