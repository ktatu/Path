package tiralabra.path.algorithms;

import java.util.ArrayDeque;
import tiralabra.path.logic.Scenario;

/**
 *
 * @author Tatu
 */
public class BreadthFirstSearch extends Algorithm {
    
    private ArrayDeque<Integer> fifo;
    
    public BreadthFirstSearch(int[][] gridMap, Scenario scen) {
        super(gridMap, scen);
        this.fifo = new ArrayDeque<>();
    }
    
    @Override
    public void initializeAlgorithm() {
        int startY = scen.getStartY();
        int startX = scen.getStartX();
        int gridAsInt = gridToInt(startY, startX);
        
        fifo.add(gridAsInt);
        distance[startY][startX] = 0;
        prevGrid[gridAsInt] = gridAsInt;
    }

    @Override
    public void runAlgorithm() {
        
        startTime = System.nanoTime();
        while (!fifo.isEmpty()) {
            if (goalVisited()) {
                break;
            }
            int gridAsInt = fifo.pollFirst();
            //System.out.println(gridAsInt);
            
            int gridY = intToGridY(gridAsInt);
            int gridX = intToGridX(gridAsInt);
            
            checkAdjacentGrids(gridY, gridX);
        }
        endTime = System.nanoTime();
        
        if (gridToInt(scen.getGoalY(), scen.getGoalX()) == -1) {
            System.out.println("algorithm didn't visit goal grid");
        } else {
            System.out.println("goal grid was visited");
        }
    }
    
    // siirrä yläluokkaan jos muut algoritmit tarvitsevat vastaavaa
    private boolean goalVisited() {
        int goalAsInt = gridToInt(scen.getGoalY(), scen.getGoalX());
        return prevGrid[goalAsInt] != -1;
    }
    
    
    private void checkAdjacentGrids(int gridY, int gridX) {
        checkGrid(gridY - 1, gridX, gridY, gridX);
        checkGrid(gridY + 1, gridX, gridY, gridX);
        checkGrid(gridY, gridX - 1, gridY, gridX);
        checkGrid(gridY, gridX + 1, gridY, gridX);
    }
    
    private void checkGrid(int gridY, int gridX, int prevGridY, int prevGridX) {
        if (gridY < 0 || gridY >= gridMap.length || gridX < 0 || gridX >= gridMap[0].length || gridMap[gridY][gridX] == 0) return;
        
        int gridAsInt = gridToInt(gridY, gridX);
        
        if (prevGrid[gridAsInt] != -1) return;
        
        fifo.add(gridToInt(gridY, gridX));
        prevGrid[gridAsInt] = gridToInt(prevGridY, prevGridX);
        distance[gridY][gridX] = distance[prevGridY][prevGridX] + 1;
    }
}
