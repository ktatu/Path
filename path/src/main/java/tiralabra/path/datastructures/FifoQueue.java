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
    
    public int poll() {
        int polledInt = queue[indexFirstEntry];
        indexFirstEntry++;
        return polledInt;
    }
    
    public void add(int number) {
        queue[indexFirstOpenCell] = number;
        indexFirstOpenCell++;
    }
    
    public boolean isEmpty() {
        return indexFirstEntry == indexFirstOpenCell;
    }
}
