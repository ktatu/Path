package tiralabra.path.performance;

import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;
import tiralabra.path.io.FileGridMapReader;
import tiralabra.path.io.FileScenarioReader;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * Performance testing of pathfinding algorithms
 * @author Tatu
 */
public class PerformanceTest extends Application {
    
    private static String[] arguments;
    
    @Override
    public void start(Stage stage) {       
        
        System.out.println("args size: " + arguments.length);
        
        if (arguments[0].equals("no_bfs_runtime")) {
            stage.setScene(new Scene(shortestPathSpeedTest(), 600, 500));
            stage.show();
        } else if (arguments[0].equals("bfs_path")) {
            stage.setScene(new Scene(bfsPathLengthTest(), 600, 500));
            stage.show();
        } else if (arguments[0].equals("bfs_runtime")) {
            stage.setScene(new Scene(bfsPathSpeedTest(), 600, 500));
            stage.show();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Running performance test");
        
        arguments = args;
        launch(PerformanceTest.class);
    }
    
    private LineChart shortestPathSpeedTest() {
        String mapFileName = arguments[1];
        int iterations = Integer.valueOf(arguments[2]);
        
        ShortestPathRuntime pathTest = new ShortestPathRuntime();
        
        return pathTest.shortestPathChart(getMap(mapFileName), getScenarios(mapFileName), iterations, mapFileName);
    }
    
    private LineChart bfsPathSpeedTest() {
        String mapFileName = arguments[1];
        int iterations = Integer.valueOf(arguments[2]);
        
        BFSRuntime pathTest = new BFSRuntime();
        
        return pathTest.shortestPathChart(getMap(mapFileName), getScenarios(mapFileName), iterations, mapFileName);
    }
    
    private BarChart bfsPathLengthTest() {        
        BFSPathLength bfsPathTest = new BFSPathLength();
        
        ArrayList<GridMap> maps = new ArrayList<>();
        ArrayList<String> mapNames = new ArrayList<>();
        for (int i = 1; i < arguments.length; i++) {
            maps.add(getMap(arguments[i]));
            mapNames.add(arguments[i]);
        }
        
        ArrayList<ArrayList<Scenario>> mapScens = new ArrayList<>();
        for (int i = 1; i < arguments.length; i++) {
            mapScens.add(getScenarios(arguments[i]));
        }
        
        return bfsPathTest.pathLengthChart(maps, mapScens, mapNames);
    }
    
    private GridMap getMap(String fileName) {
        FileGridMapReader mapReader = new FileGridMapReader();
        
        return mapReader.getGridMap(new File(fileName));
    }
    
    // Scenario file name is derived from map name so that it's not needed in arguments
    private ArrayList<Scenario> getScenarios(String mapFileName) {
        FileScenarioReader scenReader = new FileScenarioReader();
        
        return scenReader.collectScenarios(new File(mapFileName + ".scen"));
    }
}
