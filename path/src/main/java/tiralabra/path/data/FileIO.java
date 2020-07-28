package tiralabra.path.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author Tatu
 */
public class FileIO {

    private static FileIO instance = null;
    
    // private constructor prevents other classes from creating instances of FileIO using parameterless constructor
    private FileIO(){
    }
    
    // returns instance of FileIO if such exists, otherwise creates one
    public static FileIO getInstance() {
        if (instance == null) {
            instance = new FileIO();
        }
        return instance;
    }
    
    public ArrayList<String> collectFileToList(String ioFile) {
        ArrayList<String> fileAsList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ioFile), "UTF-8"));
            fileAsList = reader.lines().collect(Collectors.toCollection(ArrayList::new));
            reader.close();
        } catch (IOException ex) {
            System.out.println(ex);
        } 
        return fileAsList;
    }
}

// Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);