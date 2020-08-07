package tiralabra.path.ui;

import java.util.ArrayList;
import java.util.PriorityQueue;
import tiralabra.path.algorithms.AStar;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.algorithms.BreadthFirstSearch;
import tiralabra.path.algorithms.Dijkstra;
import tiralabra.path.data.FileGridMapReader;
import tiralabra.path.data.FileScenarioReader;
import tiralabra.path.logic.Grid;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;
import tiralabra.path.logic.ScenarioValidation;
import tiralabra.path.logic.exceptions.InvalidScenarioException;

/**
 *
 * @author Tatu
 */
public class ui {
    
    public static void main(String[] args) {
        
        char[][] testMatrix = {{'.','@','@','.',},
                               {'@','@','.','.'},
                               {'.','.','.','.'},
                               {'.','.','.','@'}};
        
        char[][] testMatrix2 = {{'.','.','.'},
                                {'.','.','.'},
                                {'.','.','.'},
                               };
        
        FileGridMapReader mapReader = new FileGridMapReader();
        
        
        GridMap test1Map = new GridMap(testMatrix);
        GridMap test2Map = new GridMap(testMatrix2);
        
        
        /*
....@@...@
...@......
.....@....
.......@..
..........
@......@..
.@........
........@.
.....@@.@@
..........
scenario start: 3, 2
Scenario goal: 6, 2
        */
        
        
        GridMap map = mapReader.getGridMap("src/battleground.map");
        
        
        // reitti n. 530, toimi 4.8 19.00
        /*
        Algorithm dijkstra = new Dijkstra(map, new Scenario(86, 59, 426, 414));
        dijkstra.runAlgorithm();
        */
        
        // sama kuin ylläoleva mutta eri mapissa
       
        Algorithm bfs = new BreadthFirstSearch(map, new Scenario(86, 59, 426, 414));
        bfs.runAlgorithm();
        System.out.println("run time bfs " + bfs.getRunTime());
        
        Algorithm dijkstra2 = new Dijkstra(map, new Scenario(86, 59, 426, 414));
        dijkstra2.runAlgorithm();
        System.out.println("runtime dijkstra " + dijkstra2.getRunTime());
        //dijkstra2.printPath();
        
        
        
        Algorithm aStar = new AStar(map, new Scenario(86, 59, 426, 414));
        aStar.runAlgorithm();
        System.out.println("runtime aStar " + aStar.getRunTime());
        //aStar.printPath();
        
    }
}

/*
.......@..
.......@..
...@......
.........@
.@.@...@@.
..@@@.....
..........
..........
..........
..........
y: 4, x: 4
start(y,x): 4, 4
goal(y,x): 5, 7
start char: .
goal char: .
*/