package tiralabra.path.algorithms;

import java.util.ArrayList;
import tiralabra.path.logic.Grid;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * Ei toimi vielä kunnolla
 * Jump Point Search based on Daniel Harabor's and Alban Grastien's paper "Online Graph Pruning for Pathfinding on Grid Maps (january 2011)"
 * @author Tatu
 */
public class JumpPointSearch extends Dijkstra {
    
    public JumpPointSearch(GridMap gridMap, Scenario scen) {
        super(gridMap, scen);
    }

    @Override
    public void initializeAlgorithm() {
        int startY = scen.getStartY();
        int startX = scen.getStartX();
        
        for (int y = 0; y < gridMap.getMapHeight(); y++) {
            for (int x = 0; x < gridMap.getMapWidth(); x++) {
                distance[y][x] = Integer.MAX_VALUE;
            }
        }
        distance[startY][startX] = 0;
        prioQueue.add(new Grid(startY, startX, 0, 0));
    }
    
    @Override
    public void runAlgorithm() {
        initializeAlgorithm();
        
        startTime = System.nanoTime();
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
            
            ArrayList<Integer> prunedNeighbors = pruneNeighbors(gridY, gridX);
            for (int neighbor : prunedNeighbors) {
                int directionY = intToGridY(neighbor) - gridY;
                int directionX = intToGridX(neighbor) - gridX;
                
                checkDirection(gridY, gridX, directionY, directionX);
            }
        }
        endTime = System.nanoTime();
    }
    
    // pruned grids are directions from y,x that are not worth scanning with jump()
    private ArrayList<Integer> pruneNeighbors(int y, int x) {
        ArrayList<Integer> prunedNeighbors = new ArrayList<>();
        // if (y,x) is start grid then nothing should be pruned
        boolean isStartGrid = (scen.getStartY() == y && scen.getStartX() == x);
        
        for (int neighborY = y - 1; neighborY <= y + 1; neighborY++) {
            for (int neighborX = x - 1; neighborX <= x + 1; neighborX++) {
                if (neighborY == y && neighborX == x || outOfBounds(neighborY, neighborX)) {
                    continue;
                }
                if (isStartGrid) {
                    prunedNeighbors.add(gridToInt(neighborY, neighborX));
                } else {
                    if (!pruneByDistance(y, x, neighborY, neighborX)) {
                        prunedNeighbors.add(gridToInt(neighborY, neighborX));
                    }
                }
            }
        }
        return prunedNeighbors;
    }
    
    private boolean pruneByDistance(int y, int x, int neighborY, int neighborX) {
        int parentAsInt = prevGrid[gridToInt(y, x)];
        int parentY = intToGridY(parentAsInt);
        int parentX = intToGridX(parentAsInt);
        
        boolean diagonalMove = (y - parentY != 0 && x - parentX != 0);
        
        float distThroughGrid = diagonalDistance(parentY, parentX, y, x) + diagonalDistance(y, x, neighborY, neighborX);
        if (diagonalMove) {
            if (diagonalDistance(parentY, parentX, neighborY, neighborX) < distThroughGrid) {
                return true;
            }
        } else {
            if (diagonalDistance(parentY, parentX, neighborY, neighborX) < distThroughGrid) {
                return true;
            }
        }
        return false;
    }
    
    private void checkDirection(int y, int x, int directionY, int directionX) {
        int jumpPoint = jump(y, x, directionY, directionX);
        if (jumpPoint != -1) {
            int jumpPointY = intToGridY(jumpPoint);
            int jumpPointX = intToGridX(jumpPoint);
            //System.out.println("lisätään jump point " + jumpPointY + ", " + jumpPointX);
            
            float distToNewJumpPoint = distance[y][x] + diagonalDistance(y, x, jumpPointY, jumpPointX);
            float heuristicEstimate = diagonalDistance(jumpPointY, jumpPointX, scen.getGoalY(), scen.getGoalX());
            
            distance[jumpPointY][jumpPointX] = distToNewJumpPoint;
            prevGrid[gridToInt(jumpPointY, jumpPointX)] = gridToInt(y, x);
            prioQueue.add(new Grid(jumpPointY, jumpPointX, distToNewJumpPoint, heuristicEstimate));
            jumpPoints++;
        }
    }
    
    private int jump(int y, int x, int directionY, int directionX) {
        int nextY = y + directionY;
        int nextX = x + directionX;
        boolean diagonal = (directionY != 0 && directionX != 0);
        
        if (!isMovePossible(nextY, nextX, y, x, diagonal)) {
            return -1;
        }
        
        if (nextY == scen.getGoalY() && nextX == scen.getGoalX()) {
            return gridToInt(nextY, nextX);
        }
        
        if (diagonal) {
            if (forcedGridOnDiagonal(nextY, nextX, y, x)) {
                return gridToInt(nextY, nextX);
            }
        } else {
            if (forcedVerticalOrHorizontal(nextY, nextX, (directionY != 0))) {
                return gridToInt(nextY, nextX);
            }
        }
        
        if (diagonal) {
            if (jump(nextY, nextX, directionY, 0) != -1 || jump(nextY, nextX, 0, directionX) != -1) {
                return gridToInt(nextY, nextX);
            }
        }
        
        return jump(nextY, nextX, directionY, directionX);
    }
    
    /**
     * Forced neighbors only occur when a grid adjacent to x and its neighbor is unpassable
     * @param y coordinate
     * @param x coordinate
     * @param prevY coordinate of neighbor being checked
     * @param prevX coordinate of neighbor being checked
     * @return true if there is an adjacent unpassable grid
     */
    private boolean forcedGridOnDiagonal(int y, int x, int prevY, int prevX) {
        if (y > prevY && x > prevX) {
            if (!isValidHorOrVerMove(y - 1, x) || !isValidHorOrVerMove(y, x - 1)) {
                return true;
            }
        } else if (y < prevY && x > prevX) {
            if (!isValidHorOrVerMove(y, x - 1) || !isValidHorOrVerMove(y + 1, x)) {
                return true;
            }
        } else if (y < prevY && x < prevX) {
            if (!isValidHorOrVerMove(y, x + 1) || !isValidHorOrVerMove(y + 1, x)) {
                return true;
            }
        } else if (y > prevY && x < prevX) {
            if (!isValidHorOrVerMove(y - 1, x) || !isValidHorOrVerMove(y, x + 1)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean forcedVerticalOrHorizontal(int y, int x, boolean verticalMove) {
        if (verticalMove) {
            if (isForced(y, x - 1) || isForced(y, x + 1)) {
                return true;
            }
        } else {
            if (isForced(y - 1, x) || isForced(y + 1, x)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isForced(int y, int x) {
        if (!outOfBounds(y, x)) {
            if (!gridMap.isPassable(gridMap.getGrid(y, x))) {
                return true;
            }
        }
        return false;
    }
    
    private float diagonalDistance(int startY, int startX, int targetY, int targetX) {
        int distanceY = Math.abs(targetY - startY);
        int distanceX = Math.abs(targetX - startX);
        
        int diagonalMoves = Math.min(distanceY, distanceX);
        int horAndVerMoves = Math.abs(distanceY - distanceX);
        
        return diagonalMoves * sqrtTwo + horAndVerMoves;
    }
}
