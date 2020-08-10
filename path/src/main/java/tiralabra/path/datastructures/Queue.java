package tiralabra.path.datastructures;

/**
 * First-in-first-out queue utilized by breadth first search
 * @author Tatu
 */
public class Queue {
    
    private int[] queue;
    private int indexFirstEntry;
    private int indexFirstOpenCell;
    
    public Queue(int mapSize) {
        // 0.86 is an estimation for 6/7 which comes from every loop iteration removing 1 int and adding at most 7 neighbors to queue
        // start grid is the exception to this and is accounted for with the + 1 to size
        int queueSize = (int) (0.86 * mapSize + 1);
        queue = new int[queueSize];
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
