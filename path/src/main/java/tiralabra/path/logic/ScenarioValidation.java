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
     * Called by PathService to ensure that the Scenario can be run
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
}
