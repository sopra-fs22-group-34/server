package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.Middle;
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
}
