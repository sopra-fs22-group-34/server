package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.Wall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class WallTest {

    @Test
    public void constructorCorrect() {
        Wall testWall = new Wall();
        assertEquals(testWall.isColorOccupied(0,1),false);
        assertEquals(testWall.isColumnOccupied(0,1),false);
    }

    @Test
    public void tilePlacement() {
        Wall testWall = new Wall();
        testWall.placeTileInRowAndColor(0,0);
        testWall.placeTileInRowAndColor(2,3);

        assertEquals(testWall.isColorOccupied(0,0),true);
        assertEquals(testWall.isColumnOccupied(0,0),true);

        assertEquals(testWall.isColorOccupied(2,3),true);
        assertEquals(testWall.isColumnOccupied(2,0),true);
    }

    @Test
    public void scoreFromTilePlacement() {
        Wall testWall = new Wall();

        //place tile in row 2, column 2 ("center" tile of wall)
        assertEquals(testWall.placeTileInRowAndColor(2,0),1);


        //place tile in row 2, column 3 (right of center)
        assertEquals(testWall.placeTileInRowAndColor(2,1),2);

        //place tile in row 2, column 1 (left of center)
        assertEquals(testWall.placeTileInRowAndColor(2,4),3);


        //place tile in row 1, column 2 (upward of center)
        assertEquals(testWall.placeTileInRowAndColor(1,1),2);

        //place tile in row 3, column 2 (downward of center)
        assertEquals(testWall.placeTileInRowAndColor(3,4),3);


        //place tile in row 3, column 3 (down right of center)
        assertEquals(testWall.placeTileInRowAndColor(3,0),4);
    }
}
