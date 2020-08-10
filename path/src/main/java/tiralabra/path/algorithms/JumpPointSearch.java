package tiralabra.path.algorithms;

import tiralabra.path.logic.Grid;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 *
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
            
            checkGrid(gridY - 1, gridX, current, false);
            checkGrid(gridY + 1, gridX, current, false);
            checkGrid(gridY, gridX - 1, current, false);
            checkGrid(gridY, gridX + 1, current, false);

            checkGrid(gridY - 1, gridX - 1, current, true);
            checkGrid(gridY - 1, gridX + 1, current, true);
            checkGrid(gridY + 1, gridX - 1, current, true);
            checkGrid(gridY + 1, gridX + 1, current, true);
        }
        endTime = System.nanoTime();
    }
    
    private void checkGrid(int y, int x, Grid grid, boolean diagonal) {
        if (gridIsPruned(y, x, grid, diagonal)) {
            return;
        }
        
        int directionY = y - grid.getY();
        int directionX = x - grid.getX();
        
        int jumpPoint = jump(y, x, directionY, directionX, grid, diagonal);
        if (jumpPoint != -1) {
            int jumpPointY = intToGridY(jumpPoint);
            int jumpPointX = intToGridX(jumpPoint);
            
            float distToNewJumpPoint = distance[grid.getY()][grid.getX()] + diagonalDistance(grid.getY(), grid.getX(), jumpPointY, jumpPointX);
            float heuristicDistToGoal = diagonalDistance(jumpPointY, jumpPointX, scen.getGoalY(), scen.getGoalX());
            
            prioQueue.add(new Grid(jumpPointY, jumpPointX, distToNewJumpPoint, heuristicDistToGoal));
            distance[jumpPointY][jumpPointX] = distToNewJumpPoint;
            prevGrid[gridToInt(y, x)] = gridToInt(grid.getY(), grid.getX());
        }
    }
    
    /**
     * Grids that don't survive pruning are not needed for finding optimal path and thus don't require scanning with jump()
     * Pruning only eliminates based on movement direction, terrain related elimination happens in jump()
     * @param y coordinate of the grid being pruned
     * @param x coordinate of the grid being pruned
     * @param grid whose neighbors are being pruned
     * @param diagonal
     * @return 
     */
    private boolean gridIsPruned(int y, int x, Grid grid, boolean diagonal) {
        int parentGrid = prevGrid[gridToInt(grid.getY(), grid.getX())];
        
        // parentGrid being -1 means that parameter grid is the start of algorithm and no pruning should be made 
        if (parentGrid == -1) {
            return false;
        }
        
        int parentY = intToGridY(parentGrid);
        int parentX = intToGridX(parentGrid);
        float distThroughGrid = diagonalDistance(parentY, parentX, grid.getY(), grid.getX()) + diagonalDistance(grid.getY(), grid.getX(), y, x);
        
        // Grids are pruned by comparing distances parent(grid)->grid->neighborGrid and parent(grid)->neighborGrid
        if (diagonal) {
            if (diagonalDistance(parentY, parentX, y, x) < distThroughGrid) {
                return true;
            }            
        } else {
            if (diagonalDistance(parentY, parentX, y, x) <= distThroughGrid) {
                return true;
            }            
        }
        
        return false;
    }
    
    /**
     * Recursively scans a direction determined by a grid and its predecessor, returns a jump point (grid as integer) if it finds one
     * @param y
     * @param x
     * @param directionY
     * @param directionX
     * @param pred
     * @param diagonal
     * @return 
     */
    private int jump(int y, int x, int directionY, int directionX, Grid pred, boolean diagonal) {
        int nextY = y + directionY;
        int nextX = x + directionX;
        
        if (!isMovePossible(nextY, nextX, y, x, diagonal)) {
            return -1;
        }
        
        if (nextY == scen.getGoalY() && nextX == scen.getGoalX()) {
            return gridToInt(nextY, nextX);
        }
        
        if (anyForcedNeighbors(nextY, nextX, pred.getY(), pred.getX())) {
            return gridToInt(nextY, nextX);
        }
        
        if (diagonal) {
            if (jump(nextY, nextX, directionY, 0, pred, false) != -1 || jump(nextY, nextX, 0, directionX, pred, false) != -1) {
                return gridToInt(nextY, nextX);
            }
        }
        
        return jump(nextY, nextX, directionY, directionX, pred, diagonal);
    }
    
    // Node n' of neighbors(n) is forced if:
    // 1) n' is not a natural neighbor of n
    // 2) len p(n)->n->n' < len p(n)->n' \ n
    /**
     * Goes through grid(y,x)'s neighbors and checks if any are forced
     * @param y coordinate
     * @param x coordinate
     * @return true if any of the neighboring grids are forced
     */
    private boolean anyForcedNeighbors(int y, int x, int parentY, int parentX) {
        for (int y2 = y - 1; y2 <= y + 1; y2++) {
            for (int x2 = x - 1; x2 <= x + 1; x2++) {
                if (y2 == y && x2 == x) {
                    continue;
                }
                if (checkIfForced(y, x, y2, x2, parentY, parentX)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    // testi: riittääkö tarkastaa, että x:n ja sen naapurin vieressä on blokattu ruutu, kun lyhin reitti parent(x):stä on <= reitti p(x)->x->naapuri?
    private boolean checkIfForced(int y, int x, int neighborY, int neighborX, int parentY, int parentX) {
        float distThroughGrid = diagonalDistance(parentY, parentX, y, x) + diagonalDistance(y, x, neighborY, neighborX);
        if (distThroughGrid <= diagonalDistance(parentY, parentX, y, x)) {
            if (adjacentGridIsUnpassable(y, x, neighborY, neighborX)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Forced neighbors only occur when a grid adjacent to x and its neighbor is unpassable
     * @param y coordinate
     * @param x coordinate
     * @param neighborY coordinate of neighbor being checked
     * @param neighborX coordinate of neighbor being checked
     * @return true if there is an adjacent unpassable grid
     */
    private boolean adjacentGridIsUnpassable(int y, int x, int neighborY, int neighborX) {
        if (y > neighborY && x > neighborX) {
            if (!isValidHorOrVerMove(y - 1, x) || !isValidHorOrVerMove(y, x - 1)) {
                return true;
            }
        } else if (y < neighborY && x > neighborX) {
            if (!isValidHorOrVerMove(y, x - 1) || !isValidHorOrVerMove(y + 1, x)) {
                return true;
            }
        } else if (y < neighborY && x < neighborX) {
            if (!isValidHorOrVerMove(y, x + 1) || !isValidHorOrVerMove(y + 1, x)) {
                return true;
            }
        } else if (y > neighborY && x < neighborX) {
            if (!isValidHorOrVerMove(y - 1, x) || !isValidHorOrVerMove(y, x + 1)) {
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
