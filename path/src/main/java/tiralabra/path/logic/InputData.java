package tiralabra.path.logic;

import java.io.File;
import tiralabra.path.data.FileGridMapReader;
import tiralabra.path.logic.exceptions.InvalidScenarioException;
import tiralabra.path.logic.exceptions.MissingUserInputException;
import tiralabra.path.logic.exceptions.NoPathFoundException;

/**
 * Data from gui is collected and verified here
 * Luultavasti poistan myöhemmin, data suoraan gui-luokassa globaaleihin muuttujiin
 * @author Tatu
 */
public class InputData {
    
    private final FileGridMapReader mapReader = new FileGridMapReader();
    private final ScenarioValidation scenValidator = new ScenarioValidation();
    
    // User input from gui
    private File mapFile;
    private String algoId;
    private boolean saveImage = false;
    private final Scenario scen = new Scenario();
    
    private GridMap map;
    
    public void dataVerification() throws MissingUserInputException, InvalidScenarioException, NoPathFoundException, IndexOutOfBoundsException, NumberFormatException {
        if (missingUserInput()) {
            throw new MissingUserInputException("Choose a map, type coordinates and select algorithm before pressing the button");
        }
        
        map = getMapFromFile();
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
    
    private GridMap getMapFromFile() throws IndexOutOfBoundsException, NumberFormatException {
        return mapReader.getGridMap(mapFile);
    }
    
    private boolean missingUserInput() {
        return (mapFile == null || algoId == null || scen.getStartX() == 0 || scen.getStartY() == 0 || scen.getGoalX() == 0 || scen.getGoalY() == 0);
    }
}