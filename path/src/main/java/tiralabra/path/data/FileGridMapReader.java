package tiralabra.path.data;

import java.io.File;
import java.util.ArrayList;
import tiralabra.path.logic.GridMap;

/**
 * Creates a GridMap from a .map file
 * @author Tatu
 */
public class FileGridMapReader {
    
    private FileIO fileIO;
    
    // Variables related to Moving Ai file format
    private int heightRow = 1;
    private int widthRow = 2;
    private int mapStartRow = 4;
    
    /**
     * FileIO instance for getting the map
     */
    public FileGridMapReader() {
        this.fileIO = FileIO.getInstance();
    }
    
    /**
     * Creates a matrix map for a GridMap by going through every cell of the map and returns the GridMap
     * @param file the file to be converted into a map
     * @return GridMap which will be used by pathfinding algorithms
     */
    public GridMap getGridMap(File file) throws IndexOutOfBoundsException, NumberFormatException {
        ArrayList<String> gridMapFileAsList = fileIO.collectFileToList(file);
        
        int height = Integer.valueOf(gridMapFileAsList.get(heightRow).split(" ")[1]);
        int width = Integer.valueOf(gridMapFileAsList.get(widthRow).split(" ")[1]);
        
        char[][] gridMap = new char[height][width];
        
        int fileY = mapStartRow;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                gridMap[y][x] = gridMapFileAsList.get(fileY).charAt(x);
            }
            fileY++;
        }
        return new GridMap(gridMap);
    }
}
