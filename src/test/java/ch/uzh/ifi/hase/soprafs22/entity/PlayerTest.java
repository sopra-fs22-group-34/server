package ch.uzh.ifi.hase.soprafs22.entity;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {


    @Test
    public void processEndOfRound() {
        Player testPlayer = new Player(0);
        testPlayer.processEndOfRound();
        assertEquals(0, testPlayer.getScore());

        Move testMoveOne = new Move(0,1,1,2,0);
        testPlayer.executeMove(testMoveOne);
        testPlayer.processEndOfRound();
        assertEquals(1, testPlayer.getScore());

        Move testMoveTwo = new Move(0,2,1,2,0);
        testPlayer.executeMove(testMoveTwo);
        testPlayer.processEndOfRound();
        assertEquals(3, testPlayer.getScore());
    }

    @Test
    public void noNegativeScore() {
        Player testPlayer = new Player(0);

        Move testMoveOne = new Move(0,1,1,1,0);
        testPlayer.executeMove(testMoveOne);

        Move testMoveTwo = new Move(0,1,1,3,0);
        testPlayer.executeMove(testMoveTwo);

        testPlayer.processEndOfRound();
        assertEquals(0, testPlayer.getScore());
    }

    @Test
    public void jsonifyTest() {
        Player testPlayer = new Player(1);
        JSONObject json = testPlayer.jsonify();
        String expected = "{\"score\":0,\"playerId\":1,\"playerBoard\":{\"stairs\":[{\"length\":1,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":2,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":3,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":4,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":5,\"colorIndex\":-1,\"tilesAmount\":0}],\"floorLine\":[],\"wall\":{\"colorsOccupied\":[[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]],\"positionsOccupied\":[[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]]}}}";
        assertEquals(expected,json.toString());



    }

}
