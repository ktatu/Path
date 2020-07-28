package tiralabra.path.data;

import java.io.IOException;
import java.util.ArrayList;
import tiralabra.path.logic.Scenario;

/**
 *
 * @author Tatu
 */
public class FileScenarioReader {
    
    FileIO fileIO;
    
    public FileScenarioReader() {
        this.fileIO = FileIO.getInstance();
    }
    
    public ArrayList<Scenario> collectScenarios(String scenFile) {
        ArrayList<String> scenFileAsList = fileIO.collectFileToList(scenFile);
        ArrayList<Scenario> scenarios = new ArrayList<>();
        
        for (int i = 1; i < scenFileAsList.size(); i++) {
            try {
                scenarios.add(readScenarioFromRow(scenFileAsList.get(i), i));
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        return scenarios;
    }
    
    private Scenario readScenarioFromRow(String row, int rowNumber) throws IOException {
        String[] rowColSplit = row.split("	");
        
        if (rowColSplit.length != 9) {
            throw new IOException("readScenarioFromRow(): row " + rowNumber + " is incorrectly formatted");
        }
        
        int startX = Integer.valueOf(rowColSplit[4]);   
        int startY = Integer.valueOf(rowColSplit[5]);
        int goalX = Integer.valueOf(rowColSplit[6]);
        int goalY = Integer.valueOf(rowColSplit[7]);
        
        return new Scenario(startX, startY, goalX, goalY);
    }
}
