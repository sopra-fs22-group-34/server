package ch.uzh.ifi.hase.soprafs22.entity;

import org.json.JSONArray;
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
    public void tilePlacementNotFullFloorLine() {
        FloorLine testFloorLine = new FloorLine();
        testFloorLine.placeTile(2);
        testFloorLine.placeTile(3);
        testFloorLine.placeTile(0);
        testFloorLine.placeTile(1);
        testFloorLine.placeTile(2);
        assertEquals(5,testFloorLine.getLength());
        assertEquals(true,testFloorLine.isOccupied());
        assertEquals(8,testFloorLine.getMinusCount());
    }

    @Test
    public void tilePlacementOverFullFloorLine() {
        FloorLine testFloorLine = new FloorLine();
        testFloorLine.placeTile(2);
        testFloorLine.placeTile(3);
        testFloorLine.placeTile(0);
        testFloorLine.placeTile(1);
        testFloorLine.placeTile(2);
        testFloorLine.placeTile(3);
        testFloorLine.placeTile(2);
        testFloorLine.placeTile(1);
        testFloorLine.placeTile(1);
        assertEquals(7,testFloorLine.getLength());
        assertEquals(true,testFloorLine.isOccupied());
        assertEquals(14,testFloorLine.getMinusCount());
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

    @Test
    public void jsonifyTest() {
        FloorLine testFloorLine = new FloorLine();
        JSONArray json = testFloorLine.jsonify();
        String expected = "[]";
        assertEquals(expected,json.toString());

        testFloorLine.placeTile(2);
        testFloorLine.placeTile(4);
        json = testFloorLine.jsonify();
        expected = "[2,4]";
        assertEquals(expected,json.toString());

    }
}
