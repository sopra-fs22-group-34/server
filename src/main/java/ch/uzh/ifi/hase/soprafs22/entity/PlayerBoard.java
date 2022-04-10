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

    public void setStairs(Stairs stairs) {
        this.stairs = stairs;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public FloorLine getFloorLine() {
        return floorLine;
    }

    public void setFloorLine(FloorLine floorLine) {
        this.floorLine = floorLine;
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

    /*public void placeTileOnFloorLine(int colorIndex) {
        floorLine.placeTile(colorIndex);
    }*/
}
