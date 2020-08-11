package tiralabra.path.ui;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import tiralabra.path.algorithms.AStar;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.algorithms.BreadthFirstSearch;
import tiralabra.path.algorithms.Dijkstra;
import tiralabra.path.algorithms.JumpPointSearch;
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
        
        GridMap map = mapReader.getGridMap("src/battleground.map");
        
        
        // sama kuin ylläoleva mutta eri mapissa
        /*
        Algorithm bfs = new BreadthFirstSearch(map, new Scenario(86, 59, 426, 414));
        bfs.runAlgorithm();
        System.out.println("runtime bfs " + bfs.getRunTime());
        
        Algorithm dijkstra2 = new Dijkstra(map, new Scenario(86, 59, 426, 414));
        dijkstra2.runAlgorithm();
        System.out.println("runtime dijkstra " + dijkstra2.getRunTime());
        System.out.println("dijkstra length " + dijkstra2.getPathLength());
        
        Algorithm aStar = new AStar(map, new Scenario(86, 59, 426, 414));
        aStar.runAlgorithm();
        System.out.println("runtime aStar " + aStar.getRunTime());
        System.out.println("aStar length " + aStar.getPathLength());
        
        Algorithm jps2 = new JumpPointSearch(map, new Scenario(86, 59, 426, 414));
        jps2.runAlgorithm();
        System.out.println("runtime jps " + jps2.getRunTime());
        System.out.println("jump points: " + jps2.getJumpPoints());
        System.out.println("jps length: " + jps2.getPathLength());
        */
        
        /*
        Algorithm aStar = new AStar(map, new Scenario(86, 59, 426, 414));
        aStar.runAlgorithm();
        System.out.println("runtime aStar " + aStar.getRunTime());
        //aStar.printPath();
        ArrayDeque<Integer> asd = new ArrayDeque<>();
        int testi = (int) (6.0/7.0*9);
        System.out.println(testi);
        
        int[] testi2 = new int[3];
        System.out.println(testi2[2]);
        
        ArrayDeque<Integer> testi3 = new ArrayDeque<>();
        System.out.println(testi3.pollFirst());
        
        
        char[][] jpsTest = {{'.','.'},
                            {'.','.'},
                            {'@','.'},
                            {'.','.'},
                            {'.','.'}};
       
        GridMap jpsMap = new GridMap(jpsTest);
        Algorithm jps = new JumpPointSearch(jpsMap, new Scenario(4,0,0,0));
        */
        
        /*
0	battleground.map	512	512	146	393	148	394	2.41421356 OK 2.4142137
0	battleground.map	512	512	312	252	312	250	2.00000000 OK 2.0
0	battleground.map	512	512	221	102	221	102	0.00000000 EI PÄÄSE LÄPI VALIDATIONISTA
0	battleground.map	512	512	352	279	353	282	3.41421356 OK 3.4142137
0	battleground.map	512	512	298	65	300	63	2.82842712 OK 2.828427
0	battleground.map	512	512	256	181	257	184	3.41421356 OK 3.4142137
0	battleground.map	512	512	373	366	372	365	1.41421356 OK 1.4142135
0	battleground.map	512	512	104	126	106	128	2.82842712 OK 2.828427
0	battleground.map	512	512	379	448	381	448	2.00000000 OK 2.0
        */
        
        Algorithm jps = new JumpPointSearch(map, new Scenario(448, 379, 448, 381));
        jps.runAlgorithm();
        System.out.println(jps.getPathLength());
    }
}

        