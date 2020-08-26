package tiralabra.path.algorithms;

import tiralabra.path.datastructures.GridList;
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
            int y = current.getY();
            int x = current.getX();
            
            if (visited[y][x]) {
                continue;
            }
            
            visited[y][x] = true;
            
            if (goalVisited()) {
                break;
            }
            
            GridList neighbors = neighborList(y, x);
            while (neighbors.canIterate()) {
                int neighbor = neighbors.getNext();
                checkGrid(neighbor, current);
            }
        }
        if (goalVisited()) {
            constructPath();
        }
    }
    
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
    
    private float diagonalDistanceToGoal(int y, int x) {
        int distanceY = Math.abs(scen.getGoalY() - y);
        int distanceX = Math.abs(scen.getGoalX() - x);
        
        int diagonalMoves = Math.min(distanceY, distanceX);
        int horAndVerMoves = Math.abs(distanceY - distanceX);
        
        return diagonalMoves * sqrtTwo + horAndVerMoves;
    }
}
