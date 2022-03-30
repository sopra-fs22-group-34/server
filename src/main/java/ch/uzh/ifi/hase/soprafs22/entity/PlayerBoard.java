package ch.uzh.ifi.hase.soprafs22.entity;



public class PlayerBoard  {

    private Stairs stairs;

    private Wall wall;

    private FloorLine floorLine;


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
        //PLACEHOLDER
        return 0;
    }
}
