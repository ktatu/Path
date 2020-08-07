package logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.logic.Grid;

/**
 *
 * @author Tatu
 */
public class GridTest {
    
    // the one being compared to should always be lesser in cases of equal values, no reason to implement equality between grids for algorithms
    
    @Test
    public void gridsWithNoHeuristicAreComparedCorrectly() {
        Grid dijGrid1 = new Grid(5, 5, 10);
        Grid dijGrid2 = new Grid(24, 45, 100);
        assertEquals(-1, dijGrid1.compareTo(dijGrid2));
        
        Grid dijGrid3 = new Grid(0, 0, 1);
        Grid dijGrid4 = new Grid(2, 2, 1);
        assertEquals(-1, dijGrid3.compareTo(dijGrid4));
        assertEquals(-1, dijGrid4.compareTo(dijGrid3));
        
        Grid dijGrid5 = new Grid(0, 0, 5);
        Grid dijGrid6 = new Grid(1, 1, 1);
        assertEquals(1, dijGrid5.compareTo(dijGrid6));
    }
    
    @Test
    public void gridsWithHeuristicAreComparedCorrectly() {
        Grid heurGrid1 = new Grid(1, 1, 10, 1);
        Grid heurGrid2 = new Grid(5, 5, 2, 10);
        assertEquals(-1, heurGrid1.compareTo(heurGrid2));
        assertEquals(1, heurGrid2.compareTo(heurGrid1));
        
        Grid heurGrid3 = new Grid(0, 0, 4, 6);
        Grid heurGrid4 = new Grid(0, 1, 6, 4);
        assertEquals(-1, heurGrid3.compareTo(heurGrid4));
        assertEquals(-1, heurGrid4.compareTo(heurGrid3));
        
        Grid heurGrid5 = new Grid(0, 0, 1, 10);
        Grid heurGrid6 = new Grid(2, 2, 4, 4);
        assertEquals(1, heurGrid5.compareTo(heurGrid6));
    }
}
