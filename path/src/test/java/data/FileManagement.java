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
import tiralabra.path.data.FileIO;
/**
 * Used by test classes for creating tested files
 * @author Tatu
 */
public class FileManagement {
    
    public static File testIOFile;
    public static FileIO testFileIO;
    public static ArrayList<String> testArrayList;
    
    public FileManagement() {
    }
    
    public static void writeIntoFile(File file, ArrayList<String> lines) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(file), "utf-8"))) {
            for (String line: lines) {
                writer.write(line + "\n");
            }
            writer.close();
        } catch(IOException ex) {
            System.out.println(ex);
        }
    }
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        testFileIO = FileIO.getInstance();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testIOFile = new File("testIOFile.test");
        testArrayList = new ArrayList<>();
    }
    
    @After
    public void tearDown() {
       testIOFile.delete();
       testArrayList.clear();
    }
}
