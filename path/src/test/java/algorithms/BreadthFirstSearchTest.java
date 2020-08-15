package algorithms;

import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.algorithms.BreadthFirstSearch;
import tiralabra.path.logic.Scenario;

/**
 *
 * @author Tatu
 */
public class BreadthFirstSearchTest extends AlgorithmSetup {
    
    
    public BreadthFirstSearchTest() {
        super();
    }
    
    @Test
    public void bfsFindsAPath() {
        for (Scenario scen : scenarios) {
            Algorithm bfs = new BreadthFirstSearch(testMap, scen);
            bfs.runAlgorithm();
            if (!bfs.goalVisited()) {
                fail("bfs didnt find a path to goal grid");
            }
        }
    }
    
    // shortest path for BFS means manhattan distance from start to goal
    @Test
    public void bfsFindsShortestPathOnEmptyMap() {
        System.out.println("leveys "+distanceTestMap.getMapHeight());
        System.out.println("pituus "+distanceTestMap.getMapWidth());
        for (Scenario scen : scenarios) {
            System.out.println(scen.getGoalX());
            System.out.println(scen.getGoalY());
            System.out.println(scen.getStartX());
            System.out.println(scen.getStartY());
            Algorithm bfs = new BreadthFirstSearch(distanceTestMap, scen);
            bfs.runAlgorithm();
            if (Math.abs(bfs.getPathLength() - manhattanDistance(scen)) > 0.1) {
                fail("bfs didn't find the shortest path");
            }
        }
    }
    
    @Test
    public void bfsFindsShortestPathOnCustomMap() {
        float[] expectedResults = {2, 4, 2, 2};
        
        for (int i = 0; i < customScenarios.size(); i++) {
            Scenario scen = customScenarios.get(i);
            Algorithm bfs = new BreadthFirstSearch(customMap, scen);
            bfs.runAlgorithm();
            
            if (bfs.getPathLength() != expectedResults[i]) {
                fail("bfs didn't find the shortest path in custom map");
            }
        } 
    }
    
    public float manhattanDistance(Scenario scen) {
        return (float) Math.abs(scen.getGoalY() - scen.getStartY()) + Math.abs(scen.getGoalX() - scen.getStartX());
    }
}
