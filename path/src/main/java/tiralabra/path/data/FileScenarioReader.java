package tiralabra.path.data;

import java.util.ArrayList;
import tiralabra.path.logic.Scenario;

/**
 *
 * @author Tatu
 */
public class FileScenarioReader {
    
    private ArrayList<String> scenarioFileAsList;
    
    FileIO fileIO;
    
    public FileScenarioReader() {
        this.fileIO = FileIO.getInstance();
    }
    
    public ArrayList<Scenario> getScenarios(String scenFile) {
        scenarioFileAsList = fileIO.collectFileToList(scenFile);
        return collectScenarios();
    }
    
    private ArrayList<Scenario> collectScenarios() {
        ArrayList<Scenario> scenarios = new ArrayList<>();
        for (int i = 1; i < scenarioFileAsList.size(); i++) {
            scenarios.add(readScenarioFromRow(scenarioFileAsList.get(i)));
        }
        return scenarios;
    }
    
    private Scenario readScenarioFromRow(String row) {
        String[] rowColSplit = row.split("	");
        
        int startX = Integer.valueOf(rowColSplit[4]);
        int startY = Integer.valueOf(rowColSplit[5]);
        int goalX = Integer.valueOf(rowColSplit[6]);
        int goalY = Integer.valueOf(rowColSplit[7]);
        
        return new Scenario(startX, startY, goalX, goalY);
    }
}
