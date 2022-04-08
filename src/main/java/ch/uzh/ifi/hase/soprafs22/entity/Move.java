package ch.uzh.ifi.hase.soprafs22.entity;

public class Move {
    private final int originIndex;
    private final int colorIndex;
    private final int targetLineIndex;
    private final int tileAmount;
    private final int playerIndex;

    Move(int originIndex, int colorIndex, int targetLineIndex, int tileAmount, int playerIndex) {
        this.originIndex = originIndex;
        this.colorIndex = colorIndex;
        this.targetLineIndex = targetLineIndex;
        this.tileAmount = tileAmount;
        this.playerIndex = playerIndex;
    }

    public int getOriginIndex() {
        return originIndex;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public int getTargetLineIndex() {
        return targetLineIndex;
    }

    public int getTileAmount() {
        return tileAmount;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}
