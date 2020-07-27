package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.data.FileIO;

/**
 *
 * @author Tatu
 */
public class FileIOTest {
    
    public FileIOTest() {
    }
    
    static File testIOFile;
    static FileIO testFileIO;
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        testIOFile = new File("testIOFile.map.scen");
        testFileIO = FileIO.getInstance();
        
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(testIOFile), "utf-8"))) {
            
            writer.write("version 1\n");
            writer.write("a_scenario_here");
            writer.close();
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
       testIOFile.delete();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void onlyOneInstanceOfFileIOCanExist() {
        FileIO comparisonTestIO;
        comparisonTestIO = FileIO.getInstance();
        assertEquals(testFileIO, comparisonTestIO);
    }
    
    @Test
    public void fileIsReturnedStringArrayList() throws IOException {
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add("version 1");
        expectedList.add("a_scenario_here");
        assertEquals(expectedList, testFileIO.collectFileToList(testIOFile.getPath()));
    }
    
    @Test
    public void fileNotFoundExceptionIsCaught() {
        testFileIO.collectFileToList("nonExistingFile.map");
    }
}
