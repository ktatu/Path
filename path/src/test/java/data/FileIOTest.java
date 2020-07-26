package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
            writer.write("0	test.map	10	10	4	1	4	2	1");
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
    public void collectFileToListReturnsFileAsStringArrayList() {
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
