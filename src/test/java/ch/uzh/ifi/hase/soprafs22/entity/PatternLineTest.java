package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.PatternLine;
import org.junit.jupiter.api.Test;
import org.json.*;

import static org.junit.jupiter.api.Assertions.*;

public class PatternLineTest {

    @Test
    public void constructorCorrect() {
        PatternLine testPatternLine = new PatternLine(3);
        assertEquals(3,testPatternLine.getLength());
        assertEquals(0,testPatternLine.getTilesAmount());
        assertEquals(-1,testPatternLine.getColorIndex());
    }

    @Test
    public void notFullPatternLine() {
        PatternLine testPatternLine = new PatternLine(4);
        testPatternLine.setTilesAmount(3);
        testPatternLine.setColorIndex(2);

        assertEquals(-1,testPatternLine.emptyFullPatternLine());
    }

    @Test
    public void fullPatternLine() {
        PatternLine testPatternLine = new PatternLine(4);
        testPatternLine.setTilesAmount(4);
        testPatternLine.setColorIndex(2);

        assertEquals(2,testPatternLine.emptyFullPatternLine());
        assertEquals(0,testPatternLine.getTilesAmount());
        assertEquals(-1,testPatternLine.getColorIndex());
        assertEquals(4,testPatternLine.getLength());
    }

    @Test
    public void jsonifyTest(){
        PatternLine testPatternLine = new PatternLine(4);
        JSONObject json = testPatternLine.jsonify();
        String expected = "{\"length\":4,\"colorIndex\":-1,\"tilesAmount\":0}";
        assertEquals(expected, json.toString());

        testPatternLine.setTilesAmount(3);
        testPatternLine.setColorIndex(3);
        json = testPatternLine.jsonify();
        expected = "{\"length\":4,\"colorIndex\":3,\"tilesAmount\":3}";
        assertEquals(expected, json.toString());
    }



}
