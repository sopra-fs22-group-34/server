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

    //executeMove returns whether the minus Tile has been picked up during the move, since the first player
    //to take tiles from the Middle must also take the minus Tile
    public boolean executeMove(Move move) {
        colorAmounts[move.getColorIndex()] = 0;

        if (hasMinusTile) {
            hasMinusTile = false;
            return true;
        }

        else {
            return false;
        }
    }

    public void placeLeftoverTiles(Integer[] leftoverColorAmounts) {
        for (int i = 0; i < 5; i++) {
            colorAmounts[i] += leftoverColorAmounts[i];
        }
    }
}
