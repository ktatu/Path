package tiralabra.path.data;

import java.util.ArrayList;
import java.util.Set;

/**
 * Creates and returns an Integer matrix out of a .map file
 * @author Tatu
 */
public class FileMapReader {
    
    FileIO fileIO;
    
    // Variables related to Moving Ai file format
    private Set<Character> passableTerrain = Set.of('.', 'G', 'S');
    private int heightRow = 1;
    private int widthRow = 2;
    private int mapStartRow = 4;
    
    public FileMapReader() {
        this.fileIO = FileIO.getInstance();
    }
    
    /**
     * Converts the ArrayList into a matrix map by going through every cell of the map
     * @param mapFilePath directory for the .map file path to be converted into a map
     * @return the int[][] map which will be used by pathfinding algorithms
     */
    public int[][] getGridMap(String mapFilePath) {
        ArrayList<String> gridMapFileAsList = fileIO.collectFileToList(mapFilePath);
        
        int height = Integer.valueOf(gridMapFileAsList.get(heightRow).split(" ")[1]);
        int width = Integer.valueOf(gridMapFileAsList.get(widthRow).split(" ")[1]);
        
        int[][] gridMap = new int[height][width];
        
        int fileY = mapStartRow;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                gridMap[y][x] = convertTerrainTypeToInteger(gridMapFileAsList.get(fileY).charAt(x));
            }
            fileY++;
        }
        return gridMap;
    }
    
    /**
     * 
     * @param terrain is a cell read from the file ArrayList
     * @return passable terrain is converted into 1, otherwise 0
     */
    private int convertTerrainTypeToInteger(Character terrain) {
        if (passableTerrain.contains(terrain)) {
            return 1;
        }
        return 0;
    } 
}
