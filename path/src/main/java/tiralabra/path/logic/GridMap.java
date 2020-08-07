package tiralabra.path.logic;

import java.util.Set;

/**
 * Map which algorithms run on
 * @author Tatu
 */
public class GridMap {
    
    private char[][] gridMap;
    private Set<Character> passableTerrain = Set.of('.', 'G', 'S');
 
    public GridMap(char[][] gridMap) {
        this.gridMap = gridMap;
    }
    
    public boolean isPassable(char terrain) {
        return passableTerrain.contains(terrain);
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
}
