package ch.uzh.ifi.hase.soprafs22.entity;

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
}
