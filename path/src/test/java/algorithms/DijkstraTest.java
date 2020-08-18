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
            if (!util.correctDistance(util.resultDistance(dij, scen), scen)) {
                fail("dijkstra didn't find the shortest path");
            }
        }
    }
    
    @Test
    public void dijkstraFindsShortestPathsOnCustomMap() {
        for (int i = 0; i < customScenarios.size(); i++) {
            Algorithm dij = new Dijkstra(customMap, customScenarios.get(i));
            dij.runAlgorithm();
            
            if (Math.abs(util.resultDistance(dij, customScenarios.get(i)) - util.expectedResults[i]) > 0.001) {
                fail("dijkstra didn't find the shortest path in custom map");
            }
        }
    }
}
