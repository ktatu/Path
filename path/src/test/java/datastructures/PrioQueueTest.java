package datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    public void prioQueueReturnsIntegersInCorrectOrder() {
        PriorityQueue<Grid> comparisonQueue = new PriorityQueue<>(10, Collections.reverseOrder());
        
        for (int i = 0; i < 1000; i++) {
            int rDistance = r.nextInt(1000);
            int rEstimation = r.nextInt(1000);
            testQueue.add(new Grid(0, 0, rDistance, rEstimation));
        }
        
        comparisonQueue.add(testQueue.poll());
        // Tests that every polled grid from min heap (testQueue) is bigger than every grid in max heap of already polled grids
        while(!testQueue.isEmpty()) {
            Grid polled = testQueue.poll();
            Grid biggestInMaxHeap = comparisonQueue.peek();
            
            System.out.println("--------");
            System.out.println("prion suurin " + biggestInMaxHeap.getDistance() + ", " + biggestInMaxHeap.getEstimation());
            System.out.println("pollattu " + polled.getDistance() + ", " + polled.getEstimation());
            System.out.println("--------");

            testQueue.printFirstOpenCell();
            if (Math.abs(polled.getDistance() + polled.getEstimation() - biggestInMaxHeap.getDistance() - biggestInMaxHeap.getEstimation()) > 0.0001) {
                assertEquals(-1, biggestInMaxHeap.compareTo(polled));
            }
            comparisonQueue.add(polled);
        }
    }
}
