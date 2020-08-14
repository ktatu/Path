package data;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.data.FileScenarioReader;
import tiralabra.path.logic.Scenario;

/**
 *
 * @author Tatu
 */
public class FileScenarioReaderTest extends FileManagement {
    
    FileScenarioReader testScenReader;
    
    public FileScenarioReaderTest() {
        super();
        this.testScenReader = new FileScenarioReader();
    }
    
    @Test
    public void scenariosInFileAreCollectedIntoScenarioArrayList() {
        testArrayList.add("version 1");
        
        // visually the gap between 0 and test.map is incorrect but it's correctly formatted
        testArrayList.add("0	test.map	10	10	4	1	4	2	1");
        testArrayList.add("0	test.map	10	10	7	8	5	9	8");
        testArrayList.add("0	test.map	10	10	3	2	0	3	0");
        writeIntoFile(testIOFile, testArrayList);
        
        ArrayList<Scenario> scenList = testScenReader.collectScenarios(testIOFile);
        
        assertEquals(4, scenList.get(0).getStartY());
        assertEquals(1, scenList.get(0).getStartX());
        assertEquals(4, scenList.get(0).getGoalY());
        assertEquals(2, scenList.get(0).getGoalX());
        
        assertEquals(3, scenList.get(2).getStartY());
        assertEquals(2, scenList.get(2).getStartX());
        assertEquals(0, scenList.get(2).getGoalY());
        assertEquals(3, scenList.get(2).getGoalX());
    }
    
    // incorrect spacing changes number of rows in the split String array
    @Test
    public void incorrectlyFormattedLinesDontMakeItToScenList() {
        testArrayList.add("version 1");
        testArrayList.add("0 test.map 10 10 4 1 4 2 1");
        testArrayList.add("0	test.map	10	10	3	2	0	3	0");
        writeIntoFile(testIOFile, testArrayList);
        
        ArrayList<Scenario> scenList = testScenReader.collectScenarios(testIOFile);
        assertEquals(1, scenList.size());
    }
    
    @Test
    public void singleLineFileMakesReaderReturnEmptyArrayList() {
        testArrayList.add("version 1");
        writeIntoFile(testIOFile, testArrayList);
        
       ArrayList<Scenario> scenList = testScenReader.collectScenarios(testIOFile);
       assertEquals(0, scenList.size());
    }
    
    @Test
    public void emptyFileMakesReaderReturnEmptyArrayList() {
        writeIntoFile(testIOFile, testArrayList);
        
        ArrayList<Scenario> scenList = testScenReader.collectScenarios(testIOFile);
        assertEquals(0, scenList.size());
    }
    
    @Test
    public void incorrectNumberOfCorrectlyFormattedColumnsCauseIOExcepion() {
        testArrayList.add("version 1");
        testArrayList.add("0	test.map	10	10	3	2	0	3	0	1");
        writeIntoFile(testIOFile, testArrayList);
        
        ArrayList<Scenario> scenList = testScenReader.collectScenarios(testIOFile);
        assertEquals(0, scenList.size());
    }
    
    @Test(expected = NumberFormatException.class)
    public void stringsInsteadOfIntegersInFilethrowsNumberFormatException() {
        testArrayList.add("version 1");
        testArrayList.add("0	test.map	10	10	@	2	0	3	0");
        writeIntoFile(testIOFile, testArrayList);
        
        testScenReader.collectScenarios(testIOFile);
    }
}