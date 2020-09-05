package tiralabra.path.algorithms;

import tiralabra.path.datastructures.List;
import tiralabra.path.datastructures.PrioQueue;
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
    public boolean closed[][];

    public void initializeJPS(GridMap map, Scenario scen) {
        initializeAlgorithm(map, scen);
        
        this.closed = new boolean[gridMap.getMapHeight()][gridMap.getMapWidth()];
        this.prioQueue = new PrioQueue();
        
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
    public void runAlgorithm(GridMap map, Scenario scen) {
        initializeJPS(map, scen);
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
    
    /**
     * Go through (x,y)'s neighbors, jump to each neighbor that does not get pruned
     * @param y coord
     * @param x coord
     */
    private void scanNeighbors(int y, int x) {
        boolean isStartGrid = (y == scen.getStartY() && x == scen.getStartX());
                
        List neighbors = prunedNeighbors(y, x, isStartGrid);
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

    /**
     * Add grid found by jump() to priority queue, update relevant data structures
     * @param jumpGrid jump point grid as an integer
     * @param parentY parent y coordinate of the new jump point
     * @param parentX parent x coordinate of the new jump point
     */
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

    /**
     * Recursively look for new jump points
     * @param y current grid's y
     * @param x current grid's x
     * @param dirY how the algorithm is moving vertically, -1 or 1
     * @param dirX how the algorithm is moving horizontally, -1  or 1
     * @return -1 if no jump point was found or the jump point as an integer
     */
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
        // Same principle in checking for forced neighbors as in pruning
        if (diagonal) {
            if ((!isPassable(y - dirY, x) && isPassable(y - dirY, x + dirX)) || (!isPassable(y, x - dirX) && isPassable(y + dirY, x - dirX))) {
                return gridToInt(y, x);
            }
        } else {
            if (dirY == 0) {
                if ((!isPassable(y - 1, x) && isPassable(y - 1, x + dirX)) || (!isPassable(y + 1, x) && isPassable(y + 1, x + dirX))) {
                    return gridToInt(y, x);
                }
            } else {
                if ((!isPassable(y, x - 1) && isPassable(y + dirY, x - 1)) || (!isPassable(y, x + 1) && isPassable(y + dirY, x + 1))) {
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
    
    /**
     * Measuring diagonal distance between two grids
     * @param startY
     * @param startX
     * @param targetY
     * @param targetX
     * @return the distance as float
     */
    private float diagonalDistance(int startY, int startX, int targetY, int targetX) {
        int distanceY = MathUtil.abs(targetY - startY);
        int distanceX = MathUtil.abs(targetX - startX);
        
        int diagonalMoves = MathUtil.min(distanceY, distanceX);
        int horAndVerMoves = MathUtil.abs(distanceY - distanceX);
        
        return diagonalMoves * sqrtTwo + horAndVerMoves;
    }

    /**
     * Prune neighbors of (x,y) for jump() by considering movement direction and forced grids
     * @param y coordinate
     * @param x coordinate
     * @param isStartGrid if (x,y) == (scen.getStartX, scen.getStartY) then no neighbors get pruned
     * @return list of neighbors (as integers) that survived pruning
     */
    private List prunedNeighbors(int y, int x, boolean isStartGrid) {
        if (isStartGrid) {
            return neighborList(y, x);
        }
        
        List neighbors = new List(8);
        
        int parent = prevGrid[gridToInt(y, x)];
        int pY = intToGridY(parent);
        int pX = intToGridX(parent);
        
        int dirY = getDirectionY(y, pY);
        int dirX = getDirectionX(x, pX);
        
        if (dirY != 0 && dirX != 0) {
            pruneDiagonal(y, x, dirY, dirX, neighbors);
        } else if (dirY == 0) {
            pruneHorizontal(y, x, dirX, neighbors);
        } else {
            pruneVertical(y, x, dirY, neighbors);
        }
        
        return neighbors;
    }
    
    /**
     * JPS requires a separate implementation of constructing path because of the gaps between jump points
     */
    @Override
    protected void constructPath() {
        int goalGridAsInt = gridToInt(scen.getGoalY(), scen.getGoalX());
        
        while (prevGrid[goalGridAsInt] != -1) {
            path.add(goalGridAsInt);
            pathBetweenJumpPoints(goalGridAsInt);
            goalGridAsInt = prevGrid[goalGridAsInt];
        }
    }
    
    /**
     * Add the gaps between two jump points for a proper path
     * @param grid 
     */
    private void pathBetweenJumpPoints(int grid) {
        if (prevGrid[grid] == -1 || grid == -1) {
            return;
        }
        
        int y = intToGridY(grid);
        int x = intToGridX(grid);
        
        int pY = intToGridY(prevGrid[grid]);
        int pX = intToGridX(prevGrid[grid]);
        
        int dirY = getDirectionY(pY, y);
        int dirX = getDirectionX(pX, x);
        
        while ((y != pY) || (x != pX)) {
            y += dirY;
            x += dirX;
            path.add(gridToInt(y, x));
        }
    }
    
    /**
     * Which neighbors to add to the "not-pruned" list when movement is diagonal
     * @param y coordinate
     * @param x coordinate
     * @param dirY vertical direction of movement
     * @param dirX horizontal direction of movement
     * @param nborList list of neighbors that do not get pruned
     */
    private void pruneDiagonal(int y, int x, int dirY, int dirX, List nborList) {
        // jump() checks if these are passable grids anyway so no point in verifying them
        // Same principle with the no-check adds in pruneVertical and pruneHorizontal
        nborList.add(gridToInt(y + dirY, x + dirX));
        nborList.add(gridToInt(y + dirY, x));
        nborList.add(gridToInt(y, x + dirX));
        
        if (!isPassable(y - dirY, x) && isPassable(y - dirY, x + dirX)) {
            nborList.add(gridToInt(y - dirY, x + dirX));
        }
        if (!isPassable(y, x - dirX) && isPassable(y + dirY, x - dirX)) {
            nborList.add(gridToInt(y + dirY, x - dirX));
        }
    }
    
    /**
     * Which neighbors to add to the "not-pruned" list when movement is horizontal
     * @param y coordinate
     * @param x coordinate
     * @param dirX horizontal direction of movement
     * @param nborList list of neighbors that do not get pruned
     */
    private void pruneHorizontal(int y, int x, int dirX, List nborList) {
        nborList.add(gridToInt(y, x + dirX));
        
        if (!isPassable(y - 1, x) && isPassable(y - 1, x + dirX)) {
            nborList.add(gridToInt(y - 1, x + dirX));
        }
        if (!isPassable(y + 1, x) && isPassable(y + 1, x + dirX)) {
            nborList.add(gridToInt(y + 1, x + dirX));
        }
    }
    
    /**
     * Which neighbors to add to the "not-pruned" list when movement is vertical
     * @param y coordinate
     * @param x coordinate
     * @param dirX vertical direction of movement
     * @param nborList list of neighbors that do not get pruned
     */
    private void pruneVertical(int y, int x, int dirY, List nborList) {
        nborList.add(gridToInt(y + dirY, x));
        
        if (!isPassable(y, x - 1) && isPassable(y + dirY, x - 1)) {
            nborList.add(gridToInt(y + dirY, x - 1));
        }
        if (!isPassable(y, x + 1) && isPassable(y + dirY, x + 1)) {
            nborList.add(gridToInt(y + dirY, x + 1));
        }
    }
}
