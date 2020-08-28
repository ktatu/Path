package tiralabra.path.performance;

import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import tiralabra.path.algorithms.AStar;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.algorithms.BreadthFirstSearch;
import tiralabra.path.algorithms.Dijkstra;
import tiralabra.path.algorithms.JumpPointSearch;
import tiralabra.path.data.FileGridMapReader;
import tiralabra.path.data.FileScenarioReader;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 *
 * @author Tatu
 */

public class PerformanceTest extends Application {
    
    private static long start;
    private static long end;
    
    private static ArrayList<Scenario> testScenarios;
    private static GridMap testMap;
    
    // Each scenario's all iterations are saved and median is chosen
    private double[] runtimes;
    
    private static int iterations;
    
    @Override
    public void start(Stage stage) {       
        stage.setScene(new Scene(performanceChart(), 600, 500));
        stage.show();
    }
    
    public static void main(String[] args) {
        System.out.println("Running performance tests");
        
        FileScenarioReader scenReader = new FileScenarioReader();
        FileGridMapReader mapReader = new FileGridMapReader();
        
        testMap = mapReader.getGridMap(new File(args[1]));
        testScenarios = scenReader.collectScenarios(new File(args[2]));
        iterations = Integer.valueOf(args[3]);
        
        launch(PerformanceTest.class);
    }
    
    public LineChart<Number, Number> performanceChart() {
        NumberAxis pathLength = new NumberAxis();
        pathLength.setLabel("Path length");
        
        NumberAxis runtime = new NumberAxis();
        runtime.setLabel("Algorithm runtime (ms)");
        
        LineChart<Number, Number> performanceChart = new LineChart<>(pathLength, runtime);
        performanceChart.setCreateSymbols(false);
        
        performanceChart.getData().add(getLineChartData("A*"));
        System.out.println("A* done");
        performanceChart.getData().add(getLineChartData("Dijkstra"));
        System.out.println("Dijkstra done");
        performanceChart.getData().add(getLineChartData("JPS"));
        System.out.println("JPS done");
        
        return performanceChart;
    }
    
    private XYChart.Series getLineChartData(String algoId) {
        XYChart.Series data = new XYChart.Series();
        data.setName(algoId);
        
        for (int i = 0; i < testScenarios.size(); i += (testScenarios.size() / 100)) {
            int pathLength = algorithmPerformanceTest(algoId, testScenarios.get(i));
            double runtime = runtimes[runtimes.length / 2];
            
            data.getData().add(new XYChart.Data(pathLength, runtime));
        }
        return data;
    }
    
    private int algorithmPerformanceTest(String algoId, Scenario scen) {
        runtimes = new double[iterations];
        
        Algorithm algo = null;
        for (int i = 0; i < iterations; i++) {
            // Need to reset datastructures
            algo = getAlgorithm(algoId);
            
            start = System.nanoTime();
            algo.runAlgorithm(testMap, scen);
            end = System.nanoTime();
            runtimes[i] = (end - start) / 1e7;
        }
        
        return (int) algo.distance[algo.scen.getGoalY()][algo.scen.getGoalX()];
    }
    
    private Algorithm getAlgorithm(String algoId) {
        Algorithm algo;
        if (algoId.equals("A*")) {
            algo = new AStar();
        } else if (algoId.equals("Dijkstra")) {
            algo = new Dijkstra();
        } else if (algoId.equals("JPS")) {
            algo = new JumpPointSearch();
        } else {
            algo = new BreadthFirstSearch();
        }
        return algo;
    }
}
