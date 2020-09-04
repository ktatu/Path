package logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.logic.AlgorithmService;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;
import tiralabra.path.logic.exceptions.NoPathFoundException;

/**
 *
 * @author Tatu
 */
public class AlgorithmServiceTest {
    
    private AlgorithmService serviceTest;
    private Scenario scen = new Scenario(2, 0, 2, 2);
    static GridMap testMap;
    
    @BeforeClass
    public static void setUpClass() {
        char[][] customMatrix = {{'@','.','.','@','.'},
                                 {'S','S','S','S','T'},
                                 {'G','@','.','.','.'}
        };
        testMap = new GridMap(customMatrix);
    }
    
    @Before
    public void setUp() {
        serviceTest = new AlgorithmService();
    }
    
    @Test
    public void runningJPSFromAlgorithmService() throws NoPathFoundException {
        serviceTest.executeAlgorithm("jps", testMap, scen, false);
    }
    
    @Test
    public void runningBFSFromAlgorithmService() throws NoPathFoundException {
        serviceTest.executeAlgorithm("bfs", testMap, scen, true);
    }
    
    @Test
    public void runningDijkstraFromAlgorithmService() throws NoPathFoundException {
        serviceTest.executeAlgorithm("dijkstra", testMap, scen, true);
    }
    
    @Test
    public void runningAStarFromAlgorithmService() throws NoPathFoundException {
        serviceTest.executeAlgorithm("aStar", testMap, scen, true);
    }
    
    @Test
    public void incorrectAlgoIdCausesNullPointerException() throws NoPathFoundException {
        try {
            serviceTest.executeAlgorithm("JPS", testMap, scen, false);
            fail("executeAlgorithm() didn't throw NullPointerException as expected");
        } catch(NullPointerException e) {
        }
    }
    
    
    @Test
    public void AlgorithmServiceThrowsNoPathFoundException() {
        // ... When no path found by algo
        Scenario scen2 = new Scenario(2, 0, 0, 4);
        try {
            serviceTest.executeAlgorithm("jps", testMap, scen2, false);
            fail("Running invalid scenario with AlgorithmService didn't throw exception as expected");
        } catch (NoPathFoundException e) {
        }
    }
    
    @Test
    public void algorithmServiceProvidesRelevantResultInfo() throws NoPathFoundException {
        Scenario scen3 = new Scenario(1, 0, 1, 1);
        serviceTest.executeAlgorithm("bfs", testMap, scen3, false);
        
        assertTrue(serviceTest.getResultInfo().contains("path length: 1"));
    }
}
