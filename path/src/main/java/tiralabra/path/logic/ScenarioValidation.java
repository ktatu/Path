package tiralabra.path.logic;

import tiralabra.path.logic.exceptions.InvalidScenarioException;

/**
 * Ensures that a given Scenario can be run by algorithms and a path between start and end grids exists
 * @author Tatu
 */
public class ScenarioValidation {
    
    private Scenario scen;
    private GridMap gridMap;
    
    /**
     * Called by app logic to validate a scenario
     * @param gridMap the map on which the Scenario would be run
     * @param scen Scenario to be validated
     * @throws InvalidScenarioException  if Scenario cannot be run on given GridMap
     */
    public void validateScenario(GridMap gridMap, Scenario scen) throws InvalidScenarioException {
        this.gridMap = gridMap;
        this.scen = scen;
        
        if (!startAndGoalAreDifferent()) {
            throw new InvalidScenarioException("Start and goal coordinates were equal");
        } else if (!scenCoordinatesAreWithinBounds()) {
            throw new InvalidScenarioException("Scenario coordinates were not within map boundaries");
        } else if (!passableTerrainInScenCoordinates()) {
            throw new InvalidScenarioException("Scenario either started or ended in unpassable terrain");
        } else if (!unobstructedPath()) {
            throw new InvalidScenarioException("No path existed between start and goal coordinates");
        }
    }
    
    /**
     * Checks if Scenario starts and ends on same grid
     * @return true if grids are not the same
     */
    private boolean startAndGoalAreDifferent() {
        return !(scen.getStartY() == scen.getGoalY() && scen.getStartX() == scen.getGoalX());
    }
    
    /**
     * Checks that Scenario coordinates are within map boundaries
     * @return true if coordinates are within bounds
     */
    private boolean scenCoordinatesAreWithinBounds() {
        return !(scen.getStartY() < 0 || scen.getGoalY() >= gridMap.getMapHeight()
                || scen.getStartX() < 0 || scen.getStartX() >= gridMap.getMapWidth()
                || scen.getGoalY() < 0 || scen.getGoalY() >= gridMap.getMapHeight()
                || scen.getGoalX() < 0 || scen.getGoalX() >= gridMap.getMapWidth());
    }
    
    /**
     * Checks that start and goal grids are passable terrain
     * @return true if both are
     */
    private boolean passableTerrainInScenCoordinates() {
        if (!gridMap.isPassable(gridMap.getGrid(scen.getStartY(), scen.getStartX()))) {
            return false;
        } else if (!gridMap.isPassable(gridMap.getGrid(scen.getGoalY(), scen.getGoalX()))) {
            return false;
        }
        return true;
    }
    
    /**
     * Uses DFS to check if a path between start and end grids exists
     * @return true if a path exists
     */
    private boolean unobstructedPath() {
        boolean[][] visited = new boolean[gridMap.getMapHeight()][gridMap.getMapWidth()];
        depthFirstSearch(scen.getStartY(), scen.getStartX(), visited);
        return visited[scen.getGoalY()][scen.getGoalX()];
    }
    
    /**
     * DFS
     * Algorithm is only used as an utility tool since the found path length is completely random
     * @param y start coordinate from Scenario
     * @param x start coordinate from Scenario
     * @param visited boolean array for DFS to keep track of where it has been
     */
    private void depthFirstSearch(int y, int x, boolean[][] visited) {
        if (y < 0 || y >= gridMap.getMapHeight() || x < 0 || x >= gridMap.getMapWidth()) {
            return;
        } else if (!gridMap.isPassable(gridMap.getGrid(y, x)) || visited[y][x]) {
            return;
        }
        visited[y][x] = true;
        
        depthFirstSearch(y - 1, x, visited);
        depthFirstSearch(y + 1, x, visited);
        depthFirstSearch(y, x - 1, visited);
        depthFirstSearch(y, x + 1, visited);
    }
}
