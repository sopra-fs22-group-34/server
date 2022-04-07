package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.PlayerBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerBoardTest {

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
