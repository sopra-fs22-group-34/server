package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;
import java.util.Random;

public class Middle implements Field {

    private Integer[] colorAmounts = new Integer[5];

    private boolean hasMinusTile = true;

    public Integer[] getColorAmounts() {
        return colorAmounts;
    }

    Middle() {
        for (int i = 0; i < 5; i++) {
            colorAmounts[i] = 0;
        }

    }
}
