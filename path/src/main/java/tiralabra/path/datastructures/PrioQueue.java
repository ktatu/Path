package tiralabra.path.datastructures;

import tiralabra.path.logic.Grid;

/**
 * PriorityQueue for Grids. Used by Dijkstra, A* and JPS
 * @author Tatu
 */
public class PrioQueue {
    
    // PrioQueue is a binary heap in the form of an array. If n is a grid's index, then its children are 2*n and 2*n+1
    private Grid[] queue;
    private int firstOpen;
    
    public PrioQueue() {
        this.queue = new Grid[128];
        this.firstOpen = 1;
    }
    
    /**
     * Adding a grid to the PriorityQueue
     * @param grid to be added to queue
     */
    public void add(Grid grid) {
        sizeCheck();
        queue[firstOpen] = grid;
        firstOpen++;
        sortAfterAdding();
    }
    
    /**
     * Check if the queue is currectly empty
     * @return true if empty
     */
    public boolean isEmpty() {
        return queue[1] == null;
    }
    
    /**
     * Remove from queue and return the grid with smallest value (distance + estimation)
     * @return the smallest grid in queue
     */
    public Grid poll() {
        if (isEmpty()) {
            return null;
        }
        Grid toReturn = queue[1];
        moveLatestGrid();
        sortAfterPolling();
        
        return toReturn;
    }
    
    /**
     * Check if the queue size needs to be expanded. Always power of 2 for ease of handling PrioQueue's tree structure
     */
    private void sizeCheck() {
        if (firstOpen == queue.length) {
            Grid[] newQueue = new Grid[queue.length * 2];
            for (int i = 0; i < queue.length; i++) {
                newQueue[i] = queue[i];
            }
            queue = newQueue;
        }
    }
    
    /**
     * Sort queue after add() was called
     * Sorting is done by moving the recently added Grid up the tree until it no longer is smaller than its parent
     */
    private void sortAfterAdding() {
        int posIndex = firstOpen - 1;
        while (posIndex > 1) {
            Grid childGrid = queue[posIndex];
            Grid parentGrid = queue[posIndex / 2];
            if (childGrid.compareTo(parentGrid) == -1) {
                swapPositions(posIndex, posIndex / 2);
                posIndex /= 2;
            } else {
                break;
            }
        }
    }
    
    /**
     * Sort queue after poll() was called
     * Sorting happens by initially moving the newest grid in queue to root and moving it downwards
     */
    private void sortAfterPolling() {
        int pos = 1;
        while (pos != -1) {
            Grid leftChild = initializeGrid(pos * 2);
            Grid rightChild = initializeGrid(pos * 2 + 1);
            
            int leftIndex = 2 * pos;
            int rightIndex = 2 * pos + 1;
            
            // If leftChild is null then rightChild also has to be null, we can stop the loop
            if (leftChild == null) {
                break;
            }
            // Check for swap with only leftChild when right is null
            if (rightChild == null) {
                pos = nullChildSwap(pos, leftIndex);
            } else {
                // Check for swap when both children need to be considered
                pos = pollSwap(pos, leftChild, rightChild, leftIndex, rightIndex);
            }
        }
    }
    
    /**
     * Swap positions of a parent and child grid in the queue
     * @param parentIndex of first grid
     * @param childIndex of second grid
     * @return childIndex to make calling methods' code cleaner
     */
    private int swapPositions(int parentIndex, int childIndex) {
        Grid first = queue[parentIndex];
        queue[parentIndex] = queue[childIndex];
        queue[childIndex] = first;
        return childIndex;
    }
    
    /**
     * After polling the last grid added to the queue is moved to the root for sorting
     * The grid's position is made available for a new grid
     */
    private void moveLatestGrid() {
        queue[1] = queue[firstOpen - 1];
        queue[firstOpen - 1] = null;
        firstOpen--;
    }
    
    /**
     * Index could be out of bounds so fetching a grid from queue requires exception handling
     * @param index
     * @return grid if there was one in the position of index, otherwise null
     */
    private Grid initializeGrid(int index) {
        try {
            Grid grid = queue[index];
            return grid;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
    
    /**
     * Checking for swap with left child when right child of a grid is null
     * @param parentIndex index of grid whose children are being checked
     * @param leftIndex index of left grid
     * @return -1 if no swap happened, index of leftChild if it did
     */
    private int nullChildSwap(int parentIndex, int leftIndex) {
        Grid parent = queue[parentIndex];
        Grid leftChild = queue[leftIndex];
        
        if (parent.compareTo(leftChild) == 1) {
            return swapPositions(parentIndex, leftIndex);
        } 
        return -1;
    }
    
    /**
     * Checking for swap when neither child is null
     * @param parentIndex index of grid whose children are being checked
     * @param leftChild grid in position 2 * parentIndex
     * @param rightChild grid in position 2 * parentIndex + 1
     * @param leftIndex index of left grid
     * @param rightIndex index of right grid
     * @return -1 if no swap happened, otherwise the index of the child that swap happened with
     */
    private int pollSwap(int parentIndex, Grid leftChild, Grid rightChild, int leftIndex, int rightIndex) {
        Grid parent = queue[parentIndex];
        
        if (leftChild.compareTo(rightChild) == -1) {
            if (parent.compareTo(leftChild) == 1) {
                return swapPositions(parentIndex, leftIndex);
            } 
        }
        if (parent.compareTo(rightChild) == 1) {
            return swapPositions(parentIndex, rightIndex);
        }
        
        return -1;
    }
}
