package tiralabra.path.io;

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
        
        return result;
    }
    
    /**
     * Determining color for point (x,y) in grid
     * @param x coordinate
     * @param y coordinate
     * @return color
     */
    private Color determineGridColor(int x, int y) {
        if ((algo.scen.getStartX() == x && algo.scen.getStartY() == y) || (algo.scen.getGoalX() == x && algo.scen.getGoalY() == y)) {
            return Color.GREEN;
        } 
        if (algo.path.contains(algo.gridToInt(y, x))) {
            return Color.RED;
        } 
        
        if (algo.visited[y][x]) {
            return Color.BLUE;
        } 
        if (algo.gridMap.isPassable(algo.gridMap.getGrid(y, x))) {
            return Color.LIGHTGRAY;
        }
        return Color.BLACK;
    }
}