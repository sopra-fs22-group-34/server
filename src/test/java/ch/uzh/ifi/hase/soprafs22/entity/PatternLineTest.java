package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.entity.PatternLine;
import org.dom4j.rule.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class PatternLineTest {

    @Test
    public void notFullPatternLine() {
        PatternLine testPatternLine = new PatternLine();
        testPatternLine.setLength(4);
        testPatternLine.setTilesAmount(3);
        testPatternLine.setColorIndex(2);

        assertEquals(testPatternLine.emptyFullPatternLine(), -1);
    }

    @Test
    public void fullPatternLine() {
        PatternLine testPatternLine = new PatternLine();
        testPatternLine.setLength(4);
        testPatternLine.setTilesAmount(4);
        testPatternLine.setColorIndex(2);

        assertEquals(testPatternLine.emptyFullPatternLine(), 2);
        assertEquals(testPatternLine.getTilesAmount(),0);
        assertEquals(testPatternLine.getColorIndex(),-1);
        assertEquals(testPatternLine.getLength(),4);
    }



}
