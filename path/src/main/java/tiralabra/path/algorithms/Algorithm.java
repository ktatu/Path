package tiralabra.path.algorithms;

import java.util.ArrayList;
import tiralabra.path.logic.Scenario;

/**
 * 
 * @author Tatu
 */
public abstract class Algorithm {
    
    protected long startTime;
    protected long endTime;
    
    protected Scenario scen;
    
    // grids (coordinates) are stored as integers for the purpose of tracking algorithms' movement on the map
    // e.g in a 3x3 map (0,0) is 0, (0,2) is 2 and (2,2) is 5
    protected int[] prevGrid;
    protected int[][] distance;
    protected int[][] gridMap;
    
    public Algorithm(int[][] gridMap, Scenario scen) {
        this.gridMap = gridMap.clone();
        this.distance = new int[this.gridMap.length][this.gridMap[0].length];
        this.scen = scen;
        
        this.prevGrid = new int[gridMap.length * gridMap[0].length];
        // prevGrid is also used to keep track of visited grids, -1 == unvisited
        for (int i = 0; i < prevGrid.length; i++) {
            prevGrid[i] = -1;
        }
    }
    
    abstract public void runAlgorithm();
    abstract public void initializeAlgorithm();
    
    protected double getRunTime() {
        return ((endTime - startTime) / 1e9);
    }
    
    /*
    protected boolean validDiagonalMove() {
        return true;
    }
    
    protected boolean validOctogonalMove(int gridY, int gridX) {
        if (gridY < 0 || gridY >= gridMap.length) {
            return false;
        } else if (gridX < 0 || gridX >= gridMap[0].length) {
            return false;
        } else if (gridMap[gridY][gridX] == 0) {
            return false;
        }
        return true;
    }
    */

    protected int gridToInt(int y, int x) {
        return y * gridMap[0].length + x;
    }
    
    protected int intToGridY(int gridAsInteger) {
        return gridAsInteger / gridMap[0].length;
    }
    
    protected int intToGridX(int gridAsInteger) {
        return gridAsInteger % gridMap[0].length;
    }
    
    protected ArrayList<String> tracePath() {
        return new ArrayList<>();
    }
}
