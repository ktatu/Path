package tiralabra.path.data;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import tiralabra.path.algorithms.Algorithm;

/**
 * Creating an image from an algorithm's movement on a map
 * @author Tatu
 */
public class AlgorithmImageWriter {
    
    private Algorithm algo;
    
    /**
     * Writes an image by going through every grid
     * @param algo from which the image is made
     * @param pathAsSet the path found by algorithm
     * @return Image with the path, terrain and visited grids marked
     */
    public WritableImage drawAlgorithm(Algorithm algo) {
        this.algo = algo;
        
        int height = algo.gridMap.getMapHeight();
        int width = algo.gridMap.getMapWidth();
        
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                writer.setColor(x, y, determineGridColor(x, y));
            }
        }
        
        // tilapäinen jps:n kartoitusta varten
        /*
        if (algo.jumpPoints != null) {
            for (int grid: algo.jumpPoints) {
                writer.setColor(algo.intToGridX(grid), algo.intToGridY(grid), Color.YELLOW);
            }
        }
        */
        
        return result;
    }
    
    private Color determineGridColor(int x, int y) {
        if (isStartOrGoal(x, y)) {
            return Color.GREEN;
        } 
        if (algo.path.contains(algo.gridToInt(y, x))) {
            return Color.RED;
        } 
        if (algo.jumpPoints != null) {
            if (algo.jumpPoints.contains(algo.gridToInt(y, x))) {
                return Color.YELLOW;
            }
        }
        if (algo.visited[y][x]) {
            return Color.BLUE;
        } else if (algo.gridMap.isPassable(algo.gridMap.getGrid(y, x))) {
            return Color.LIGHTGRAY;
        }
        return Color.BLACK;
    }
    
    private boolean isStartOrGoal(int x, int y) {
        if (algo.scen.getStartX() == x && algo.scen.getStartY() == y) {
            return true;
        } else if (algo.scen.getGoalX() == x && algo.scen.getGoalY() == y) {
            return true;
        }
        return false;
    }
}
