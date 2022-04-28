package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.Factory;
import org.junit.jupiter.api.Test;
import org.json.*;
import static org.junit.jupiter.api.Assertions.*;

public class FactoryTest {


    @Test
    public void constructorCorrect() {
        Factory testFactory = new Factory();
        int sum = 0;
        for (int value : testFactory.getColorAmounts()) {
            sum += value;
        }

        assertEquals(4, sum);
    }

    @Test
    public void checkValidMove() {
        Factory testFactory = new Factory();
        Integer[] colorAmounts = testFactory.getColorAmounts();

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

        Move testMove = new Move(0,colorIndex,0,tileAmount,0);
        assertTrue(testFactory.checkIfMoveValid(testMove));
    }

    @Test
    public void checkInvalidMoveTooManyTiles() {
        Factory testFactory = new Factory();
        Integer[] colorAmounts = testFactory.getColorAmounts();

        int colorIndex = 0;
        int tileAmount = 0;

        //find first nonzero entry in colorAmounts, create Move with this colorIndex and tileAmount
        for (int i = 0; i < 5; i++) {
            if (colorAmounts[i] != 0) {
                colorIndex = i;
                tileAmount = colorAmounts[i] + 1;
                break;
            }
        }

        Move testMove = new Move(0,colorIndex,0,tileAmount,0);
        assertFalse(testFactory.checkIfMoveValid(testMove));
    }

    @Test
    public void executeMoveTest() {
        Factory testFactory = new Factory();
        Integer[] colorAmounts = testFactory.getColorAmounts();

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

        Move testMove = new Move(0,colorIndex,0,tileAmount,0);


        //create arrays for expected returned colorAmounts and expected colorAmounts in the Factory
        Integer[] expectedLeftoverColorAmounts = colorAmounts;
        expectedLeftoverColorAmounts[colorIndex] = 0;

        Integer[] emptyFactoryColorAmounts = new Integer[5];
        for (int i = 0; i < 5; i++) {
            emptyFactoryColorAmounts[i] = 0;
        }

        assertArrayEquals(expectedLeftoverColorAmounts, testFactory.executeMove(testMove));
        assertArrayEquals(emptyFactoryColorAmounts, testFactory.getColorAmounts());
    }

    @Test
    public void isEmptyTest() {
        Factory testFactory = new Factory();

        assertFalse(testFactory.isEmpty());


        Integer[] colorAmounts = testFactory.getColorAmounts();

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

        Move testMove = new Move(0,colorIndex,0,tileAmount,0);

        testFactory.executeMove(testMove);

        assertTrue(testFactory.isEmpty());
    }
}
