package tiralabra.path.ui;

import java.util.ArrayList;
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
        FileScenarioReader scenReader = new FileScenarioReader();
        ArrayList<Scenario> scens = scenReader.getScenarios("src/battleground.map.scen");
    }
}
