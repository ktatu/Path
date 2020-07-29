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
import tiralabra.path.data.FileMapReader;

/**
 *
 * @author Tatu
 */
public class FileMapReaderTest extends FileManagement {
    
    FileMapReader testMapReader = new FileMapReader();
    
    File formatTestFile;
    ArrayList<String> formatTestList;
    
    public FileMapReaderTest() {
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
        int[] expectedIntegers = new int[]{0,0,0,1,1,1,0,0,1};
        
        testArrayList.add("@TW");
        testArrayList.add(".GS");
        testArrayList.add("U?.");
        writeIntoFile(testIOFile, testArrayList);
        
        int[][] testMap = testMapReader.getGridMap(testIOFile.getPath());
        
        int expectedIndex = 0;
        for (int y = 0; y < testMap.length; y++) {
            for (int x = 0; x < testMap[0].length; x++) {
                assertEquals(expectedIntegers[expectedIndex], testMap[y][x]);
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