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
    private FifoQueue queue;
    
    /**
     * Start grid related operations
     * @param map
     * @param scen
     */
    public void initializeBFS(GridMap map, Scenario scen) {
        initializeAlgorithm(map, scen);
        
        this.queue = new FifoQueue(gridMap.getMapHeight() * gridMap.getMapWidth());
        
        int startY = scen.getStartY();
        int startX = scen.getStartX();
        int gridAsInt = gridToInt(startY, startX);
        
        queue.add(gridAsInt);
        distance[startY][startX] = 0;
        visited[startY][startX] = true;
    }

    /**
     * Iterate through first-in-first-out queue until a path to goal grid is found
     */
    @Override
    public void runAlgorithm(GridMap map, Scenario scen) {
        initializeBFS(map, scen);
        
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
     * Goes through all adjacent non-diagonal neighbors of grid (x,y)
     * @param y current grid's y coordinate
     * @param x  current grids x coordinate
     */
    private void checkAdjacentGrids(int y, int x) {
        checkGrid(y - 1, x, y, x);
        checkGrid(y + 1, x, y, x);
        checkGrid(y, x - 1, y, x);
        checkGrid(y, x + 1, y, x);
    }
    
    /**
     * Checks whether the grid can be moved into and does related operations if so
     * @param y y coordinate of grid currently being visited
     * @param x x coordinate of grid currently being visited
     * @param pY y coordinate of the previous grid
     * @param pX x cooordinate of the previous grid
     */
    private void checkGrid(int y, int x, int pY, int pX) {
        if (!isPassable(y, x)) {
            return;
        } else if (visited[y][x]) {
            return;
        }
        
        visited[y][x] = true;
        queue.add(gridToInt(y, x));
        prevGrid[gridToInt(y, x)] = gridToInt(pY, pX);
        distance[y][x] = distance[pY][pX] + 1;
    }
}
