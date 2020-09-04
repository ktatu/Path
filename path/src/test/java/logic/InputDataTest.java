package logic;

import java.io.File;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import tiralabra.path.logic.InputData;
import tiralabra.path.logic.exceptions.InvalidScenarioException;
import tiralabra.path.logic.exceptions.MissingUserInputException;

/**
 *
 * @author Tatu
 */
public class InputDataTest {
    
    private InputData inputTest;
    
    private File testMapFile;
    
    
    @Before
    public void setUp() {
        inputTest = new InputData();
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
        
    }
    
    @Test
    public void scenCoordinatesSetCorrectly() {
        setGlobalVariables();
        
        assertEquals(0, inputTest.getScen().getStartX());
        assertEquals(1, inputTest.getScen().getStartY());
        assertEquals(2, inputTest.getScen().getGoalX());
        assertEquals(3, inputTest.getScen().getGoalY());
    }
    
    @Test
    public void nonExistentMapFileCausesIndexOutOfBoundsException() throws MissingUserInputException, InvalidScenarioException {
        // Input variables need to be set first, otherwise test won't get to tested exception before something else gets thrown
        setGlobalVariables();
        
        try {
            inputTest.dataVerification();
            fail("dataVerification() didn't throw indexOutOfBoundsException as expected");
        } catch (IndexOutOfBoundsException e) {
        }
    }
    
    
    @Test
    public void missingCoordinateCausesMissingUserInputException() throws MissingUserInputException, InvalidScenarioException {
        setGlobalVariables();
        // Coordinates are initialized to -1 so this is the equivalent of not setting startX in gui
        inputTest.setCoordinate("startX", -1);
        
        try {
            inputTest.dataVerification();
            fail("dataVerification() didn't throw MissingUserInputException over missing a coordinate as expected");
        } catch (MissingUserInputException e) {
        }
    }
    
    @Test
    public void missingMapFileCausesMissingUserInputException() throws MissingUserInputException, InvalidScenarioException {
        setGlobalVariables();
        // Coordinates are initialized to -1 so this is the equivalent of not setting startX in gui
        inputTest.setMapFile(null);
        
        try {
            inputTest.dataVerification();
            fail("dataVerification() didn't throw MissingUserInputException over missing map file as expected");
        } catch (MissingUserInputException e) {
        }
    }
    
    @Test
    public void missingAlgoIdCausesMissingUserInputException() throws MissingUserInputException, InvalidScenarioException {
        setGlobalVariables();
        // Coordinates are initialized to -1 so this is the equivalent of not setting startX in gui
        inputTest.setAlgorithmId(null);
        
        try {
            inputTest.dataVerification();
            fail("dataVerification() didn't throw MissingUserInputException over missing algorithm id as expected");
        } catch (MissingUserInputException e) {
        }
    }
    
    private void setGlobalVariables() {
        inputTest.setAlgorithmId("JPS");
        inputTest.setMapFile(new File("testMap.map"));
        
        inputTest.setCoordinate("startX", 0);
        inputTest.setCoordinate("startY", 1);
        inputTest.setCoordinate("goalX", 2);
        inputTest.setCoordinate("goalY", 3);
    }
    
}
