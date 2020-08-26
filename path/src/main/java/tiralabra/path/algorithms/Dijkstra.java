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

    public PrioQueue prioQueue;
    
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
                distance[y][x] = 1000000;
            }
        }
        distance[startY][startX] = 0;
        prioQueue.add(new Grid(startY, startX, 0));
    }
    
    /**
     * Runs initialization then Dijkstra. Goes through every neighbor of each grid polled from priority queue
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
        
        if (goalVisited()) {
            constructPath();
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
        
        float newDistance = diagonal ? grid.getDistance() + sqrtTwo : grid.getDistance() + 1.0f;
        
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
        return isPassable(y, x);
    }

    /**
     * Check if diagonal move can occur. The move isn't viable if the two adjacent grids next to both the grid and its parent are unpassable terrain
     * @param y coordinate
     * @param x coordinate
     * @param pY parent coordinate
     * @param pX parent coordinate
     * @return true if the move is not blocked
     */
    protected boolean isValidDiagonalMove(int y, int x, int pY, int pX) {
        if (!isPassable(y, x)) {
            return false;
        }
        
        int dirY = getDirectionY(y, pY);
        int dirX = getDirectionX(x, pX);
        
        if (dirY == 1 && dirX == 1) { // Southeast
            if (!isPassable(y - 1, x) && !isPassable(y, x - 1)) {
                return false;
            }
        } else if (dirY == -1 && dirX == 1) { // Northeast
            if (!isPassable(y, x - 1) && !isPassable(y + 1, x)) {
                return false;
            }
        } else if (dirY == -1 && dirX == -1) { // Northwest
            if (!isPassable(y, x + 1) && !isPassable(y + 1, x)) {
                return false;
            }
        } else if (dirY == 1 && dirX == -1) { // Southwest
            if (!isPassable(y - 1, x) && !isPassable(y, x + 1)) {
                return false;
            }
        }
        return true;         
    }
    
    /**
     * Determines vertical direction based on two grids' y coordinates
     * @param y coordinate
     * @param pY parent coordinate
     * @return 1 for upwards and -1 for downwards, 0 is no vertical movement
     */
    protected int getDirectionY(int y, int pY) {
        if (y > pY) {
            return 1;
        } else if (y == pY) {
            return 0;
        } else {
            return -1;
        }
    }
    
    /**
     * Determines horizontal direction based on two grids' x coordinates
     * @param x coordinate
     * @param pX parent coordinate
     * @return  1 for right, -1 for left, 0 is no horizontal movement
     */
    protected int getDirectionX(int x, int pX) {
        if (x > pX) {
            return 1;
        } else if (x == pX) {
            return 0;
        } else {
            return -1;
        }
    }
}
