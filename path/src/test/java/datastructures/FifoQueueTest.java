package datastructures;

import java.util.ArrayDeque;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import tiralabra.path.datastructures.FifoQueue;

/**
 *
 * @author Tatu
 */
public class FifoQueueTest {
    
    private FifoQueue testQueue;
    Random r = new Random(1337);
    
    @Before
    public void setUp() {
        testQueue = new FifoQueue(10);
    }

    @Test
    public void queueReturnsIntegersInCorrectOrder() {
        int testSize = 1000000;
        testQueue = new FifoQueue(testSize);
        
        ArrayDeque comparisonQueue = new ArrayDeque<>();
        
        for (int i = 0; i < testSize; i++) {
            int randomInt = r.nextInt(1000000);
            testQueue.add(randomInt);
            comparisonQueue.add(randomInt);
        }
        
        while (!testQueue.isEmpty()) {
            assertEquals(comparisonQueue.poll(), testQueue.poll());
        }
    }
    
    @Test
    public void queueReturnsZeroWhenPolledAndEmpty() {
        assertEquals(0, testQueue.poll());
        
        testQueue.add(5);
        testQueue.poll();
        assertEquals(0, testQueue.poll());
    }
    
    /*
    Tällä hetkellä jonon koko = mapin koko. Myöhemmin ehkä optimointia
    @Test
    public void queueSizeIsInitializedCorrectly() {
        Random r = new Random(1337);
        
        for (int i = 0; i < 35; i++) {
            int testMapSize = r.nextInt(10000);
            FifoQueue sizeTestQueue = new FifoQueue(testMapSize);
            int expectedQueueSize = (int) (0.9 * testMapSize);
            
            for (int j = 0; j < expectedQueueSize; j++) {
                sizeTestQueue.add(1);
            }
            // Queue should now be full and adding one more integer causes exception
            try {
                sizeTestQueue.add(1);
                fail("Size of test queue is larger than intended");
            } catch(IndexOutOfBoundsException e) {
            }
        }
    }
    */
    @Test
    public void queueIsEmptyAsExpected() {
        assertTrue(testQueue.isEmpty());
        
        testQueue.add(1);
        assertFalse(testQueue.isEmpty());
        
        testQueue.poll();
        assertTrue(testQueue.isEmpty());
    }
}
