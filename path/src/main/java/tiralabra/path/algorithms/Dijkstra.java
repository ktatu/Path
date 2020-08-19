package tiralabra.path.algorithms;

import tiralabra.path.datastructures.PrioQueue;
import tiralabra.path.logic.Grid;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * Implementation of classic Dijkstra. Class is extended by Dijkstra variations that require same methods (currently A*)
 * @author Tatu
 */
public class Dijkstra extends Algorithm {

    protected PrioQueue prioQueue;
    
    public Dijkstra(GridMap gridMap, Scenario scen) {
        super(gridMap, scen);
        this.prioQueue = new PrioQueue();
    }

    /**
     * Adds the start grid to Priority Queue and sets initial grid distances to max Integer value
     */
    @Override
    public void initializeAlgorithm() {
        int startY = scen.getStartY();
        int startX = scen.getStartX();

        for (int y = 0; y < gridMap.getMapHeight(); y++) {
            for (int x = 0; x < gridMap.getMapWidth(); x++) {
                distance[y][x] = Integer.MAX_VALUE;
            }
        }
        distance[startY][startX] = 0;
        prioQueue.add(new Grid(startY, startX, 0));
    }
    
    /**
     * Runs initialization then Dijkstra. Goes through every possible adjacent grid for each grid taken from priority queue
     */
    @Override
    public void runAlgorithm() {
        initializeAlgorithm();
        
        while (!prioQueue.isEmpty()) {
            Grid current = prioQueue.poll(); 
            int gridY = current.getY();
            int gridX = current.getX();
            
            if (visited[gridY][gridX]) {
                continue;
            }
            
            visited[gridY][gridX] = true;
            
            if (goalVisited()) {
                break;
            }
            
            checkGrid(gridY - 1, gridX, current, false);
            checkGrid(gridY + 1, gridX, current, false);
            checkGrid(gridY, gridX - 1, current, false);
            checkGrid(gridY, gridX + 1, current, false);

            checkGrid(gridY - 1, gridX - 1, current, true);
            checkGrid(gridY - 1, gridX + 1, current, true);
            checkGrid(gridY + 1, gridX - 1, current, true);
            checkGrid(gridY + 1, gridX + 1, current, true);
        }
    }
    
    /**
     * Checks whether the grid can be moved into and does related Dijkstra operations if so
     * @param y coordinate of grid being checked
     * @param x coordinate of grid being checked
     * @param grid the grid from which the move is happening
     * @param diagonal whether the move is diagonal or not
     */
    private void checkGrid(int y, int x, Grid grid, boolean diagonal) {
        if (!isMovePossible(y, x, grid.getY(), grid.getX(), diagonal) || visited[y][x]) {
            return;
        }
        
        float newDistance = diagonal ? grid.getDistance() + sqrtTwo : grid.getDistance() + 1;
        if (newDistance < distance[y][x]) {
            distance[y][x] = newDistance;
            prevGrid[gridToInt(y, x)] = gridToInt(grid.getY(), grid.getX());
            prioQueue.add(new Grid(y, x, newDistance));
        }
    }
    
    /**
     * Calls other methods to check if the grid can be moved into based on the movement type
     * @param y coordinate of grid being checked
     * @param x coordinate of grid being checked
     * @param prevGridY y coordinate of the grid where the move is happening from
     * @param prevGridX x coordinate of the grid where the move is happening from
     * @param diagonal whether the move is diagonal or not 
     * @return true if the grid can be moved into
     */
    protected boolean isMovePossible(int y, int x, int prevGridY, int prevGridX, boolean diagonal) {
        if (diagonal) {
            return isValidDiagonalMove(y, x, prevGridY, prevGridX);
        }
        return isValidHorOrVerMove(y, x);
    }
    
    /**
     * Checks if a diagonal grid can be moved into
     * @param y coordinate of grid being checked
     * @param x coordinate of grid being checked
     * @param prevY y coordinate of the grid where the move is happening from
     * @param prevX x coordinate of the grid where the move is happening from
     * @return true if the grid can be moved into
     */
    protected boolean isValidDiagonalMove(int y, int x, int prevY, int prevX) {
        if (!isValidHorOrVerMove(y, x)) {
            return false;
        }
        
        if (y > prevY && x > prevX) {
            if (!isValidHorOrVerMove(y - 1, x) && !isValidHorOrVerMove(y, x - 1)) {
                return false;
            }
        } else if (y < prevY && x > prevX) {
            if (!isValidHorOrVerMove(y, x - 1) && !isValidHorOrVerMove(y + 1, x)) {
                return false;
            }
        } else if (y < prevY && x < prevX) {
            if (!isValidHorOrVerMove(y, x + 1) && !isValidHorOrVerMove(y + 1, x)) {
                return false;
            }
        } else if (y > prevY && x < prevX) {
            if (!isValidHorOrVerMove(y - 1, x) && !isValidHorOrVerMove(y, x + 1)) {
                return false;
            }
        }
        return true;
    }
}
