package ch.uzh.ifi.hase.soprafs22.entity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void validFactoryMove() {
        Game testGame = new Game(2);
        JSONObject json = testGame.jsonify();
        JSONArray factories = (JSONArray) json.get("factories");
        JSONObject factory = (JSONObject) factories.get(0);
        Integer[] colorAmounts = (Integer[]) factory.get("colorAmounts");

        int colorIndex = 0;
        int tileAmount = 0;

        //find first nonzero entry in colorAmounts, create Move with this colorIndex and tileAmount
        for (int i = 0; i < 5; i++) {
            if (colorAmounts[i] != 0) {
                colorIndex = i;
                tileAmount = colorAmounts[i];
                break;
            }
        }


        Move testMove = new Move(0,colorIndex,4,tileAmount,0);

        assertTrue(testGame.checkIfMoveValid(testMove));
    }

    @Test
    public void invalidFactoryMove() {
        Game testGame = new Game(2);
        JSONObject json = testGame.jsonify();
        JSONArray factories = (JSONArray) json.get("factories");
        JSONObject factory = (JSONObject) factories.get(0);
        Integer[] colorAmounts = (Integer[]) factory.get("colorAmounts");

        int colorIndex = 0;
        int tileAmount = 0;

        //find first nonzero entry in colorAmounts, create Move with this colorIndex and tileAmount
        for (int i = 0; i < 5; i++) {
            if (colorAmounts[i] != 0) {
                colorIndex = i;
                tileAmount = colorAmounts[i];
                break;
            }
        }


        Move testMove = new Move(0,colorIndex,4,5,0);

        assertFalse(testGame.checkIfMoveValid(testMove));
    }

    @Test
    public void validMiddleMove() {
        Game testGame = new Game(2);
        JSONObject json = testGame.jsonify();
        JSONArray factories = (JSONArray) json.get("factories");
        JSONObject factory = (JSONObject) factories.get(0);
        Integer[] colorAmounts = (Integer[]) factory.get("colorAmounts");

        int colorIndex = 0;
        int tileAmount = 0;

        //find first nonzero entry in colorAmounts, create Move with this colorIndex and tileAmount
        for (int i = 0; i < 5; i++) {
            if (colorAmounts[i] != 0) {
                colorIndex = i;
                tileAmount = colorAmounts[i];
                colorAmounts[i] = 0;
                break;
            }
        }


        Move testMove = new Move(0,colorIndex,4,tileAmount,0);

        //execute the Move that takes tiles from the factory
        testGame.executeMove(testMove);

        colorIndex = 0;
        tileAmount = 0;

        //find first nonzero entry in colorAmounts, create Move with this colorIndex and tileAmount
        for (int i = 0; i < 5; i++) {
            if (colorAmounts[i] != 0) {
                colorIndex = i;
                tileAmount = colorAmounts[i];
                break;
            }
        }

        System.out.println(colorIndex);
        System.out.println(tileAmount);

        //create Move that takes tiles from middle, based on which leftover tiles have been placed there by executing the last move
        testMove = new Move(-1,colorIndex,3,tileAmount,1);

        assertTrue(testGame.checkIfMoveValid(testMove));


    }

    @Test
    public void invalidMiddleMove() {
        Game testGame = new Game(2);

        Move testMove = new Move(-1,0,4,1,0);

        //trying to take tiles from the middle should always fail if no factory moves have been made, since middle will be empty then, except for minus tile.
        assertFalse(testGame.checkIfMoveValid(testMove));


    }

    @Test
    public void roundNotOver() {
        //make sure that round is detected as ongoing upon invoking constructor
        Game testGame = new Game(2);
        assertFalse(testGame.isRoundOver());
    }

    @Test
    public void gameNotOver() {
        //make sure that game is detected as ongoing upon invoking constructor
        Game testGame = new Game(2);
        assertFalse(testGame.isGameOver());
    }

    
}
