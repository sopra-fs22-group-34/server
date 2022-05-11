package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.Wall;
import org.json.JSONObject;
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

    @Test
    public void fullHorizontalRow() {
        Wall testWall = new Wall();

        testWall.placeTileInRowAndColor(0,0);
        testWall.placeTileInRowAndColor(0,1);
        testWall.placeTileInRowAndColor(0,2);
        testWall.placeTileInRowAndColor(0,3);
        assertEquals(7, testWall.placeTileInRowAndColor(0,4));
    }

    @Test
    public void fullVerticalRow() {
        Wall testWall = new Wall();

        testWall.placeTileInRowAndColor(0,0);
        testWall.placeTileInRowAndColor(1,4);
        testWall.placeTileInRowAndColor(2,3);
        testWall.placeTileInRowAndColor(3,2);
        assertEquals(12, testWall.placeTileInRowAndColor(4,1));
    }

    @Test
    public void fullWallRowDetection() {
        Wall testWall = new Wall();

        assertFalse(testWall.hasFullWallRow());

        testWall.placeTileInRowAndColor(0,0);
        testWall.placeTileInRowAndColor(0,1);
        testWall.placeTileInRowAndColor(0,2);
        testWall.placeTileInRowAndColor(0,3);
        testWall.placeTileInRowAndColor(0,4);

        assertTrue(testWall.hasFullWallRow());
    }

    @Test
    public void jsonifyTest() {
        Wall testWall = new Wall();

        JSONObject jsonOne = testWall.jsonify();
        String expected = "{\"colorsOccupied\":[[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]],\"positionsOccupied\":[[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]]}";
        assertEquals(expected,jsonOne.toString());

        testWall.placeTileInRowAndColor(0,0);
        JSONObject jsonTwo = testWall.jsonify();
        expected = "{\"colorsOccupied\":[[true,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]],\"positionsOccupied\":[[true,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]]}";
        assertEquals(expected,jsonTwo.toString());

        testWall.placeTileInRowAndColor(0,1);
        jsonTwo = testWall.jsonify();
        expected = "{\"colorsOccupied\":[[true,true,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]],\"positionsOccupied\":[[true,true,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]]}";
        assertEquals(expected,jsonTwo.toString());

        testWall.placeTileInRowAndColor(1,0);
        jsonTwo = testWall.jsonify();
        expected = "{\"colorsOccupied\":[[true,true,false,false,false],[true,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]],\"positionsOccupied\":[[true,true,false,false,false],[false,true,false,false,false],[false,false,false,false,false],[false,false,false,false,false],[false,false,false,false,false]]}";
        assertEquals(expected,jsonTwo.toString());

    }
}
