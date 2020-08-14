/*
package algorithms;

import static algorithms.AlgorithmSetup.customMap;
import static algorithms.AlgorithmSetup.customScenarios;
import static algorithms.AlgorithmSetup.distanceTestMap;
import static algorithms.AlgorithmSetup.scenarios;
import static algorithms.AlgorithmSetup.testMap;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.algorithms.AStar;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.algorithms.JumpPointSearch;
import tiralabra.path.logic.Scenario;

public class JumpPointSearchTest extends AlgorithmSetup {
    
    TestUtils util = new TestUtils();
    
    public JumpPointSearchTest() {
        super();
    }

    @Test
    public void jpsFindsAPath () {
        for (Scenario scen : scenarios) {
            Algorithm jps = new JumpPointSearch(testMap, scen);
            jps.runAlgorithm();
            if (!jps.goalVisited()) {
                fail("JPS didn't find the path");
            }
        }
    }
    
    @Test
    public void jpsFindsTheShortestPathOnEmptyMap() {
        for (Scenario scen : scenarios) {
            Algorithm jps = new AStar(distanceTestMap, scen);
            jps.runAlgorithm();
            if (!util.correctDistance(jps.getPathLength(), scen)) {
                fail("JPS didn't find the shortest path on empty map");
            }
        }
    }
    
    @Test
    public void jpsFindsTheShortestPathOnCustomMap() {
        for (int i = 0; i < customScenarios.size(); i++) {
            Algorithm jps = new AStar(customMap, customScenarios.get(i));
            jps.runAlgorithm();
            
            if (Math.abs(jps.getPathLength() - util.expectedResults[i]) > 0.001) {
                fail("JPS didn't find the shortest path on custom map");
            }
        }
    }
}
*/