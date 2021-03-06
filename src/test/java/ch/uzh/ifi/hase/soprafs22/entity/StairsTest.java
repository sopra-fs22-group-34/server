package ch.uzh.ifi.hase.soprafs22.entity;


import ch.uzh.ifi.hase.soprafs22.entity.Stairs;
import ch.uzh.ifi.hase.soprafs22.entity.PatternLine;
import org.junit.jupiter.api.Test;
import org.json.*;
import org.skyscreamer.jsonassert.JSONAssert;

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
        Move testMove = new Move(0,2,1,1,0);
        testStairs.executeMove(testMove);
        assertEquals(-1,testStairs.emptyFullPatternLine(1));
    }

    @Test
    public void fullPatternLine() {
        Stairs testStairs = new Stairs();
        Move testMove = new Move(0,2,1,2,0);
        testStairs.executeMove(testMove);
        assertEquals(2,testStairs.emptyFullPatternLine(1));
    }

    @Test
    public void emptyPatternLineMoveValid() {
        Stairs testStairs = new Stairs();
        Move testMove = new Move(0,0,0,1,0);
        assertTrue(testStairs.checkIfMoveValid(testMove));
    }

    @Test
    public void emptyPatternLineOverFillMoveValid() {
        Stairs testStairs = new Stairs();
        Move testMove = new Move(0,0,0,2,0);
        assertTrue(testStairs.checkIfMoveValid(testMove));
    }

    @Test
    public void matchingColorPatternLineMoveValid() {
        Stairs testStairs = new Stairs();
        Move firstMove = new Move(0,0,1,1,0);
        testStairs.executeMove(firstMove);
        Move testMove = new Move(0,0,1,1,0);
        assertTrue(testStairs.checkIfMoveValid(testMove));
    }

    @Test
    public void matchingColorPatternLineOverFillMoveValid() {
        Stairs testStairs = new Stairs();
        Move firstMove = new Move(0, 0, 1, 1, 0);
        testStairs.executeMove(firstMove);
        Move testMove = new Move(0, 0, 1, 2, 0);
        assertTrue(testStairs.checkIfMoveValid(testMove));
    }

    @Test
    public void matchingColorPatternLineNoSpaceMoveInvalid() {
        Stairs testStairs = new Stairs();
        Move firstMove = new Move(0,0,1,2,0);
        testStairs.executeMove(firstMove);
        Move testMove = new Move(0,0,1,1,0);
        assertFalse(testStairs.checkIfMoveValid(testMove));
    }

    @Test
    public void wrongColorPatternLineMoveInvalid() {
        Stairs testStairs = new Stairs();
        Move firstMove = new Move(0,0,1,1,0);
        testStairs.executeMove(firstMove);
        Move testMove = new Move(0,1,1,1,0);
        assertFalse(testStairs.checkIfMoveValid(testMove));
    }

    @Test
    public void executeMoveNoExcessTiles() {
        Stairs testStairs = new Stairs();
        Move testMove = new Move(0,1,1,1,0);
        assertEquals(0, testStairs.executeMove(testMove));
    }

    @Test
    public void executeMoveExcessTiles() {
        Stairs testStairs = new Stairs();
        Move testMove = new Move(0,1,1,3,0);
        assertEquals(1, testStairs.executeMove(testMove));
    }

    @Test
    void jsonifyTest(){
        Stairs testStairs = new Stairs();
        JSONArray json = testStairs.jsonify();
        String expected = "[{\"length\":1,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":2,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":3,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":4,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":5,\"colorIndex\":-1,\"tilesAmount\":0}]";
        assertEquals(expected, json.toString());
    }
}
