package tiralabra.path.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * Methods and data structures used by all algorithms
 * @author Tatu
 */
public abstract class Algorithm {
    
    // tilapäinen
    public int jumpPoints = 1;
    
    protected long startTime;
    protected long endTime;
    
    // Keeps track of what was each grid's predecessor
    // Each cell in prevGrid[] represents a grid, which has been converted into an integer based on its coordinates
    public int[] prevGrid;
    // Distance from start grid to others
    protected float[][] distance;
    public boolean[][] visited;
    
    protected Scenario scen;
    protected GridMap gridMap;
    
    //float sqrtTwo = (float) Math.sqrt(2);
    float sqrtTwo = (float) 1.4;
    
    /**
     * Sets up data structures for algorithms
     * @param gridMap the map the algorithm is running on
     * @param scen start and goal coordinates of algorithm
     */
    public Algorithm(GridMap gridMap, Scenario scen) {
        this.gridMap = gridMap;
        this.distance = new float[this.gridMap.getMapHeight()][this.gridMap.getMapWidth()];
        this.visited = new boolean[this.gridMap.getMapHeight()][this.gridMap.getMapWidth()];
        this.scen = scen;
        
        // Same initialization for all algorithms so done in constructor instead of initializeAlgorithm()
        this.prevGrid = new int[this.gridMap.getMapHeight() * this.gridMap.getMapWidth()];
        for (int i = 0; i < prevGrid.length; i++) {
            prevGrid[i] = -1;
        }
    }
    
    // Operations needed before algorithm can be run are made here. Not included in algorithm runtime
    abstract public void initializeAlgorithm();
    // Run the algorithm until the shortest path is found or all viable grids have been visited
    abstract public void runAlgorithm();
    
    /**
     * Check if coordinates are within map boundaries and the grid in these coordinates is passable terrain
     * @param y coordinate
     * @param x coordinate
     * @return true if grid can be moved to, otherwise false
     */
    protected boolean isValidHorOrVerMove(int y, int x) {
        if (outOfBounds(y, x)) {
            return false;
        }
        return gridMap.isPassable(gridMap.getGrid(y, x));
    }
    
    protected boolean outOfBounds(int y, int x) {
        if (y < 0 || y >= gridMap.getMapHeight() || x < 0 || x >= gridMap.getMapWidth()) {
            return true;
        }
        return false;
    }

    /**
     * Grid's coordinates are converted into an integer
     * @param y coordinate
     * @param x coordinate
     * @return Grid's value as integer.
     */
    public int gridToInt(int y, int x) {
        return y * gridMap.getMapWidth() + x;
    }
    
    /**
     * Reads a grid's y coordinate from an integer 
     * @param gridAsInteger grid's integer value
     * @return y coordinate of the grid
     */
    public int intToGridY(int gridAsInteger) {
        return gridAsInteger / gridMap.getMapWidth();
    }
    
    /**
     * Reads a grid's x coordinate from an integer
     * @param gridAsInteger grid's integer value
     * @return x coordinate of the grid
     */
    public int intToGridX(int gridAsInteger) {
        return gridAsInteger % gridMap.getMapWidth();
    }
    
    /**
     * Checks from visited[][] whether algorithm has gone over the goal grid
     * @return true if it has
     */
    public boolean goalVisited() {
        return visited[scen.getGoalY()][scen.getGoalX()];
    }
    
    /**
     * Runtime of algorithm
     * @return runtime in seconds
     */
    public double getRunTime() {
        return ((endTime - startTime) / 1e9);
    }
    
    /**
     * Distance from start grid to goal grid
     * @return distance as float retrieved from distance matrix
     */
    public float getPathLength() {
        return distance[scen.getGoalY()][scen.getGoalX()];
    }
    
    /**
     * Prints coordinates of every grid on the shortest path from start grid to end grid
     */
    public void printPath() {
        ArrayList<Integer> path = new ArrayList<>();
        int goalGridAsInt = gridToInt(scen.getGoalY(), scen.getGoalX());
        
        while (prevGrid[goalGridAsInt] != -1) {
            path.add(goalGridAsInt);
            goalGridAsInt = prevGrid[goalGridAsInt];
        }
        
        path.add(gridToInt(scen.getStartY(), scen.getStartX()));
        Collections.reverse(path);
        
        for (int grid : path) {
            int y = intToGridY(grid);
            int x = intToGridX(grid);
            System.out.println("y: " + y + ", x: " + x);
        }
    }

    public int getJumpPoints() {
        return jumpPoints;
    }
}
