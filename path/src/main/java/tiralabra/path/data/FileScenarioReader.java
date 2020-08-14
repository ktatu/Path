package tiralabra.path.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import tiralabra.path.logic.Scenario;

/**
 * Creates a list of Scenarios from a .scen file
 * @author Tatu
 */
public class FileScenarioReader {
    
    FileIO fileIO;
    
    public FileScenarioReader() {
        this.fileIO = FileIO.getInstance();
    }
    
    /**
     * Gets the .scen file as an ArrayList from FileIO and collects them into a list of scenarios
     * @param file .scen file
     * @return list of Scenarios
     */
    public ArrayList<Scenario> collectScenarios(File file) {
        ArrayList<String> scenFileAsList = fileIO.collectFileToList(file);
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
    
    /**
     * Converts a line from the file into a scenario
     * @param line the current scenario line
     * @param lineNumber is explicitly given so that exception prints out which line was malformatted
     * @return a Scenario object made out of the line
     * @throws IOException 
     */
    private Scenario readScenarioFromRow(String line, int lineNumber) throws IOException {
        String[] rowColSplit = line.split("	");
        
        if (rowColSplit.length != 9) {
            throw new IOException("readScenarioFromRow(): row " + lineNumber + " is incorrectly formatted");
        }
        
        int startY = Integer.valueOf(rowColSplit[5]);
        int startX = Integer.valueOf(rowColSplit[4]);   
        int goalY = Integer.valueOf(rowColSplit[7]);
        int goalX = Integer.valueOf(rowColSplit[6]);
        
        return new Scenario(startX, startY, goalX, goalY);
    }
}
