package datastructures;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.datastructures.FifoQueue;

/**
 *
 * @author Tatu
 */
public class FifoQueueTest {
    
    private FifoQueue testQueue;
    
    @Before
    public void setUp() {
        testQueue = new FifoQueue(10);
    }

    @Test
    public void queueReturnsIntegersInCorrectOrder() {
        testQueue.add(5);
        testQueue.add(5);
        testQueue.add(10);
        testQueue.add(8);
        testQueue.add(7);
        
        assertEquals(5, testQueue.poll());
        assertEquals(5, testQueue.poll());
        assertEquals(10, testQueue.poll());
        assertEquals(8, testQueue.poll());
        assertEquals(7, testQueue.poll());
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
        assertEquals(true, testQueue.isEmpty());
        
        testQueue.add(1);
        assertEquals(false, testQueue.isEmpty());
        
        testQueue.poll();
        assertEquals(true, testQueue.isEmpty());
    }
}
