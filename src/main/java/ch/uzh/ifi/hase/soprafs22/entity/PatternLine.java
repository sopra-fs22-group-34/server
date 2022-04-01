package ch.uzh.ifi.hase.soprafs22.entity;


public class PatternLine {

    private int length;

    private int tilesAmount;

    private int colorIndex;


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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
}
