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
            Algorithm aStar = new AStar();
            aStar.runAlgorithm(testMap, scen);
            if (!aStar.goalVisited()) {
                fail("AStar didn't find the path");
            }
        }
    }
    
    @Test
    public void aStarFindsTheShortestPathOnEmptyMap() {
        for (Scenario scen : scenarios) {
            Algorithm aStar = new AStar();
            aStar.runAlgorithm(distanceTestMap, scen);
            if (!util.correctDistance(util.resultDistance(aStar, scen), scen)) {
                fail("AStar didn't find the shortest path on empty map");
            }
        }
    }
    
    @Test
    public void aStarFindsTheShortestPathOnCustomMap() {
        for (int i = 0; i < customScenarios.size(); i++) {
            Algorithm aStar = new AStar();
            aStar.runAlgorithm(customMap, customScenarios.get(i));
            
            if (Math.abs(util.resultDistance(aStar, customScenarios.get(i)) - util.expectedResults[i]) > 0.001) {
                fail("AStar didn't find the shortest path on custom map");
            }
        }
    }
}
