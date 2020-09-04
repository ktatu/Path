package tiralabra.path.algorithms;

import tiralabra.path.datastructures.List;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * Upper class for actual algorithm implementation classes
 * @author Tatu
 */
public abstract class Algorithm {
    
    /**
     * Keeps track of each grid's predecessor
     * Each cell in prevGrid[] represents a grid, which has been converted into an integer based on its coordinates
     */
    protected int[] prevGrid;
    // Distance from start grid to others
    protected float[][] distance;
    public boolean[][] visited;
    
    public Scenario scen;
    public GridMap gridMap;
    
    public List path;
    
    protected final float sqrtTwo = (float) 1.4;
    
    /**
     * Running the algorithm with given map and scenario
     * @param map
     * @param scen
     */
    abstract public void runAlgorithm(GridMap map, Scenario scen);
    
    /**
     * Grid's coordinates are converted into an integer
     * @param y coordinate
     * @param x coordinate
     * @return Grid's value as integer.
     */
    public int gridToInt(int y, int x) {
        if (outOfBounds(y, x)) {
            return -1;
        }
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
     * Checks from visited[][] if algorithm has visited the goal grid
     * @return true if it has been visited
     */
    public boolean goalVisited() {
        return visited[scen.getGoalY()][scen.getGoalX()];
    }
    
    /**
     * Distance from start grid to goal grid
     * @return path length from distance[][]
     */
    public float getPathLength() {
        return distance[scen.getGoalY()][scen.getGoalX()];
    }
    
    /**
     * Check if the grid (x,y) is within bounds and passable terrain
     * @param y
     * @param x
     * @return 
     */
    protected boolean isPassable(int y, int x) {
        if (outOfBounds(y, x)) {
            return false;
        }
        return gridMap.passableGrid(y, x);
    }
    
    /**
     * Creates a path from goal grid to start grid
     */
    protected void constructPath() {
        int goalGridAsInt = gridToInt(scen.getGoalY(), scen.getGoalX());
        
        while (prevGrid[goalGridAsInt] != -1) {
            path.add(goalGridAsInt);
            goalGridAsInt = prevGrid[goalGridAsInt];
        }
    }
    
    /**
     * Initialization operations shared between all algorithms
     * @param map
     * @param scen 
     */
    protected void initializeAlgorithm(GridMap map, Scenario scen) {
        this.path = new List(map.getMapHeight() + map.getMapWidth());
        this.gridMap = map;
        this.distance = new float[this.gridMap.getMapHeight()][this.gridMap.getMapWidth()];
        this.visited = new boolean[this.gridMap.getMapHeight()][this.gridMap.getMapWidth()];
        this.scen = scen;
        this.prevGrid = new int[this.gridMap.getMapHeight() * this.gridMap.getMapWidth()];
        
        prevGrid[gridToInt(scen.getStartY(), scen.getStartX())] = -1;
    }
    
    /**
     * Check if (x,y) is actually inside the map
     * @param y coordinate
     * @param x coordinate
     * @return true if (x,y) is not within map boundaries
     */
    private boolean outOfBounds(int y, int x) {
        if (y < 0 || y >= gridMap.getMapHeight() || x < 0 || x >= gridMap.getMapWidth()) {
            return true;
        }
        return false;
    }
}
