package tiralabra.path.logic;

import java.io.File;
import tiralabra.path.io.FileGridMapReader;
import tiralabra.path.logic.exceptions.InvalidScenarioException;
import tiralabra.path.logic.exceptions.MissingUserInputException;

/**
 * Data from gui is collected and verified here
 * @author Tatu
 */
public class InputData {
    
    private final FileGridMapReader mapReader = new FileGridMapReader();
    private final ScenarioValidation scenValidator = new ScenarioValidation();
    
    // User input from gui
    private File mapFile;
    private String algoId;
    private boolean saveImage;
    private final Scenario scen;
    
    private GridMap map;
    
    /**
     * Scenario gets initialized to unusable values for checking if user input is missing
     */
    public InputData() {
        saveImage = false;
        scen = new Scenario();
    }
    
    /**
     * Verify all data and throw a relevant exception back to gui if data is flawed
     * @throws MissingUserInputException
     * @throws InvalidScenarioException
     * @throws IndexOutOfBoundsException
     * @throws NumberFormatException 
     */
    public void dataVerification() throws MissingUserInputException, InvalidScenarioException, IndexOutOfBoundsException, NumberFormatException {
        if (missingUserInput()) {
            throw new MissingUserInputException("Choose a map, type coordinates and select algorithm before pressing the button");
        }
        
        // Can throw IndexOutOfBoundsException or NumberFormatException to gui when map file was not properly formatted
        map = getMapFromFile();
        
        // Throws MissingUserException back to gui if validation fails
        scenValidator.validateScenario(map, scen);
    }
    
    public void setMapFile(File file) {
        this.mapFile = file;
    }
    
    /**
     * Set the coordinates for a Scenario to be run on the map
     * @param coordId which coordinate type (startX, startY, goalX, goalY)
     * @param coordinate the actual coordinate
     */
    public void setCoordinate(String coordId, int coordinate) {
        switch (coordId) {
            case "startX":
                scen.setStartX(coordinate);
                break;
            case "startY":
                scen.setStartY(coordinate);
                break;
            case "goalX":
                scen.setGoalX(coordinate);
                break;
            case "goalY":
                scen.setGoalY(coordinate);
                break;
            default:
                // should never happen
        }
    }
    
    public void setAlgorithmId(String algoId) {
        this.algoId = algoId;
    }
    
    public void setSaveImage(boolean value) {
        saveImage = value;
    }

    public String getAlgoId() {
        return algoId;
    }

    public Scenario getScen() {
        return scen;
    }
    
    public boolean getSaveImage() {
        return saveImage;
    }

    public GridMap getMap() {
        return map;
    }
    
    /**
     * Get a GridMap object from a file
     * @return
     * @throws IndexOutOfBoundsException
     * @throws NumberFormatException 
     */
    private GridMap getMapFromFile() throws IndexOutOfBoundsException, NumberFormatException {
        return mapReader.getGridMap(mapFile);
    }
    
    /**
     * Check if any input required for running an algorithm is missing
     * @return 
     */
    private boolean missingUserInput() {
        return (mapFile == null || algoId == null || scen.getStartX() == -1 || scen.getStartY() == -1 || scen.getGoalX() == -1 || scen.getGoalY() == -1);
    }
}