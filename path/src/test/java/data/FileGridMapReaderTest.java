package data;

import static data.FileManagement.testArrayList;
import static data.FileManagement.testIOFile;
import java.io.File;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.data.FileGridMapReader;
import tiralabra.path.logic.GridMap;

/**
 *
 * @author Tatu
 */
public class FileGridMapReaderTest extends FileManagement {
    
    FileGridMapReader testMapReader = new FileGridMapReader();
    
    File formatTestFile;
    ArrayList<String> formatTestList;
    
    public FileGridMapReaderTest() {
        super();
    }
    
    @BeforeClass
    public static void setUpClass() {
        testArrayList = new ArrayList<>();
    }
    
    @AfterClass
    public static void tearDownClass() {
        testIOFile.delete();
    }
    
    @Before
    @Override
    public void setUp() {
        testArrayList.add("type octile");
        testArrayList.add("height 3");
        testArrayList.add("width 3");
        testArrayList.add("map");
        testIOFile = new File("test.map");
        // mapTestFile and formatTestList are used when the initial lines above are tested
        formatTestFile = new File("test2.map");
        formatTestList = new ArrayList<>();
    }
    
    @After
    @Override
    public void tearDown() {
        testArrayList.clear();
        testIOFile.delete();
        
        formatTestList.clear();
        formatTestFile.delete();
    }
    
    @Test
    public void mapInFileIsConvertedIntoIntegerBasedMap() {
        char[] expectedCharacters = new char[]{'@', 'T', 'W', '.', 'G', 'S', 'U', '?', '.'};
        int expectedIndex = 0;
        
        testArrayList.add("@TW");
        testArrayList.add(".GS");
        testArrayList.add("U?.");
        writeIntoFile(testIOFile, testArrayList);
        
        GridMap gridMap = testMapReader.getGridMap(testIOFile.getPath());
        
        for (int y = 0; y < gridMap.getMapHeight(); y++) {
            for (int x = 0; x < gridMap.getMapWidth(); x++) {
                assertEquals(expectedCharacters[expectedIndex], gridMap.getGrid(y, x));
                expectedIndex++;
            }
        }
    }
    
    //uneven as in all map lines must be of equal length
    @Test(expected = IndexOutOfBoundsException.class)
    public void unevenLinesInFileCauseThrownIndexOutOfBoundsException() {
        testArrayList.add("@@@");
        testArrayList.add("..");
        testArrayList.add("@@@");
        writeFileThenReadIt(testIOFile, testArrayList);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void fewerLinesThanExpectedCausesThrownIndexOutOfBoundsException() {
        testArrayList.add("@@@");
        testArrayList.add("...");
        writeFileThenReadIt(testIOFile, testArrayList);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void emptySpaceBetweenLinesCausesThrownIndexOutOfBoundsException() {
        testArrayList.add("@@@");
        testArrayList.add("");
        testArrayList.add("@@@");
        testArrayList.add("...");
        writeFileThenReadIt(testIOFile, testArrayList);
    }
    
    // both dimensions are handled similarly so testing only one is sufficient
    @Test(expected = IndexOutOfBoundsException.class)
    public void noSpaceInDimensionLineCausesThrownIndexOutOfBoundsException() {
        // error should happen on height line so no need to add more lines
        formatTestList.add("type octile");
        formatTestList.add("height3");
        writeFileThenReadIt(formatTestFile, formatTestList);
    }
    
    @Test(expected = NumberFormatException.class)
    public void otherwiseIncorrectlyFormattedDimensionLineCausesNumberFormatException() {
        formatTestList.add("type octile");
        formatTestList.add("height    3");
        writeFileThenReadIt(formatTestFile, formatTestList);
    }
    
    public void writeFileThenReadIt(File file, ArrayList<String> testList) {
        writeIntoFile(file, testList);
        testMapReader.getGridMap(file.getPath());
    }
}