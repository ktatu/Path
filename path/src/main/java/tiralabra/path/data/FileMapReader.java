package tiralabra.path.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
/**
 *
 * @author Tatu
 */
/*
To implement different cost terrain types: HashSets into HashMaps that store char and cost
Store cost alongside terrain types in config-file (e.g .:1,S:3)
modify map-creation functions
*/
public class FileMapReader {
    
    private ArrayList<String> gridMapFileAsList;
    
    FileIO fileIO;
    
    // variables related to Moving Ai file format
    private Set<Character> passableTerrain = Set.of('.', 'G', 'S');
    private int heightRow = 1;
    private int widthRow = 2;
    private int mapStartRow = 4;
    
    public FileMapReader() {
        this.fileIO = FileIO.getInstance();
    }
    
    public int[][] getGridMap(String filePath) {
        gridMapFileAsList = fileIO.getFileAsList(filePath);
        return createGridMap();
    }
    
    private int[][] createGridMap() {
        int width = Integer.valueOf(gridMapFileAsList.get(widthRow).split(" ")[1]);
        int height = Integer.valueOf(gridMapFileAsList.get(heightRow).split(" ")[1]);
        
        int[][] gridMap = new int[height][width];
        
        int fileY = mapStartRow;
        
        for (int y = 0; y < gridMap.length; y++) {
            for (int x = 0; x < gridMap[0].length; x++) {
                gridMap[y][x] = convertTerrainTypeToInteger(gridMapFileAsList.get(fileY).charAt(x));
            }
            fileY++;
        }
        return gridMap;
    }
    
    private int convertTerrainTypeToInteger(Character terrain) {
        if (passableTerrain.contains(terrain)) {
            return 1;
        }
        return 0;
    } 
}
