package tiralabra.path.algorithms;

import tiralabra.path.datastructures.FifoQueue;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * Breadth-first-search looks for shortest path by moving only horizontally and vertically on the map
 * @author Tatu
 */
public class BreadthFirstSearch extends Algorithm {
    
    // Grids are stored as integers into a first-in-first-out queue
    private final FifoQueue queue;
    
    /**
     * Makes a call to Algorithm to set data structures and initializes queue
     * @param gridMap
     * @param scen 
     */
    public BreadthFirstSearch(GridMap gridMap, Scenario scen) {
        super(gridMap, scen);
        this.queue = new FifoQueue(gridMap.getMapHeight() * gridMap.getMapWidth());
    }
    
    /**
     * Start grid related operations
     */
    @Override
    public void initializeAlgorithm() {
        int startY = scen.getStartY();
        int startX = scen.getStartX();
        int gridAsInt = gridToInt(startY, startX);
        
        queue.add(gridAsInt);
        distance[startY][startX] = 0;
        visited[startY][startX] = true;
    }

    /**
     * Runs initialization then BFS
     */
    @Override
    public void runAlgorithm() {
        initializeAlgorithm();
        
        while (!queue.isEmpty()) {
            if (goalVisited()) {
                break;
            }
            int gridAsInt = queue.poll();
            
            int gridY = intToGridY(gridAsInt);
            int gridX = intToGridX(gridAsInt);
            
            checkAdjacentGrids(gridY, gridX);
        }
        
        if (goalVisited()) {
            constructPath();
        }
    }
    
    /**
     * Goes through all adjacent non-diagonal neighbors of grid given as coordinates
     * @param gridY current grid's y coordinate
     * @param gridX  current grids x coordinate
     */
    private void checkAdjacentGrids(int gridY, int gridX) {
        checkGrid(gridY - 1, gridX, gridY, gridX);
        checkGrid(gridY + 1, gridX, gridY, gridX);
        checkGrid(gridY, gridX - 1, gridY, gridX);
        checkGrid(gridY, gridX + 1, gridY, gridX);
    }
    
    /**
     * Checks whether the grid can be moved into and does related BFS operations if so
     * @param gridY y coordinate of grid currently being visited
     * @param gridX x coordinate of grid currently being visited
     * @param prevGridY y coordinate of the previous grid
     * @param prevGridX x cooordinate of the previous grid
     */
    private void checkGrid(int gridY, int gridX, int prevGridY, int prevGridX) {
        if (!isValidHorOrVerMove(gridY, gridX)) {
            return;
        } else if (visited[gridY][gridX]) {
            return;
        }
        
        visited[gridY][gridX] = true;
        queue.add(gridToInt(gridY, gridX));
        prevGrid[gridToInt(gridY, gridX)] = gridToInt(prevGridY, prevGridX);
        distance[gridY][gridX] = distance[prevGridY][prevGridX] + 1;
    }
}
