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

        //initialize colorAmounts to all zero
        for (int i = 0; i < 5; i++) {
            colorAmounts[i] = 0;
        }

        //generate four tiles of random color
        for (int i = 0; i < 4; i++) {
            int randomInt = random.nextInt(5);
            colorAmounts[randomInt]++;
        }
    }

    public boolean checkIfMoveValid(Move attemptedMove) {
        int colorIndex = attemptedMove.getColorIndex();
        int tileAmount = attemptedMove.getTileAmount();

        if (colorAmounts[colorIndex] == tileAmount) {
            return true;
        }

        else {
            return false;
        }
    }


    //this needs to return an Integer array to reflect the leftover tiles that need to be placed in the middle
    public Integer[] executeMove(Move move) {
        Integer[] leftoverColorAmounts = colorAmounts;
        leftoverColorAmounts[move.getColorIndex()] = 0;

        for (int i = 0; i < 5; i++) {
            colorAmounts[i] = 0;
        }
        //set all colorAmounts to 0, since Factory now empty, and return leftover tiles
        return leftoverColorAmounts;
    }
}
