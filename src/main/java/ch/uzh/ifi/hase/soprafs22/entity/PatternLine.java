package ch.uzh.ifi.hase.soprafs22.entity;

import org.json.*;

import java.io.Serializable;


public class PatternLine implements Serializable {

    private int length;

    private int tilesAmount;

    //colorIndex is 0 through 4 for a color, -1 if the PatternLine is empty
    private int colorIndex;

    PatternLine(int length){
        this.length = length;
        this.tilesAmount = 0;
        this.colorIndex = -1;
    }


    public int getLength() {
        return length;
    }

    public int getTilesAmount() {
        return tilesAmount;
    }

    public void setTilesAmount(int tilesAmount) {
        this.tilesAmount = tilesAmount;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }


    //return colorIndex of PatternLine if it is full, -1 otherwise to signify it isn't full
    public int emptyFullPatternLine(){
        if (length == tilesAmount) {
            int returnedColor = getColorIndex();
            setTilesAmount(0);
            setColorIndex(-1);
            return returnedColor;
        }
        else {
            return -1;
        }
    }

    public JSONObject jsonify() {
        JSONObject json = new JSONObject();
        json.put("length", length);
        json.put("tilesAmount", tilesAmount);
        json.put("colorIndex", colorIndex);
        return json;
    }
}
