package ch.uzh.ifi.hase.soprafs22.entity;

import java.util.ArrayList;

public class Factory implements Field {

    private ArrayList<Integer> colorAmounts;

    public ArrayList<Integer> getColorAmounts() {
        return colorAmounts;
    }

    public void setColorAmounts(ArrayList<Integer> colorAmounts) {
        this.colorAmounts = colorAmounts;
    }

    //these are placeholders -> not yet implemented
    public void setupFactory() {

    }
}
