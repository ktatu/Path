package data;


import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.data.FileIO;
/**
 *
 * @author Tatu
 */
public class FileIOTest extends FileManagement {

    public FileIOTest() {
        super();
    }
    
    @Test
    public void onlyOneInstanceOfFileIOCanExist() {
        FileIO comparisonTestIO;
        comparisonTestIO = FileIO.getInstance();
        assertEquals(testFileIO, comparisonTestIO);
    }
    
    @Test
    public void fileIsReturnedAsStringArrayList() throws IOException {
        testArrayList.add("version 1");
        testArrayList.add("a_scenario_here");
        writeIntoFile(testIOFile, testArrayList);
        assertEquals(testArrayList, testFileIO.collectFileToList(testIOFile.getPath()));
    }
    
    @Test
    public void emptyArrayListIsReturnedDueToEmptyFile() throws IOException {
        writeIntoFile(testIOFile, testArrayList);
        ArrayList<String> returnedList = testFileIO.collectFileToList(testIOFile.getPath());
        assertTrue(returnedList.isEmpty());
    }
    
    // exception message can be read in output
    @Test
    public void fileNotFoundExceptionIsCaught() {
        testFileIO.collectFileToList("nonExistentFile.map");
    }
}
