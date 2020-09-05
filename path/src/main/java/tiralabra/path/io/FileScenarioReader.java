package tiralabra.path.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import tiralabra.path.logic.Scenario;

/**
 * Creates a list of Scenarios from a .scen file
 * Note that collectScenarios is only used to provide an ArrayList of scenarios for performance testing
 * No use of ArrayList in the actual program outside of io / performance testing!
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
        
        // Return an empty list when there was no file to read
        if (scenFileAsList.isEmpty()) {
            return scenarios;
        }
        
        readMovingAiFormat(scenFileAsList, scenarios);
        
        return scenarios;
    }
    
    /**
     * Attempt to read each line from the provided list
     * @param scenFileAsList list of the scenarios as string
     * @param scenarios list into which the scenarios will be added
     */
    private void readMovingAiFormat(ArrayList<String> scenFileAsList, ArrayList<Scenario> scenarios) {
        for (int i = 1; i < scenFileAsList.size(); i++) {
            try {
                scenarios.add(movingAiLine(scenFileAsList.get(i), i));
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
    
    /**
     * Converts a line from the file into a scenario
     * @param line the current scenario line
     * @param lineNumber is explicitly given so that exception prints out which line was malformatted
     * @return a Scenario object made out of the line
     * @throws IOException 
     */
    private Scenario movingAiLine(String line, int lineNumber) throws IOException {
        String[] rowColSplit = line.split("	");
        
        if (rowColSplit.length != 9) {
            throw new IOException("readScenarioFromRow(): row " + lineNumber + " is incorrectly formatted");
        }
        
        int startX = Integer.valueOf(rowColSplit[4]);
        int startY = Integer.valueOf(rowColSplit[5]);   
        int goalX = Integer.valueOf(rowColSplit[6]);
        int goalY = Integer.valueOf(rowColSplit[7]);
        
        return new Scenario(startY, startX, goalY, goalX);
    }
}
