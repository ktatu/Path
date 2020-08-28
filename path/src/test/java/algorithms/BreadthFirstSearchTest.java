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
    
    TestUtils util = new TestUtils();
    
    public BreadthFirstSearchTest() {
        super();
    }
    
    @Test
    public void bfsFindsAPath() {
        for (Scenario scen : scenarios) {
            Algorithm bfs = new BreadthFirstSearch();
            bfs.runAlgorithm(testMap, scen);
            if (!bfs.goalVisited()) {
                System.out.println("scen start: " + scen.getStartY() + " " + scen.getStartX());
                System.out.println("scen goal: " + scen.getGoalY() + " " + scen.getGoalX());
                fail("bfs didnt find a path to goal grid");
            }
        }
    }
    
    // shortest path for BFS means manhattan distance from start to goal
    @Test
    public void bfsFindsShortestPathOnEmptyMap() {
        for (Scenario scen : scenarios) {
            Algorithm bfs = new BreadthFirstSearch();
            bfs.runAlgorithm(distanceTestMap, scen);
            if (Math.abs(util.resultDistance(bfs, scen) - manhattanDistance(scen)) > 0.001) {
                fail("bfs didn't find the shortest path");
            }
        }
    }
    
    @Test
    public void bfsFindsShortestPathOnCustomMap() {
        float[] expectedResults = {2, 4, 2, 2};
        
        for (int i = 0; i < customScenarios.size(); i++) {
            Scenario scen = customScenarios.get(i);
            Algorithm bfs = new BreadthFirstSearch();
            bfs.runAlgorithm(customMap, scen);
            
            if (util.resultDistance(bfs, scen) != expectedResults[i]) {
                fail("bfs didn't find the shortest path in custom map");
            }
        } 
    }
    
    public float manhattanDistance(Scenario scen) {
        return (float) Math.abs(scen.getGoalY() - scen.getStartY()) + Math.abs(scen.getGoalX() - scen.getStartX());
    }
}
