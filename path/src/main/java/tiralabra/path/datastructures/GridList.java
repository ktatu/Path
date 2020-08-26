package tiralabra.path.datastructures;

/**
 * ArrayList-like structure for saving and accessing grids as integers
 * @author Tatu
 */
public class GridList {
    private int[] array;
    
    private int firstOpen;
    private int iterationIndex;
    
    public GridList(int size) {
        this.array = new int[size];
        this.firstOpen = 0;
        this.iterationIndex = 0;
    }
    
    /**
     * Adding an integer to the TestList
     * @param grid to be added
     */
    public void add(int grid) {
        sizeCheck();
        array[firstOpen++] = grid;
    }
    
    /**
     * Check if the whole array has been iterated
     * @return false if the whole array has been iterated over
     */
    public boolean canIterate() {
        if (iterationIndex == firstOpen) {
            iterationIndex = 0;
            return false;
        }
        return true;
    }
    
    /**
     * Return the next integer of iteration
     * @return next integer of iteration
     */
    public int getNext() {
        return array[iterationIndex++];
    }
    
    /**
     * Expanding array size when it's full
     */
    private void sizeCheck() {
        if (firstOpen == array.length) {
            int[] newArray = new int[array.length * 2];
            System.arraycopy(array, 1, newArray, 1, array.length - 1);
            array = newArray;
        }
    }
}
