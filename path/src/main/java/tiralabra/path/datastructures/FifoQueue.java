package tiralabra.path.datastructures;

/**
 * First-in-first-out queue utilized by breadth first search
 * @author Tatu
 */
public class FifoQueue {
    
    private final int[] queue;
    private int indexFirstEntry;
    private int indexFirstOpenCell;
    
    public FifoQueue(int mapSize) {
        queue = new int[mapSize];
        indexFirstEntry = 0;
        indexFirstOpenCell = 0;
    }
    
    /**
     * Polling the queue
     * @return the first int (==the oldest)
     */
    public int poll() {
        int polledInt = queue[indexFirstEntry];
        indexFirstEntry++;
        return polledInt;
    }
    
    /**
     * Add an integer to queue
     * @param number integer to be added
     */
    public void add(int number) {
        queue[indexFirstOpenCell] = number;
        indexFirstOpenCell++;
    }
    
    /**
     * Check if queue is empty
     * @return true if empty
     */
    public boolean isEmpty() {
        return indexFirstEntry == indexFirstOpenCell;
    }
}
