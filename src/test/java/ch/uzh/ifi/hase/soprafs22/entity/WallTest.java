package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.Wall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class WallTest {

    @Test
    public void constructorCorrect() {
        Wall testWall = new Wall();
        assertFalse(testWall.isColorOccupied(0, 1));
        assertFalse(testWall.isColumnOccupied(0, 1));
    }

    @Test
    public void tilePlacement() {
        Wall testWall = new Wall();
        testWall.placeTileInRowAndColor(0,0);
        testWall.placeTileInRowAndColor(2,3);

        assertTrue(testWall.isColorOccupied(0, 0));
        assertTrue(testWall.isColumnOccupied(0, 0));

        assertTrue(testWall.isColorOccupied(2, 3));
        assertTrue(testWall.isColumnOccupied(2, 0));
    }

    @Test
    public void scoreFromTilePlacement() {
        Wall testWall = new Wall();

        //place tile in row 2, column 2 ("center" tile of wall)
        assertEquals(1,testWall.placeTileInRowAndColor(2,0));


        //place tile in row 2, column 3 (right of center)
        assertEquals(2,testWall.placeTileInRowAndColor(2,1));

        //place tile in row 2, column 1 (left of center)
        assertEquals(3,testWall.placeTileInRowAndColor(2,4));


        //place tile in row 1, column 2 (upward of center)
        assertEquals(2,testWall.placeTileInRowAndColor(1,1));

        //place tile in row 3, column 2 (downward of center)
        assertEquals(3,testWall.placeTileInRowAndColor(3,4));


        //place tile in row 3, column 3 (down right of center)
        assertEquals(4,testWall.placeTileInRowAndColor(3,0));
    }
}
