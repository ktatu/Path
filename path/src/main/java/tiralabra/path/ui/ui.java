package tiralabra.path.ui;

import java.util.ArrayList;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.algorithms.BreadthFirstSearch;
import tiralabra.path.data.FileMapReader;
import tiralabra.path.data.FileScenarioReader;
import tiralabra.path.logic.Scenario;

/**
 *
 * @author Tatu
 */
public class ui {
    
    public static void main(String[] args) {
        FileMapReader mapReader = new FileMapReader();
        
    /*    int[][] map = mapReader.getGridMap("src/test.map");
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                System.out.print(map[y][x]);
            }
            System.out.println("");
        }
    */    
    
        
        Scenario scen = new Scenario(0, 9, 0, 7);
        int[][] map = mapReader.getGridMap("src/test.map");
        
        Algorithm bfs = new BreadthFirstSearch(map, scen);
        bfs.initializeAlgorithm();
        bfs.runAlgorithm();
    }
}
