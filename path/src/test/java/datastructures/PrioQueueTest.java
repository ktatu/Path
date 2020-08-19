package datastructures;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.datastructures.PrioQueue;
import tiralabra.path.logic.Grid;

/**
 *
 * @author Tatu
 */
public class PrioQueueTest {
    
    private PrioQueue testPrio;

    private Random r = new Random(1427);
    
    @Before
    public void setUp() {
        testPrio = new PrioQueue();
    }

    @Test
    public void prioQueueInCorrectOrderWithTwoGrids() {
        testPrio.add(new Grid(0, 0, 10, 0));
        testPrio.add(new Grid(0, 0, 5, 0));
        
        Grid polled = testPrio.poll();
        assertEquals(5, (int)polled.getDistance());
    }
    
    @Test
    public void prioQueueReturnsGridsInCorrectOrderUsingDistanceVariable() {
        float min;
        
        for (int i = 0; i < 1000000; i++) {
            int rand = r.nextInt(1000000);
            testPrio.add(new Grid(0, 0, rand, 0));
        }
        
        min = testPrio.poll().getDistance();
        
        int pollIndex = 1;
        while (!testPrio.isEmpty()) {
            Grid polled = testPrio.poll();
            
            if (min > polled.getDistance()) {
                System.out.println("min: " + min);
                System.out.println("polled: " + polled.getDistance());
                System.out.println("index: " + pollIndex);
                fail("PrioQueue is not in correct order after polling grids using distance variable");
            }
            pollIndex++;
            min = Math.max(min, polled.getDistance());
        }
    }
    
    @Test
    public void prioQueueReturnsGridsInCorrectOrderUsingEstimationVariable() {
        float min;
        
        for (int i = 0; i < 1000000; i++) {
            int rand = r.nextInt(1000000);
            testPrio.add(new Grid(0, 0, 0, rand));
        }
        
        min = testPrio.poll().getDistance();
        
        while (!testPrio.isEmpty()) {
            Grid polled = testPrio.poll();
            
            if (min > polled.getDistance()) {
                fail("PrioQueue is not in correct order after polling grids using estimation variable");
            }
            min = Math.max(min, polled.getDistance());
        }
    }
    
    @Test
    public void prioQueueReturnsGridsInCorrectOrderUsingBothVariables() {
        Grid min;
        
        for (int i = 0; i < 1000000; i++) {
            testPrio.add(new Grid(0, 0, r.nextInt(500000), r.nextInt(500000)));
        }
        
        min = testPrio.poll();
        
        while (!testPrio.isEmpty()) {
            Grid polled = testPrio.poll();
            
            if (min.compareTo(polled) == 1) {
                fail("PrioQueue is not in correct order after polling grids using both variables");
            }
            min = polled;
        }
    }
    
    @Test
    public void prioQueueIsEmptyAsExpected() {
        assertTrue(testPrio.isEmpty());
        
        testPrio.add(new Grid(0, 0, 1, 1));
        assertFalse(testPrio.isEmpty());
        
        testPrio.poll();
        assertTrue(testPrio.isEmpty());
    }
    
    @Test
    public void prioQueueReturnsNullWhenPolledAndEmpty() {
        assertNull(testPrio.poll());
        
        testPrio.add(new Grid(0, 0, 1, 1));
        testPrio.poll();
        assertNull(testPrio.poll());
    }
    
}
