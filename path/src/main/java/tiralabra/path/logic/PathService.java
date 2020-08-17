package tiralabra.path.logic;

import java.io.File;
import tiralabra.path.data.FileGridMapReader;
import tiralabra.path.logic.exceptions.InvalidScenarioException;
import tiralabra.path.logic.exceptions.MissingUserInputException;
import tiralabra.path.logic.exceptions.NoPathFoundException;

/**
 * Main class of program logic. Ties ui, algorithm and data packages together.
 * @author Tatu
 */
public class PathService {
    
    private final FileGridMapReader mapReader = new FileGridMapReader();
    private final ScenarioValidation scenValidator = new ScenarioValidation();
    private final AlgorithmService algoService = new AlgorithmService();
    
    // User input from gui
    private File mapFile;
    private String algoId;
    private final Scenario scen = new Scenario();
    
    public void executeProgram() throws MissingUserInputException, InvalidScenarioException, NoPathFoundException {
        if (missingUserInput()) {
            throw new MissingUserInputException("Choose a map, type coordinates and select algorithm before pressing the button");
        }
        
        GridMap map = getMapFromFile();

        scenValidator.validateScenario(map, scen);
        algoService.executeAlgorithm(algoId, map, scen);
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
    
    private GridMap getMapFromFile() throws IndexOutOfBoundsException, NumberFormatException {
        return mapReader.getGridMap(mapFile);
    }
    
    private boolean missingUserInput() {
        return (mapFile == null || algoId == null || scen.getStartX() == 0 || scen.getStartY() == 0 || scen.getGoalX() == 0 || scen.getGoalY() == 0);
    }
}