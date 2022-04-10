package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.PlayerBoard;
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

    @Test
    public void emptyPatternLinesNoChange() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        assertEquals(0, testPlayerBoard.processEndOfRound());
    }

    //TODO: write more test cases once tile placement functions exist
    /*@Test
    public void fullPatternLineCorrectScoring() {
        PlayerBoard testPlayerBoard = new PlayerBoard();


    }*/
}
