package tiralabra.path.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Tatu
 */
public class FileIO {
    
    private String ioFile;
    private ArrayList<String> fileAsList;
    private static FileIO instance = null;
    
    // there will only be one instance of FileIO that all classes will use
    public static FileIO getInstance() {
        if (instance == null) {
            instance = new FileIO();
        }
        return instance;
    }
    
    private void collectFileToList() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ioFile), "UTF-8"));
            this.fileAsList = collectLinesToList(reader.lines());
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } 
    }
    
    private ArrayList<String> collectLinesToList(Stream<String> lines) {
        return lines.collect(Collectors.toCollection(ArrayList::new));
    }
    
    public ArrayList<String> getFileAsList(String ioFile) {
        this.ioFile = ioFile;
        collectFileToList();
        return this.fileAsList;
    }
}