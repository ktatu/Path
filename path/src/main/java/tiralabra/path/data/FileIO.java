package tiralabra.path.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * All file related functionalities happen through FileIO
 */
public class FileIO {

    // FileIO is a Singleton, meaning only one instance of the class can exist
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
    
    /**
     * Reads every line of a file and collects them into an ArrayList
     * @param file 
     * @return the file collected into an ArrayList
     */
    public ArrayList<String> collectFileToList(File file) {
        ArrayList<String> fileAsList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            fileAsList = reader.lines().collect(Collectors.toCollection(ArrayList::new));
            reader.close();
        } catch (IOException ex) {
            System.out.println(ex);
        } 
        return fileAsList;
    }
}