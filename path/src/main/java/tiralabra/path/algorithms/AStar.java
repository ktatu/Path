package tiralabra.path.algorithms;

import tiralabra.path.datastructures.List;
import tiralabra.path.datastructures.PrioQueue;
import tiralabra.path.logic.Grid;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * Basic implementation of A*. Uses diagonal distance (without considering unpassable terrain) as heuristic
 * @author Tatu
 */
public class AStar extends Dijkstra {

    /**
     * Necessary initialization operations for running A*
     * @param map
     * @param scen 
     */
    public void initializeAStar(GridMap map, Scenario scen) {
        initializeAlgorithm(map, scen);
        
        int startY = scen.getStartY();
        int startX = scen.getStartX();
        
        this.prioQueue = new PrioQueue();
        
        for (int y = 0; y < gridMap.getMapHeight(); y++) {
            for (int x = 0; x < gridMap.getMapWidth(); x++) {
                distance[y][x] = 1000000;
            }
        }
        distance[startY][startX] = 0;
        prioQueue.add(new Grid(startY, startX, 0, diagonalDistanceToGoal(startY, startX)));
    }
    
    /**
     * Iterate through grids until a path to goal grid is found
     * @param map
     * @param scen 
     */
    @Override
    public void runAlgorithm(GridMap map, Scenario scen) {
        initializeAStar(map, scen);
        
        while (!prioQueue.isEmpty()) {
            Grid current = prioQueue.poll(); 
            int y = current.getY();
            int x = current.getX();
            
            if (visited[y][x]) {
                continue;
            }
            
            visited[y][x] = true;
            
            if (goalVisited()) {
                break;
            }
            
            List neighbors = neighborList(y, x);
            while (neighbors.canIterate()) {
                int neighbor = neighbors.getNext();
                checkGrid(neighbor, current);
            }
        }
        if (goalVisited()) {
            constructPath();
        }
    }
    
    /**
     * Consider the grid for priority queue update data structures if it gets added 
     * @param nbor the grid being considered
     * @param prev grid from which the movement to nbor is happening
     */
    private void checkGrid(int nbor, Grid prev) {
        int x = intToGridX(nbor);
        int y = intToGridY(nbor);
        
        boolean diagonal = (x != prev.getX() && y != prev.getY());
        
        if (!isMovePossible(y, x, prev.getY(), prev.getX(), diagonal)) {
            return;
        }
        
        float newDistance = diagonal ? prev.getDistance() + sqrtTwo : prev.getDistance() + 1;
        if (newDistance < distance[y][x]) {
            distance[y][x] = newDistance;
            prevGrid[gridToInt(y, x)] = gridToInt(prev.getY(), prev.getX());
            prioQueue.add(new Grid(y, x, newDistance, diagonalDistanceToGoal(y, x)));
        }
    }
    
    /**
     * Calculating the heuristic from (x,y) to goal grid
     * @param y coordinate
     * @param x coordinate
     * @return diagonal distance to goal
     */
    private float diagonalDistanceToGoal(int y, int x) {
        int distanceY = MathUtil.abs(scen.getGoalY() - y);
        int distanceX = MathUtil.abs(scen.getGoalX() - x);
        
        int diagonalMoves = MathUtil.min(distanceY, distanceX);
        int horAndVerMoves = MathUtil.abs(distanceY - distanceX);
        
        return diagonalMoves * sqrtTwo + horAndVerMoves;
    }
}
