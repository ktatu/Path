package logic;

import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.logic.Grid;

/**
 *
 * @author Tatu
 */
public class GridTest {
    
    @Test
    public void gridsWithNoHeuristicAreComparedCorrectly() {
        Grid dijGrid1 = new Grid(0, 0, 10, 0);
        Grid dijGrid2 = new Grid(0, 0, 100, 0);
        assertEquals(-1, dijGrid1.compareTo(dijGrid2));
           
        Grid dijGrid5 = new Grid(0, 0, 5, 0);
        Grid dijGrid6 = new Grid(0, 0, 1, 0);
        assertEquals(1, dijGrid5.compareTo(dijGrid6));
    }
    
    @Test
    public void gridsWithHeuristicAreComparedCorrectly() {
        Grid heurGrid1 = new Grid(0, 0, 10, 1);
        Grid heurGrid2 = new Grid(0, 0, 2, 10);
        assertEquals(-1, heurGrid1.compareTo(heurGrid2));
        assertEquals(1, heurGrid2.compareTo(heurGrid1));
        
        // If distance + estimation is equal on both then the grid with smaller estimation is considered smaller
        Grid heurGrid3 = new Grid(0, 0, 4, 6);
        Grid heurGrid4 = new Grid(0, 0, 6, 4);
        assertEquals(1, heurGrid3.compareTo(heurGrid4));
        assertEquals(-1, heurGrid4.compareTo(heurGrid3));
        
        Grid heurGrid5 = new Grid(0, 0, 1, 10);
        Grid heurGrid6 = new Grid(0, 0, 4, 4);
        assertEquals(1, heurGrid5.compareTo(heurGrid6));
    }
}
