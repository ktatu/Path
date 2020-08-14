package performance;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import tiralabra.path.datastructures.FifoQueue;

/**
 *
 * @author Tatu
 */
public class FifoQueuePerformance {
    
    private FifoQueue testFifo;
    private ArrayDeque testJavaDeque;
    private Queue testJavaQueue;
    
    private final Random r = new Random(1337);
    private int testSize;
    

}
