package tiralabra.path.performance;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * A performance test comparing BFS's path lengths to optimal paths
 * @author Tatu
 */
public class BFSPathLength {
    
    public BarChart pathLengthChart(ArrayList<GridMap> maps, ArrayList<ArrayList<Scenario>> scens, ArrayList<String> mapNames) {
        CategoryAxis mapName = new CategoryAxis();
        mapName.setLabel("Map");
        
        NumberAxis pathLength = new NumberAxis();
        pathLength.setLabel("BFS path length % of optimal path length");
        
        BarChart<String, Number> barChart = new BarChart<>(mapName, pathLength);
        barChart.setTitle("BFS path and optimal path length");
        barChart.setBarGap(3);
        barChart.setCategoryGap(20);
        
        addBars(barChart, maps, scens, mapNames);
        
        return barChart;
    }
    
    /**
     * Create a bar for the barchart out of each map
     * @param barChart the graph
     * @param maps maps on which the performance tests will be run
     * @param scens list of scenarios for each map
     * @param mapNames name of each map
     */
    private void addBars(BarChart<String, Number> barChart, ArrayList<GridMap> maps, ArrayList<ArrayList<Scenario>> scens, ArrayList<String> mapNames) {
        XYChart.Series worstCase = new XYChart.Series();
        worstCase.setName("Worst case");
        
        XYChart.Series avg = new XYChart.Series();
        avg.setName("Average");
        
        XYChart.Series median = new XYChart.Series();
        median.setName("Median");
        
        for (int i = 0; i < maps.size(); i++) {
            int mapIndex = i + 1;
            System.out.println("Running map " + mapIndex + "/" + maps.size());
            
            float[] results = getMapData(maps.get(i), scens.get(i));
            
            worstCase.getData().add(new XYChart.Data(mapNames.get(i), getWorstCase(results)));
            avg.getData().add(new XYChart.Data(mapNames.get(i), getAverage(results)));
            median.getData().add(new XYChart.Data(mapNames.get(i), getMedian(results)));
        }
        
        barChart.getData().addAll(worstCase, avg, median);
    }
    
    /**
     * Run a map's scenarios and gather result data
     * @param map
     * @param scens
     * @return Array of performance results, cell for each scenario
     */
    private float[] getMapData(GridMap map, ArrayList<Scenario> scens) {
        AlgorithmRunner runner = new AlgorithmRunner(1, map);
        
        float optimalLength;
        float bfsLength;
        
        float[] resData = new float[scens.size()];
        
        for (int i = 0; i < scens.size(); i++) {
            // JPS is used to find the optimal path length for comparison
            runner.algorithmPerformanceTest("JPS", scens.get(i));
            optimalLength = runner.getDistance();
            
            runner.algorithmPerformanceTest("BFS", scens.get(i));
            bfsLength = runner.getDistance();
            
            resData[i] = (optimalLength / bfsLength) * 100;
        }
        
        return resData;
    }
    
    /**
     * Poorest result of performance testing
     * @param results array of results
     * @return result with lowest percentage value
     */
    private float getWorstCase(float[] results) {
        float worstCase = 100f;
        
        for (int i = 0; i < results.length; i++) {
            if (results[i] < worstCase) {
                worstCase = results[i];
            }
        }
        return worstCase;
    }
    
    /**
     * Calculate average result
     * @param results array of results
     * @return average from the array
     */
    private float getAverage(float[] results) {
        long sumOfDifferences = 0l;
        for (float res: results) {
            // Some scen files contain scenarios where the start and goal grids are the same, skip these
            if (Float.isNaN(res)) {
                continue;
            }
            sumOfDifferences += res;
        }
        return (float) sumOfDifferences / results.length;
    }
    
    /**
     * Median result
     * @param results array of results
     * @return median result from the array
     */
    private float getMedian(float[] results) {
        Arrays.sort(results);
        
        return results[results.length / 2];
    }
}
