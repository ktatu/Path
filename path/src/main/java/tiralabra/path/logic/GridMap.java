package tiralabra.path.logic;

/**
 * Map which algorithms run on
 * @author Tatu
 */
public class GridMap {
    
    private final char[][] gridMap;
    private final char[] passableTerrain = {'.', 'G', 'S'};
 
    public GridMap(char[][] gridMap) {
        this.gridMap = gridMap;
    }
    
    public boolean isPassable(char terrain) {
        return contains(terrain);
    }
    
    public boolean passableGrid(int y, int x) {
        return contains(gridMap[y][x]);
    }
    
    public char getGrid(int y, int x) {
        return gridMap[y][x];
    }
    
    public int getMapHeight() {
        return this.gridMap.length;
    }
    
    public int getMapWidth() {
        return this.gridMap[0].length;
    }
    
    /**
     * Check if the terrain array contains a character
     * @param terrain the terrain that is being evaluated
     * @return true if the array contains parameter char
     */
    private boolean contains(char terrain) {
        for (int i = 0; i < passableTerrain.length; i++) {
            if (passableTerrain[i] == terrain) {
                return true;
            }
        }
        return false;
    }
}
