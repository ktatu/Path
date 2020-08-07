package algorithms;

import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.algorithms.Dijkstra;
import tiralabra.path.logic.Scenario;

/**
 *
 * @author Tatu
 */
public class DijkstraTest extends AlgorithmSetup {
    
    TestUtils util = new TestUtils();
    
    public DijkstraTest() {
        super();
    }

    @Test
    public void dijkstraFindsAPath(){
        for (Scenario scen: scenarios) {
            Algorithm dij = new Dijkstra(testMap, scen);
            dij.runAlgorithm();
            if (!dij.goalVisited()) {
                fail("dijkstra didn't find a path to goal grid");
            }
        }
    }
    
    @Test
    public void dijkstraFindsTheShortestPathsOnEmptyMap() {
        for (Scenario scen: scenarios) {
            Algorithm dij = new Dijkstra(distanceTestMap, scen);
            dij.runAlgorithm();
            if (!util.correctDistance(dij.getPathLength(), scen)) {
                fail("dijkstra didn't find the shortest path");
            }
        }
    }
    
    @Test
    public void dijkstraFindsShortestPathsOnCustomMap() {
        float[] expectedResults = {2, 4, 2, Integer.MAX_VALUE, sqrtTwo};
        
        for (int i = 0; i < customScenarios.size(); i++) {
            Algorithm dij = new Dijkstra(customMap, customScenarios.get(i));
            dij.runAlgorithm();
            
            if (Math.abs(dij.getPathLength() - expectedResults[i]) > 0.001) {
                fail("dijkstra didn't find the shortest path in custom map");
            }
        }
    }
}
