package tiralabra.path.logic;

import java.util.HashSet;
import javafx.scene.image.WritableImage;
import tiralabra.path.algorithms.AStar;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.algorithms.BreadthFirstSearch;
import tiralabra.path.algorithms.Dijkstra;
import tiralabra.path.data.AlgorithmImageWriter;
import tiralabra.path.data.FileIO;
import tiralabra.path.logic.exceptions.NoPathFoundException;


/**
 * Running the selected algorithm and gathering result related data to display in gui
 * @author Tatu
 */
public class AlgorithmService {
    
    private Algorithm algo;
    private boolean saveImage;
    
    private long startTime;
    private long endTime;
    
    private String algoId;
    
    public void executeAlgorithm(String algoId, GridMap map, Scenario scen, boolean saveImage) throws NoPathFoundException {
        setAlgorithm(algoId, map, scen);
        this.saveImage = saveImage;
        this.algoId = algoId;
        
        System.out.println("Running " + algoId);
        
        startTime = System.nanoTime();
        algo.runAlgorithm();
        endTime = System.nanoTime();
        
        if (!algo.goalVisited()) {
            throw new NoPathFoundException(algoId + " didn't find a path to goal grid");
        }
    }
    
    private void setAlgorithm(String algoId, GridMap gridMap, Scenario scen) {
        switch (algoId) {
            case "bfs":
                algo = new BreadthFirstSearch(gridMap, scen);
                break;
            case "dijkstra":
                algo = new Dijkstra(gridMap, scen);
                break;
            case "aStar":
                algo = new AStar(gridMap, scen);
                break;
            default:
                // Should never happen
        }
    }
    
    public String getResultInfo() {
        return "Runtime: " + runTime() + "s, " + "visited grids: " + numOfVisitedGrids() + ", path length: " + pathLength();
    }
    
    private int numOfVisitedGrids() {
        int visitedGrids = 0;
        for (int y = 0; y < algo.gridMap.getMapHeight(); y++) {
            for (int x = 0; x < algo.gridMap.getMapWidth(); x++) {
                if (algo.visited[y][x]) {
                    visitedGrids++;
                }
            }
        }
        return visitedGrids;
    }
    
    /**
     * Distance from start grid to goal grid
     * @return distance as float retrieved from distance matrix
     */
    private float pathLength() {
        return algo.distance[algo.scen.getGoalY()][algo.scen.getGoalX()];
    }
    
    /**
     * Runtime of algorithm
     * @return runtime in seconds
     */
    private double runTime() {
        return ((endTime - startTime) / 1e9);
    }
    
    public WritableImage getAlgoImage() {
        final AlgorithmImageWriter algDrawer = new AlgorithmImageWriter();
        
        WritableImage algoImage = algDrawer.drawAlgorithm(algo, pathAsList());

        if (saveImage) {
            FileIO.getInstance().saveImage(algoImage, algoId);
        }
        return algoImage;
    }
    
    private HashSet<Integer> pathAsList() {
        HashSet<Integer> pathList = new HashSet<>();
        int goalGridAsInt = algo.gridToInt(algo.scen.getGoalY(), algo.scen.getGoalX());
        
        while (algo.prevGrid[goalGridAsInt] != -1) {
            pathList.add(goalGridAsInt);
            goalGridAsInt = algo.prevGrid[goalGridAsInt];
        }
        return pathList;
    }
}
