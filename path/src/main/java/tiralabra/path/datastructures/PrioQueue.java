package tiralabra.path.datastructures;

import tiralabra.path.logic.Grid;

/**
 * Minimum heap, Grids ordered by distances
 * @author Tatu
 */
public class PrioQueue {
    
    private final Grid[] prioQueue;
    private int firstOpenCell;
    
    public PrioQueue(int mapSize) {
        prioQueue = new Grid[mapSize + 1];
        this.firstOpenCell = 1;
    }
    
    /**
     * Adds a grid to prioQueue, increases index and calls sorting function
     * @param grid to be added into queue 
     */
    public void add(Grid grid) {
        prioQueue[firstOpenCell] = grid;
        firstOpenCell++;
        sortQueueAfterAdding();
    }
    
    /**
     * Grabs the smallest Grid from queue, calls for a (different) sorting function and return the grid
     * @return the smallest Grid in queue which is always the root node
     */
    public Grid poll() {
        Grid gridToReturn = prioQueue[1];
        sortQueueAfterPolling();
        return gridToReturn;
    }
    
    /**
     * Check whether queue is empty. Cell zero is kept empty for ease of managing the queue, so the first available cell is 1
     * @return true if empty 
     */
    public boolean isEmpty() {
        return (firstOpenCell == 1);
    }
    
    /**
     * Swaps the recently added Grid with its parent until it's in the correct position priority-wise
     */
    private void sortQueueAfterAdding() {
        int pos = firstOpenCell - 1;
        while (pos > 1) {
            if (prioQueue[pos].compareTo(prioQueue[pos / 2]) == -1) {
                swapPositions(pos, pos / 2);
                pos /= 2;
            } else {
                break;
            }
        }
    }
    
    /**
     * Places the biggest Grid in queue to root position and swaps it with its children until queue is in order
     */
    private void sortQueueAfterPolling() {
        prioQueue[1] = prioQueue[firstOpenCell - 1];
        prioQueue[firstOpenCell - 1] = null;
        int indexOfLastGrid = firstOpenCell - 1;
        firstOpenCell--;
        
        int pos = 1;
        while (pos < indexOfLastGrid - 1) {
            int newPos = swapDownwards(pos);
            if (newPos != -1) {
                pos = newPos;
            } else {
                break;
            }
        }
    }
    
    /**
     * Checks the possible swapping scenarios and does the operation if necessary
     * @param idxParent index of Grid that is currently being compared to its children
     * @return -1 if no swapping happened, otherwise the index of position where the parent ended up after it occurred
     */
    private int swapDownwards(int idxParent) {
        Grid parent = prioQueue[idxParent];
        
        int idxLeftChild = idxParent * 2;
        int idxRightChild = idxParent * 2 + 1;
        
        Grid leftChild = idxLeftChild > firstOpenCell ? null : prioQueue[idxParent * 2];
        Grid rightChild = leftChild == null ? null : prioQueue[idxParent * 2 + 1];
        
        if (leftChild == null && rightChild == null) {
            return -1;
        }
        
        if (leftChild == null) {
            if (parent.compareTo(rightChild) == 1) {
                swapPositions(idxParent, idxRightChild);
                return idxRightChild;
            }
        } else if (rightChild == null) {
            if (parent.compareTo(leftChild) == 1) {
                swapPositions(idxParent, idxLeftChild);
                return idxLeftChild;
            }
        } else {
            int idxSmallestChild = prioQueue[idxLeftChild].compareTo(prioQueue[idxRightChild]) == -1 ? idxLeftChild : idxRightChild;
            if (prioQueue[idxParent].compareTo(prioQueue[idxSmallestChild]) == 1) {
                swapPositions(idxParent, idxSmallestChild);
                return idxSmallestChild;
            }
        }
        return -1;
    }
    
    /**
     * Swapping operation
     * @param idx1 index of first Grid
     * @param idx2 index of second Grid
     */
    private void swapPositions(int idx1, int idx2) {
        Grid first = prioQueue[idx1];
        prioQueue[idx1] = prioQueue[idx2];
        prioQueue[idx2] = first;
    }
    
    // poista tämä kun valmista
    public void printQueue() {
        for (int i = 1; i < firstOpenCell; i++) {
            System.out.println(prioQueue[i].getDistance() + prioQueue[i].getEstimation());
        }
    }
    
    public void printFirstOpenCell() {
        System.out.println(this.firstOpenCell);
    }

}


/*
    private int swapDownwards(int idxParent) {
        Grid parent = prioQueue[idxParent];
        
        int idxLeftChild = idxParent * 2;
        int idxRightChild = idxParent * 2 + 1;
        
        Grid leftChild = idxLeftChild > queueSize ? null : prioQueue[idxParent * 2];
        Grid rightChild = leftChild == null ? null : prioQueue[idxParent * 2 + 1];
        
        if (leftChild == null && rightChild == null) {
            return -1;
        }
        
        if (leftChild == null) {
            if (parent.compareTo(rightChild) == 1) {
                swapPositions(idxParent, idxRightChild);
                return idxRightChild;
            }
        } else if (rightChild == null) {
            if (parent.compareTo(leftChild) == 1) {
                swapPositions(idxParent, idxLeftChild);
                return idxLeftChild;
            }
        } else {
            int idxSmallestChild = prioQueue[idxLeftChild].compareTo(prioQueue[idxRightChild]) == -1 ? idxLeftChild : idxRightChild;
            if (prioQueue[idxParent].compareTo(prioQueue[idxSmallestChild]) == 1) {
                swapPositions(idxParent, idxSmallestChild);
                return idxSmallestChild;
            }
        }
        return -1;
    }
*/