package tiralabra.path.algorithms;

import tiralabra.path.logic.Grid;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * Basic implementation of A*. Uses diagonal distance (without considering unpassable terrain) as heuristic
 * @author Tatu
 */
public class AStar extends Dijkstra {
    
    public AStar(GridMap gridMap, Scenario scen) {
        super(gridMap, scen);
    }

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
    
    
    private void checkGrid(int y, int x, Grid grid, boolean diagonal) {
        if (!isMovePossible(y, x, grid.getY(), grid.getX(), diagonal)) {
            return;
        }
        
        float newDistance = diagonal ? grid.getDistance() + sqrtTwo : grid.getDistance() + 1;
        if (newDistance < distance[y][x]) {
            distance[y][x] = newDistance;
            prevGrid[gridToInt(y, x)] = gridToInt(grid.getY(), grid.getX());
            prioQueue.add(new Grid(y, x, newDistance, diagonalDistanceToGoal(y, x)));
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
