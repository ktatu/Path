package tiralabra.path.logic;

import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import tiralabra.path.algorithms.AStar;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.algorithms.BreadthFirstSearch;
import tiralabra.path.algorithms.Dijkstra;
import tiralabra.path.logic.exceptions.NoPathFoundException;


/**
 *
 * @author Tatu
 */
public class AlgorithmService {
    
    private GridMap gridMap;
    private Scenario scen;
    
    public AlgorithmService() {
    }
    
    public void executeAlgorithm(String algoId, GridMap map, Scenario scen) throws NoPathFoundException {
        this.gridMap = map;
        this.scen = scen;
        
        Algorithm algo = getAlgorithm(algoId);
        System.out.println("Running " + algoId);
        algo.runAlgorithm();
        
        if (!algo.goalVisited()) {
            throw new NoPathFoundException(algoId + " didn't find a path to goal grid");
        }
        
        getImageOfAlgorithm(algo);
    }
    
    private Algorithm getAlgorithm(String algoId) {
        switch (algoId) {
            case "bfs":
                return new BreadthFirstSearch(gridMap, scen);
            case "dijkstra":
                return new Dijkstra(gridMap, scen);
            case "aStar":
                return new AStar(gridMap, scen);
            default:
                // Should never happen
                return null;
        }
    }
    
    public WritableImage getImageOfAlgorithm(Algorithm algo) {
        WritableImage result = new WritableImage(gridMap.getMapWidth(), gridMap.getMapHeight());
        PixelWriter writer = result.getPixelWriter();
        
        for (int y = 0; y < gridMap.getMapHeight(); y++) {
            for (int x = 0; x < gridMap.getMapWidth(); x++) {
                
                // Draw the map with visited grids
                if (algo.visited[y][x]) {
                    writer.setColor(x, y, Color.BLUE);
                } else if (gridMap.isPassable(gridMap.getGrid(y, x))) {
                    writer.setColor(x, y, Color.WHITE);
                } else {
                    writer.setColor(x, y, Color.BLACK);
                }
            }
        }
        
        // Draw the actual path on top of map
        int grid = algo.gridToInt(scen.getGoalY(), scen.getGoalX());
        while (algo.prevGrid[grid] != -1) {
            int pathY = algo.intToGridY(grid);
            int pathX = algo.intToGridX(grid);
            writer.setColor(pathX, pathY, Color.RED);
            grid = algo.prevGrid[grid];
        }
        
        writer.setColor(scen.getStartX(), scen.getStartY(), Color.GREEN);
        writer.setColor(scen.getGoalX(), scen.getGoalY(), Color.GREEN);
        
        File testFile = new File("test.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(result, null),
                    "png", testFile);
        } catch (IOException e) {
            System.out.println("image saving failed");
        }
        return result;
    }
}
