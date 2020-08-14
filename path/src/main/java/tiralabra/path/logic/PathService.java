package tiralabra.path.logic;

import java.io.File;
import java.util.List;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.data.FileGridMapReader;
import tiralabra.path.logic.exceptions.InvalidScenarioException;
import tiralabra.path.logic.exceptions.MissingUserInputException;

/**
 * Main class of program logic. Ties ui, algorithm and data packages together.
 * @author Tatu
 */
public class PathService {
    
    private final FileGridMapReader mapReader = new FileGridMapReader();
    private final ScenarioValidation scenValidator = new ScenarioValidation();
    
    private File mapFile;
    private String algoId;
    private final Scenario scen = new Scenario();
    
    public String errorMsg;
    public boolean error;
    
    public void executeProgram(File mapFile, List<String> scenCoordinates) throws MissingUserInputException, InvalidScenarioException {
        if (missingUserInput()) {
            throw new MissingUserInputException("Choose a map, type coordinates and select algorithm before pressing the button");
        }
        
        GridMap map = getMapFromFile();
        scenValidator.validateScenario(map, scen);
        
        
    }
    
    /**
     * Sets the map file to be used with algorithms
     * @param file selected with file chooser in gui
     */
    public void setMapFile(File file) {
        this.mapFile = file;
    }
    
    /**
     * Set the coordinates for a Scenario to be run on the map
     * @param coordId which coordinate type (startX, startY, goalX, goalY)
     * @param coordinate the actual coordinate
     */
    public void setCoordinate(String coordId, int coordinate) {
        switch(coordId) {
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
    
    /**
     * set algorithm to be run on a global variable
     * @param algoId string identifier of the algorithm to be run
     */
    public void setAlgorithm(String algoId) {
        this.algoId = algoId;
    }
    
    private GridMap getMapFromFile() throws IndexOutOfBoundsException, NumberFormatException {
        return mapReader.getGridMap(mapFile);
    }
    
    private boolean missingUserInput() {
        return (mapFile == null || algoId == null || scen.getStartX() == 0 || scen.getStartY() == 0 || scen.getGoalX() == 0 || scen.getGoalY() == 0);
    }
}
