package tiralabra.path.algorithms;

import tiralabra.path.datastructures.GridList;
import tiralabra.path.logic.Grid;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * Jump Point Search based on Daniel Harabor's and Alban Grastien's paper "Online Graph Pruning for Pathfinding on Grid Maps (january 2011)"
 * @author Tatu
 */
public class JumpPointSearch extends Dijkstra {
    
    // closed[][] performs the same duty as visited[][] in other algorithms; keeping track of which grids have been dealt with
    // JPS uses visited[][] to mark grids scanned by jump(). More convenient since AlgorithmImageWriter can access visited[][] but not closed[][]
    private final boolean closed[][];
    
    public JumpPointSearch(GridMap gridMap, Scenario scen) {
        super(gridMap, scen);
        this.closed = new boolean[gridMap.getMapHeight()][gridMap.getMapWidth()];
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
        prioQueue.add(new Grid(startY, startX, 0, diagonalDistance(startY, startX, scen.getGoalY(), scen.getGoalX())));
    }
    
    @Override
    public void runAlgorithm() {
        initializeAlgorithm();
        while (!prioQueue.isEmpty()) {
            if (closed[scen.getGoalY()][scen.getGoalX()]) {
                break;
            }
            
            Grid current = prioQueue.poll(); 
            int y = current.getY();
            int x = current.getX();

            if (closed[y][x]) {
                continue;
            }
            closed[y][x] = true;
            
            scanNeighbors(y, x);
        }
        if (goalVisited()) {
            constructPath();
        }
    }
    
    private void scanNeighbors(int y, int x) {
        boolean isStartGrid = (y == scen.getStartY() && x == scen.getStartX());
                
        GridList neighbors = prunedNeighbors(y, x, isStartGrid);
        while (neighbors.canIterate()) {
            int nbor = neighbors.getNext();
            
            int nY = intToGridY(nbor);
            int nX = intToGridX(nbor);
            
            int jumpPoint = jump(nY, nX, nY - y, nX - x);
            if (jumpPoint != -1) {
                addNewJumpPoint(jumpPoint, y, x);
            }
        }
    }

    private void addNewJumpPoint(int jumpGrid, int parentY, int parentX) {
        int jumpY = intToGridY(jumpGrid);
        int jumpX = intToGridX(jumpGrid);
        
        float newDist = distance[parentY][parentX] + diagonalDistance(parentY, parentX, jumpY, jumpX);
        float heuristicEstimate = diagonalDistance(jumpY, jumpX, scen.getGoalY(), scen.getGoalX());
        
        if (jumpY == scen.getGoalY() && jumpX == scen.getGoalX()) {
            closed[jumpY][jumpX] = true;
        }
        
        if (newDist < distance[jumpY][jumpX]) {
            distance[jumpY][jumpX] = newDist;
            prevGrid[gridToInt(jumpY, jumpX)] = gridToInt(parentY, parentX);
            prioQueue.add(new Grid(jumpY, jumpX, newDist, heuristicEstimate));
        }
    } 

    private int jump(int y, int x, int dirY, int dirX) {
        int nextY = y + dirY;
        int nextX = x + dirX;
        
        boolean diagonal = (dirY != 0 && dirX != 0);
        
        if (!isMovePossible(y, x, y - dirY, x - dirX, diagonal)) {
            return -1;
        }

        visited[y][x] = true; 
        
        if (y == scen.getGoalY() && x == scen.getGoalX()) {
            return gridToInt(y, x);
        }
        
        if (diagonal) {
            if (forcedDiagonal(y, x, dirY, dirX)) {
                return gridToInt(y, x);
            }
        } else {
            if (dirY == 0) {
                if (checkForcedHorizontal(y, x, dirX)) {
                    return gridToInt(y, x);
                }
            } else {
                if (checkForcedVertical(y, x, dirY)) {
                    return gridToInt(y, x);
                }
            }
        }
        if (diagonal) {
            if (jump(nextY, x, dirY, 0) != -1 || jump(y, nextX, 0, dirX) != -1) {
                return gridToInt(y, x);
            }
        }
        
        return jump(nextY, nextX, dirY, dirX);
    }
    
    private boolean checkForcedVertical(int y, int x, int directionY) {
        // Scanning north
        if (directionY == -1) {
            if ((!isPassable(y, x - 1) && isPassable(y - 1, x - 1)) || (!isPassable(y, x + 1) && isPassable(y - 1, x + 1))) {
                return true;
            }
            return false;
        }
        // South
        if ((!isPassable(y, x - 1) && isPassable(y + 1, x - 1)) || (!isPassable(y, x + 1) && isPassable(y + 1, x + 1))) {
            return true;
        }
        return false;
    }
    
    private boolean checkForcedHorizontal(int y, int x, int directionX) {
        // West
        if (directionX == -1) {
            if ((!isPassable(y - 1, x) && isPassable(y - 1, x - 1)) || (!isPassable(y + 1, x) && isPassable(y + 1, x -1))) {
                return true;
            }
            return false;
        }
        //East
        if ((!isPassable(y - 1, x) && isPassable(y - 1, x + 1)) || (!isPassable(y + 1, x) && isPassable(y + 1, x + 1))) {
            return true;
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
    
    private GridList prunedNeighbors(int y, int x, boolean isStartGrid) {
        // No pruning when grid (x,y) is the starting grid
        if (isStartGrid) {
            return neighborList(y, x);
        }
        
        GridList neighbors = new GridList(8);
        
        int parent = prevGrid[gridToInt(y, x)];
        int pY = intToGridY(parent);
        int pX = intToGridX(parent);
        
        int dirY = getDirectionY(y, pY);
        int dirX = getDirectionX(x, pX);

        if (dirY != 0 && dirX != 0) {
            if (forcedDiagonal(y, x, dirY, dirX)) {
                int prevY = y - dirY;
                int prevX = x - dirX;
                neighbors.add(gridToInt(prevY + 2, prevX));
                neighbors.add(gridToInt(prevY, prevX + 2));
            }
            neighbors.add(gridToInt(y + dirY, x + dirX));
            neighbors.add(gridToInt(y + dirY, x));
            neighbors.add(gridToInt(y, x + dirX));
        } else if (dirY == 0) {
            if (checkForcedHorizontal(y, x, dirX)) {
                neighbors.add(gridToInt(y - 1, x + dirX));
                neighbors.add(gridToInt(y + 1, x + dirX));
            }
            neighbors.add(gridToInt(y, x + dirX));
        } else {
            if (checkForcedVertical(y, x, dirY)) {
                neighbors.add(gridToInt(y + dirY, x + 1));
                neighbors.add(gridToInt(y + dirY, x - 1));
            }
            neighbors.add(gridToInt(y + dirY, x));

        }
        return neighbors;
    }
    
    // JPS requires a separate implementation of constructing path because of the gaps between jump points
    @Override
    protected void constructPath() {
        int goalGridAsInt = gridToInt(scen.getGoalY(), scen.getGoalX());
        
        while (prevGrid[goalGridAsInt] != -1) {
            path.add(goalGridAsInt);
            pathBetweenJumpPoints(goalGridAsInt);
            goalGridAsInt = prevGrid[goalGridAsInt];
        }
    }
    
    private void pathBetweenJumpPoints(int grid) {
        if (prevGrid[grid] == -1) {
            return;
        }
        int[] coord = intToCoordinates(grid);
        int[] pCoord = intToCoordinates(prevGrid[grid]);
        
        int dirY = getDirectionY(pCoord[1], coord[1]);
        int dirX = getDirectionX(pCoord[0], coord[0]);
        
        while ((coord[0] != pCoord[0]) || (coord[1] != pCoord[1])) {
            coord[0] += dirX;
            coord[1] += dirY;
            path.add(gridToInt(coord[1], coord[0]));
        }
    }
    
    private boolean forcedDiagonal(int y, int x, int directionY, int directionX) {
        // Scanning southwest
        if (directionY == 1 && directionX == -1) {
            if (!isPassable(y - 1, x) && isPassable(y - 1, x- 1)) {
                return true;
            } else if (!isPassable(y, x +1) && isPassable(y + 1, x)) {
                return true;
            }
        }
        // Northwest
        else if (directionY == -1 && directionX == -1) {
            if (!isPassable(y + 1, x) && isPassable(y + 1, x - 1)) {
                return true;
            } else if (!isPassable(y, x + 1) && isPassable(y - 1, x + 1)) {
                return true;
            }
        }
        // Northeast
        else if (directionY == -1 && directionX == 1) {
            if (!isPassable(y, x - 1) && isPassable(y - 1, x - 1)) {
                return true;
            } else if (!isPassable(y + 1, x) && isPassable(y + 1, x + 1)) {
                return true;
            }
        }
        // Southeast
        else {
            if (!isPassable(y, x + 1) && isPassable(y - 1, x + 1)) {
                return true;
            } else if (!isPassable(y, x - 1) && isPassable(y + 1, x - 1)) {
                return true;
            }
        }
        return false;
    }
}
