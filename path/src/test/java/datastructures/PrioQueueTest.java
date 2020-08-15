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
    
    private PrioQueue testQueue;
    private Random r = new Random(553);
    
    @Before
    public void setUp() {
        testQueue = new PrioQueue(1000);
    }

    
    @Test
    public void correctPrioQueueOrderWithTwoGrids() {
        testQueue.add(new Grid(0, 0, 0, 5));
        testQueue.add(new Grid(0, 0, 4, 0));
        
        Grid polled = testQueue.poll();
        System.out.println(polled.getDistance()+"------------"+polled.getEstimation());
    }
    
    
    @Test
    public void prioQueueReturnsIntegersInCorrectOrderUsingDistanceVariable() {
        float min;
        
        for (int i = 0; i < 1000; i++) {
            testQueue.add(new Grid(0, 0, r.nextInt(1000000), 0));
        }
        
        min = testQueue.poll().getDistance();
        
        while(!testQueue.isEmpty()) {
            Grid polled = testQueue.poll();
            assertTrue(min < polled.getDistance() || min == polled.getDistance());
            // Min should always end up being the next polled grid's value
            min = Math.max(min, polled.getDistance());
        }
    }
    
    @Test
    public void prioQueueReturnsIntegersInCorrectOrderUsingEstimationVariable() {
        float min;
        
        for (int i = 0; i < 1000; i++) {
            testQueue.add(new Grid(0, 0, 0, r.nextInt(1000000)));
        }
        
        min = testQueue.poll().getDistance();
        
        while(!testQueue.isEmpty()) {
            Grid polled = testQueue.poll();
            assertTrue(min < polled.getEstimation()|| min == polled.getEstimation());
            // Min should always end up being the next polled grid's value
            min = Math.max(min, polled.getDistance());
        }
    }
    
    /*
    @Test
    public void prioQueueReturnsIntegersInCorrectOrderUsingBothVariables() {
        float min;
        
        for (int i = 0; i < 100; i++) {
            testQueue.add(new Grid(0, 0, r.nextInt(100), r.nextInt(100)));
        }
        
        Grid firstPoll = testQueue.poll();
        
        min = Float.sum(firstPoll.getDistance(), firstPoll.getEstimation());
        
        int i = 1;
        while(!testQueue.isEmpty()) {
            Grid polled = testQueue.poll();
            
            float polledSum = Float.sum(polled.getDistance(), polled.getEstimation());
            
            System.out.println("min "+min);
            System.out.println("polled "+polledSum);
            System.out.println(i);
            assertTrue(min < polledSum || min == polledSum);
            // Min should always end up being the next polled grid's value
            min = Math.max(min, Float.sum(polled.getDistance(), polled.getEstimation()));
            i++;
        }
    }
    */
    @Test
    public void prioQueueIsEmptyAsExpected() {
        assertTrue(testQueue.isEmpty());
        
        testQueue.add(new Grid(0, 0, 1, 1));
        assertFalse(testQueue.isEmpty());
        
        testQueue.poll();
        assertTrue(testQueue.isEmpty());
    }
    
    @Test
    public void prioQueueReturnsNullWhenPolledAndEmpty() {
        assertNull(testQueue.poll());
        
        testQueue.add(new Grid(0, 0, 1, 1));
        testQueue.poll();
        assertNull(testQueue.poll());
    }
}
