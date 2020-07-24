package tiralabra.path.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
/**
 *
 * @author Tatu
 */
public class FileMapDao implements MapDao {
    
    HashSet<Character> passableTerrain;
    HashSet<Character> unPassableTerrain;
    
    public FileMapDao() {
        passableTerrain = new HashSet<>();
        unPassableTerrain = new HashSet<>();
    }
    
    @Override
    public int[][] createMap(Object object) {
        getTerrainTypes();
        
    }

    @Override
    public void getTerrainTypes() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
            collectTerrainTypesToSet(passableTerrain, properties.getProperty("passable"));
            collectTerrainTypesToSet(unPassableTerrain, properties.getProperty("unPassable"));
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    private void collectTerrainTypesToSet(HashSet<Character> terrainSet, String terrainTypes) {
        for (int i = 0; i < terrainTypes.length(); i++) {
            terrainSet.add(terrainTypes.charAt(i));
        }
    }
    
    // laittaa FileIO:n lukemaan kartan tiedostosta ja palauttaa sen createMap():lle
    private ArrayList<String> getMapFromFileIO(String mapFile) {
        
    }
}
