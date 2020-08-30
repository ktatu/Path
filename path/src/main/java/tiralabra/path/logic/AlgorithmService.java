package tiralabra.path.logic;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javafx.scene.image.WritableImage;
import tiralabra.path.algorithms.AStar;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.algorithms.BreadthFirstSearch;
import tiralabra.path.algorithms.Dijkstra;
import tiralabra.path.algorithms.JumpPointSearch;
import tiralabra.path.data.AlgorithmImageWriter;
import tiralabra.path.data.FileIO;
import tiralabra.path.logic.exceptions.NoPathFoundException;


/**
 * Running the selected algorithm and gathering result related data to display in gui
 * @author Tatu
 */
public class AlgorithmService {
    
    public Algorithm algo;
    private boolean saveImage;
    
    private long startTime;
    private long endTime;
    
    public void executeAlgorithm(String algoId, GridMap map, Scenario scen, boolean saveImage) throws NoPathFoundException {
        setAlgorithm(algoId);
        this.saveImage = saveImage;
        
        startTime = System.nanoTime();
        algo.runAlgorithm(map, scen);
        endTime = System.nanoTime();
        
        
        if (!algo.goalVisited()) {
            throw new NoPathFoundException(algoId + " didn't find a path to goal grid");
        }
        
    }
    
    private void setAlgorithm(String algoId) {
        switch (algoId) {
            case "bfs":
                algo = new BreadthFirstSearch();
                break;
            case "dijkstra":
                algo = new Dijkstra();
                break;
            case "aStar":
                algo = new AStar();
                break;
            case "jps":
                algo = new JumpPointSearch();
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
     * Distance from start grid to goal grid rounded to ceiling at accuracy of 1 decimal
     * @return rounded distance
     */
    private String pathLength() {
        DecimalFormat dFormat = new DecimalFormat("#.#");
        dFormat.setRoundingMode(RoundingMode.CEILING);
        dFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        
        float dist = algo.distance[algo.scen.getGoalY()][algo.scen.getGoalX()];
        
        return dFormat.format(dist);
    }
    
    /**
     * Runtime of algorithm
     * @return runtime in seconds
     */
    private double runTime() {
        System.out.println((endTime - startTime) / 1e9);
        return ((endTime - startTime) / 1e9);
    }
    
    /**
     * Calling AlgorithmImageWriter to draw the algorithm
     * @return WritableImage object of the algorithm
     */
    public WritableImage getAlgoImage() {
        final AlgorithmImageWriter algDrawer = new AlgorithmImageWriter();
        
        WritableImage algoImage = algDrawer.drawAlgorithm(algo);

        if (saveImage) {
            FileIO.getInstance().saveImage(algoImage, algo.getClass().getSimpleName());
        }
        return algoImage;
    }
}
