package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.Factory;
import org.junit.jupiter.api.Test;

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
}
