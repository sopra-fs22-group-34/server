package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.Random;

public class Factory implements Field {

    private Integer[] colorAmounts = new Integer[5];

    public Integer[] getColorAmounts() {
        return colorAmounts;
    }

    Factory() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            colorAmounts[i] = 0;
        }

        for (int i = 0; i < 4; i++) {
            int randomInt = random.nextInt(5);
            colorAmounts[randomInt]++;
        }
    }
}
