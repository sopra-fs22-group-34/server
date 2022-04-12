package ch.uzh.ifi.hase.soprafs22.entity;



public class PlayerBoard  {

    private Stairs stairs;

    private Wall wall;

    private FloorLine floorLine;

    PlayerBoard() {
        stairs = new Stairs();
        wall = new Wall();
        floorLine = new FloorLine();
    }


    public Stairs getStairs() {
        return stairs;
    }

    public Wall getWall() {
        return wall;
    }

    public FloorLine getFloorLine() {
        return floorLine;
    }

    public int processEndOfRound(){
        int scoreDifference = 0;

        for (int row = 0; row < 5; row++) {
            int returnedColor = stairs.emptyFullPatternLine(row);
            if (returnedColor != -1){
                scoreDifference += wall.placeTileInRowAndColor(row, returnedColor);
            }
        }

        scoreDifference -= floorLine.processEndOfRound();

        return scoreDifference;
    }

    public boolean checkIfMoveValid(Move attemptedMove) {
        boolean wallSpotOccupied = wall.isColorOccupied(attemptedMove.getTargetRowIndex(), attemptedMove.getColorIndex());
        if (!wallSpotOccupied) {
            return stairs.checkIfMoveValid(attemptedMove);
        }

        else {
            return false;
        }

    }

    public void executeMove(Move move) {
        int excessTiles = stairs.executeMove(move);

        //place excess tiles in FloorLine

        for (int i = 0; i < excessTiles; i++) {
            floorLine.placeTile(move.getColorIndex());
        }
    }

}
