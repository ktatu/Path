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
     * Checking if list contains a specific grid
     * Slow (O(n)) but only used by AlgorithmImageWriter, not counted towards performance
     * @param grid
     * @return 
     */
    public boolean contains(int grid) {
        for (int i = 0; i < firstOpen; i++) {
            if (array[i] == grid) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Expanding array size when it's full
     */
    private void sizeCheck() {
        if (firstOpen == array.length) {
            int[] newArray = new int[array.length * 2];
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
    }
}
