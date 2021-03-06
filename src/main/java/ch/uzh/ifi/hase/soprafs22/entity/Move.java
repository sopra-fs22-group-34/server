package ch.uzh.ifi.hase.soprafs22.entity;

public class Move {
    //-1 stands for Middle, any nonnegative integer n stands for n-th Factory
    private final int originIndex;
    private final int colorIndex;
    //-1 stands for FloorLine, any nonnegative integer n stands for n-th row
    private final int targetRowIndex;
    private final int tileAmount;
    private final int playerIndex;

    public Move(int originIndex, int colorIndex, int targetRowIndex, int tileAmount, int playerIndex) {
        this.originIndex = originIndex;
        this.colorIndex = colorIndex;
        this.targetRowIndex = targetRowIndex;
        this.tileAmount = tileAmount;
        this.playerIndex = playerIndex;
    }

    public int getOriginIndex() {
        return originIndex;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public int getTargetRowIndex() {
        return targetRowIndex;
    }

    public int getTileAmount() {
        return tileAmount;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}
