package logic;

import java.util.Random;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;
import tiralabra.path.logic.ScenarioValidation;
import tiralabra.path.logic.exceptions.InvalidScenarioException;

/**
 *
 * @author Tatu
 */
public class ScenarioValidationTest {
    
    static GridMap testGridMap;
    ScenarioValidation validator = new ScenarioValidation();
    
    @BeforeClass
    public static void setUpClass() {
        char[][] testMatrix = {{'.','@','@','.',},
                               {'@','@','@','.'},
                               {'.','.','.','.'},
                               {'.','.','.','@'}};
        
        testGridMap = new GridMap(testMatrix);
    }
    @Test
    public void identicalCoordinatesAsStartAndGoalCauseThrownInvalidScenarioException() {
        try {
            validator.validateScenario(testGridMap, new Scenario(2, 2, 2, 2));
            fail("validator didn't throw an exception when start and goal coordinates were identical");
        } catch (InvalidScenarioException ex) {
        }
    }
    
    @Test
    public void outOfBoundsScenCoordinatesCauseThrownInvalidScenarioException() {
        for (int i = 0; i < 1000; i++) {
            try {
                int randomInt1 = randomInt();
                int randomInt2 = randomInt();
                int randomInt3 = randomInt();
                int randomInt4 = randomInt();
                
                testBounds(new Scenario (randomInt1, randomInt2, randomInt3, randomInt4));
                if (!(intWithinBounds(randomInt1) && intWithinBounds(randomInt2) && intWithinBounds(randomInt3) && intWithinBounds(randomInt4))) {
                    fail("validator didn't throw exception when coordinate was out of bounds");
                }  
            } catch (InvalidScenarioException ex) {
            }
        }
    }
    
    // 3 assisting methods for the test above
    private int randomInt() {
        Random r = new Random();
        return r.nextInt(1000) - 500;
    }
    
    private boolean intWithinBounds(int num) {
        if (Math.abs(num - testGridMap.getMapHeight()) <= testGridMap.getMapHeight() || Math.abs(num - testGridMap.getMapWidth()) <= testGridMap.getMapWidth()) {
            return true;
        }
        return false;
    }
    
    private void testBounds(Scenario scen) throws InvalidScenarioException {
        validator.validateScenario(testGridMap, scen);
    }
    
    @Test
    public void unpassableTerrainAsStartGridCausesThrownInvalidScenarioException() {
        try {
            validator.validateScenario(testGridMap, new Scenario(3, 3, 0, 3));
            fail("validator didn't throw exception when start grid was unpassable terrain");
        } catch (InvalidScenarioException ex) {
        }
    }
    
    @Test
    public void unpassableTerrainAsGoalGridCausesThrownInvalidScenarioException() {
        try {
            validator.validateScenario(testGridMap, new Scenario(0, 3, 3, 3));
            fail("validator didn't throw exception when goal grid was unpassable terrain");
        } catch (InvalidScenarioException ex) {
        }
    }
}
