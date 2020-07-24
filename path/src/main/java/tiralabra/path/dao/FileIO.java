package tiralabra.path.dao;

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
    
    public FileIO(String ioFile) {
        this.ioFile = ioFile;
    }
    
    private void collectFileToList() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ioFile), "UTF-8"));
            this.fileAsList = collectLinesToList(reader.lines());
            reader.close();
        } catch (IOException ex) {
            System.out.println(ex);
        } 
    }
    
    private ArrayList<String> collectLinesToList(Stream<String> lines) {
        return lines.collect(Collectors.toCollection(ArrayList::new));
    }
    
    // collectFileToList() jonnekin muualle
    public ArrayList<String> getFileAsList() {
        collectFileToList();
        return this.fileAsList;
    }
}


/*
        // ehkä mieluummin catchit kullekin poikkeustapaukselle erikseen fileReaderissä, voi helpottaa testausta
        /*catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error: " + ex);
        }*/