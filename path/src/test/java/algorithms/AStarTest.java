package algorithms;

import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.algorithms.AStar;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.logic.Scenario;

/**
 *
 * @author Tatu
 */
public class AStarTest extends AlgorithmSetup {
    
    TestUtils util = new TestUtils();
    
    public AStarTest() {
        super();
    }

    @Test
    public void aStarFindsAPath () {
        for (Scenario scen : scenarios) {
            Algorithm aStar = new AStar(testMap, scen);
            aStar.runAlgorithm();
            if (!aStar.goalVisited()) {
                fail("AStar didn't find the path");
            }
        }
    }
    
    @Test
    public void aStarFindsTheShortestPathOnEmptyMap() {
        for (Scenario scen : scenarios) {
            Algorithm aStar = new AStar(distanceTestMap, scen);
            aStar.runAlgorithm();
            if (!util.correctDistance(aStar.getPathLength(), scen)) {
                fail("AStar didn't find the shortest path on empty map");
            }
        }
    }
    
    @Test
    public void aStarFindsTheShortestPathOnCustomMap() {
        for (int i = 0; i < customScenarios.size(); i++) {
            Algorithm aStar = new AStar(customMap, customScenarios.get(i));
            aStar.runAlgorithm();
            
            if (Math.abs(aStar.getPathLength() - util.expectedResults[i]) > 0.001) {
                
                System.out.println("alku " + customScenarios.get(i).getStartX() + ", " + customScenarios.get(i).getStartY());
                System.out.println("loppu " + customScenarios.get(i).getGoalX() + ", " + customScenarios.get(i).getGoalY());
                
                System.out.println("löydetyn reitin pituus" + aStar.getPathLength());
                System.out.println("expected result " + util.expectedResults[i]);
                fail("AStar didn't find the shortest path on custom map");
            }
        }
    }
}
