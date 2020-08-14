package algorithms;

import java.util.ArrayList;
import java.util.Random;
import org.junit.BeforeClass;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;
import tiralabra.path.logic.ScenarioValidation;
import tiralabra.path.logic.exceptions.InvalidScenarioException;

/**
 * Creates testMaps and scenarios for algorithm test classes
 * @author Tatu
 */
public class AlgorithmSetup {
    
    static Random r = new Random();
    static ScenarioValidation validator = new ScenarioValidation();
    
    final static float sqrtTwo = (float)Math.sqrt(2);
    
    static GridMap testMap;
    static GridMap distanceTestMap;
    static GridMap customMap;
    
    static ArrayList<Scenario> scenarios;
    static ArrayList<Scenario> customScenarios;
    
    @BeforeClass
    public static void setupClass() {
        testMap = createTestMap(false);
        distanceTestMap = createTestMap(true);
        scenarios = createValidScenarios(testMap);
        char[][] customMatrix = {{'@','.','.','@','.'},
                                 {'S','S','S','S','T'},
                                 {'G','@','.','.','.'}
        };
        customMap = new GridMap(customMatrix);
        customScenarios = new ArrayList<>();
        
        customScenarios.add(new Scenario(2,0,1,1));
        customScenarios.add(new Scenario(2,0,2,2));
        customScenarios.add(new Scenario(2,2,0,2));
        // scenarioValidation should never allow this scenario to go through but even if it did the algo should run fine
        customScenarios.add(new Scenario(1,3,0,4));
        customScenarios.add(new Scenario(2,2,1,3));
    }
    
    // if distanceTest is true then method returns a map consisting of only passable terrain
    public static GridMap createTestMap(boolean distanceTest) {
        char[][] gridMap = new char[20][20];
        
        for (int y = 0; y < gridMap.length; y++) {
            for (int x = 0; x < gridMap[0].length; x++) {
                gridMap[y][x] = createTerrain(distanceTest);
            }
        }
        return new GridMap(gridMap);
    }
    
    private static char createTerrain(boolean distanceTest) {
        if (distanceTest) {
            return '.';
        }
        int terrainRandomizer = r.nextInt(10) + 1;
        if (terrainRandomizer == 10) {
            return '@';
        }
        return '.';
    }
    
    private static ArrayList<Scenario> createValidScenarios(GridMap testMap) {
        ArrayList<Scenario> newScenarios = new ArrayList<>();
        
        while (newScenarios.size() < 5) {
            int hLimit = testMap.getMapHeight();
            int wLimit = testMap.getMapWidth();
            
            Scenario scen = new Scenario(r.nextInt(hLimit), r.nextInt(wLimit), r.nextInt(hLimit), r.nextInt(wLimit));
            try {
                validator.validateScenario(testMap, scen);
                newScenarios.add(scen);
            } catch (InvalidScenarioException ex) {
            }
        }
        return newScenarios;
    }
}
