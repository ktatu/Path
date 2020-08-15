package tiralabra.path.logic;

import java.io.File;
import java.util.List;
import tiralabra.path.algorithms.AStar;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.algorithms.Dijkstra;
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
    private final AlgorithmService algoService = new AlgorithmService();
    
    private File mapFile;
    private String algoId;
    private final Scenario scen = new Scenario();
    
    public String errorMsg;
    public boolean error;
    
    public void executeProgram() throws MissingUserInputException, InvalidScenarioException {
        if (missingUserInput()) {
            throw new MissingUserInputException("Choose a map, type coordinates and select algorithm before pressing the button");
        }
        
        GridMap map = getMapFromFile();
        // isoissa mapeissa DFS heittää stackoverflow
        scenValidator.validateScenario(map, scen);
        
        algoService.executeAlgorithm(algoId, map, scen);
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
    public void setAlgorithmId(String algoId) {
        this.algoId = algoId;
    }
    
    private GridMap getMapFromFile() throws IndexOutOfBoundsException, NumberFormatException {
        return mapReader.getGridMap(mapFile);
    }
    
    private boolean missingUserInput() {
        if (mapFile == null) {
            System.out.println("null mapfile");
        }
        if (algoId == null) {
            System.out.println("null algoId");
        }
        if (scen.getStartX() == 0 || scen.getStartY() == 0 || scen.getGoalX() == 0 || scen.getGoalY() == 0) {
            System.out.println("vika koordinaateissa");
        }
        return (mapFile == null || algoId == null || scen.getStartX() == 0 || scen.getStartY() == 0 || scen.getGoalX() == 0 || scen.getGoalY() == 0);
    }
    
    public void dijkstraTest() {
        if (mapFile == null) {
            return;
        }
        GridMap map = mapReader.getGridMap(mapFile);
        Scenario scena = new Scenario(436, 396, 77, 57);
        Algorithm test = new AStar(map, scena);
        
        test.runAlgorithm();
        System.out.println(test.getPathLength());
    }
}

//529.2936