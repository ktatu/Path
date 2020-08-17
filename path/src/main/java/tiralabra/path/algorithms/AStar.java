package tiralabra.path.algorithms;

import java.util.PriorityQueue;
import tiralabra.path.logic.Grid;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * Basic implementation of A*. Uses diagonal distance (without considering unpassable terrain) as heuristic
 * @author Tatu
 */
public class AStar extends Dijkstra {
    
    /**
     * 
     * @param gridMap
     * @param scen
     */
    public AStar(GridMap gridMap, Scenario scen) {
        super(gridMap, scen);
    }

    /**
     *
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
        prioQueue.add(new Grid(startY, startX, 0, diagonalDistanceToGoal(startY, startX)));
    }
    
    @Override
    public void runAlgorithm() {
        initializeAlgorithm();
        
        while (!prioQueue.isEmpty()) {
            Grid current = prioQueue.poll(); 
            int gridY = current.getY();
            int gridX = current.getX();
            
            //System.out.println("x,y "+ gridX+","+gridY);
            
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
    
    private void checkGrid(int gridY, int gridX, Grid grid, boolean diagonal) {
        if (!isMovePossible(gridY, gridX, grid.getY(), grid.getX(), diagonal)) {
            return;
        }
        float newDistance = diagonal ? grid.getDistance() + sqrtTwo : grid.getDistance() + 1;
        if (newDistance < distance[gridY][gridX]) {
            distance[gridY][gridX] = newDistance;
            prevGrid[gridToInt(gridY, gridX)] = gridToInt(grid.getY(), grid.getX());
            prioQueue.add(new Grid(gridY, gridX, newDistance, diagonalDistanceToGoal(gridY, gridX)));
        }
    }
    
    private float diagonalDistanceToGoal(int y, int x) {
        int distanceY = Math.abs(scen.getGoalY() - y);
        int distanceX = Math.abs(scen.getGoalX() - x);
        
        int diagonalMoves = Math.min(distanceY, distanceX);
        int horAndVerMoves = Math.abs(distanceY - distanceX);
        
        return diagonalMoves * sqrtTwo + horAndVerMoves;
    }
}
