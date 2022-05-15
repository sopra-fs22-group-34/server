package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.PlayerBoard;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerBoardTest {

    @Test
    public void moveWithFreeWallColorSlotValid() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        Move testMove = new Move(0,0,0,1,0);
        assertTrue(testPlayerBoard.checkIfMoveValid(testMove));
    }

    @Test
    public void moveWithOccupiedWallColorSlotInvalid() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        testPlayerBoard.getWall().placeTileInRowAndColor(0,0);
        Move testMove = new Move(0,0,0,1,0);
        assertFalse(testPlayerBoard.checkIfMoveValid(testMove));
    }

    //TODO: possibly work with Mockito more
    @Test
    public void emptyPatternLinesNoChange() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        assertEquals(0, testPlayerBoard.processEndOfRound());
    }

    @Test
    public void partiallyFullPatternLineCorrectScoring() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        Move testMove = new Move(0,0,1,1,0);
        testPlayerBoard.executeMove(testMove);
        assertEquals(0, testPlayerBoard.processEndOfRound());

    }

    @Test
    public void fullPatternLineCorrectScoring() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        Move testMove = new Move(0,0,0,1,0);
        testPlayerBoard.executeMove(testMove);
        assertEquals(1, testPlayerBoard.processEndOfRound());

    }

    @Test
    public void executeMoveNoExcessTiles() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        Move testMove = new Move(0,0,0,1,0);
        testPlayerBoard.executeMove(testMove);

        int minusCount = testPlayerBoard.getFloorLine().getMinusCount();
        assertEquals(0, minusCount);
    }

    @Test
    public void executeMoveWithExcessTiles() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        Move testMove = new Move(0,0,0,2,0);
        testPlayerBoard.executeMove(testMove);
        int minusCount = testPlayerBoard.getFloorLine().getMinusCount();
        assertEquals(1, minusCount);
    }

    @Test
    public void placeTilesOnFloorLine() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        Move testMove = new Move(0,0,-1,2,0);
        testPlayerBoard.executeMove(testMove);
        int minusCount = testPlayerBoard.getFloorLine().getMinusCount();
        assertEquals(2, minusCount);
    }

    @Test
    public void jsonifyTest() {
        PlayerBoard testPlayerBoard = new PlayerBoard();

        JSONObject json = testPlayerBoard.jsonify();
        String expected = "{\"stairs\":[{\"length\":1,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":2,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":3,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":4,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":5,\"colorIndex\":-1,\"tilesAmount\":0}],\"floorLine\":[],\"wall\":{\"colorsOccupied\":[[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]],\"positionsOccupied\":[[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]]}}";
        assertEquals(expected,json.toString());
        Move testMove = new Move(0,2,0,1,0);
        testPlayerBoard.executeMove(testMove);

        //verify that placing 1 tile of color 2 in top row of stairs, and then processing end of round, leads to the wall
        //correctly displaying row 0 and color 2, and row 0 and column 2 respectively, as occupied
        json = testPlayerBoard.jsonify();
        expected = "{\"stairs\":[{\"length\":1,\"colorIndex\":2,\"tilesAmount\":1},{\"length\":2,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":3,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":4,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":5,\"colorIndex\":-1,\"tilesAmount\":0}],\"floorLine\":[],\"wall\":{\"colorsOccupied\":[[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]],\"positionsOccupied\":[[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]]}}";
        assertEquals(expected,json.toString());

        testPlayerBoard.processEndOfRound();

        json = testPlayerBoard.jsonify();
        expected = "{\"stairs\":[{\"length\":1,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":2,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":3,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":4,\"colorIndex\":-1,\"tilesAmount\":0},{\"length\":5,\"colorIndex\":-1,\"tilesAmount\":0}],\"floorLine\":[],\"wall\":{\"colorsOccupied\":[[false,false,true,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]],\"positionsOccupied\":[[false,false,true,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]]}}";
        assertEquals(expected,json.toString());

    }
}
