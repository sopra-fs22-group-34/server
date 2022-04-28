package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.Middle;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class MiddleTest {


    @Test
    public void constructorCorrect() {
        Middle testMiddle = new Middle();
        int sum = 0;
        for (int value : testMiddle.getColorAmounts()) {
            sum += value;
        }

        assertEquals(0, sum);
    }

    @Test
    public void leftoverTilePlacement() {
        Middle testMiddle = new Middle();
        Integer[] leftoverTiles = {0,1,1,0,0};

        //ensure leftover tiles are placed correctly
        testMiddle.placeLeftoverTiles(leftoverTiles);
        assertArrayEquals(testMiddle.getColorAmounts(), leftoverTiles);
    }

    @Test
    public void checkValidMove() {
        Middle testMiddle = new Middle();

        Integer[] leftoverTiles = {0,2,1,0,0};
        testMiddle.placeLeftoverTiles(leftoverTiles);


        Move testMove = new Move(0,1,0,2,0);
        assertTrue(testMiddle.checkIfMoveValid(testMove));
    }

    @Test
    public void checkInvalidMoveTooManyTiles() {
        Middle testMiddle = new Middle();

        Integer[] leftoverTiles = {0,1,1,0,0};
        testMiddle.placeLeftoverTiles(leftoverTiles);


        Move testMove = new Move(0,1,0,2,0);
        assertFalse(testMiddle.checkIfMoveValid(testMove));
    }

    @Test
    public void executeMoveTest() {
        Middle testMiddle = new Middle();

        Integer[] leftoverTiles = {0,2,1,0,0};
        testMiddle.placeLeftoverTiles(leftoverTiles);

        //check if the first move correctly returns that the minus tile is to be picked up
        Move testMoveOne = new Move(0,1,0,2,0);
        assertTrue(testMiddle.executeMove(testMoveOne));

        Integer[] expectedColorAmounts = {0,0,1,0,0};

        assertArrayEquals(expectedColorAmounts, testMiddle.getColorAmounts());

        //check if the first move correctly returns that the minus tile is to be picked up
        Move testMoveTwo = new Move(0,2,0,1,0);
        assertFalse(testMiddle.executeMove(testMoveTwo));
    }

    @Test
    public void isEmptyTest() {
        Middle testMiddle = new Middle();

        assertTrue(testMiddle.isEmpty());

        Integer[] leftoverTiles = {0,2,1,0,0};
        testMiddle.placeLeftoverTiles(leftoverTiles);

        assertFalse(testMiddle.isEmpty());
    }

    @Test
    void jsonifyTest(){
        Middle testMiddle = new Middle();
        JSONObject json = testMiddle.jsonify();
        String expected = "{\"hasMinusTile\":true,\"colorAmounts\":[0,0,0,0,0]}";
        assertEquals(expected, json.toString());
    }
}
