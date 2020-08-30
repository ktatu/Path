package tiralabra.path.performance;

import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;
import tiralabra.path.data.FileGridMapReader;
import tiralabra.path.data.FileScenarioReader;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 *
 * @author Tatu
 */

public class PerformanceTest extends Application {
    
    private static String[] arguments;
    
    @Override
    public void start(Stage stage) {       
        
        System.out.println("args size: " + arguments.length);
        
        if (arguments[0].equals("shortest_path")) {
            stage.setScene(new Scene(shortestPathSpeedTest(), 600, 500));
            stage.show();
        }
        else if (arguments[0].equals("bfs_path")) {
            stage.setScene(new Scene(bfsPathLengthTest(), 600, 500));
            stage.show();
        }
        else if (arguments[0].equals("arrays_copy")) {
            arrayCopyTest();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Running performance test");
        
        arguments = args;
        launch(PerformanceTest.class);
    }
    
    private LineChart shortestPathSpeedTest() {
        String mapFileName = arguments[1];
        String scenFileName = arguments[2];
        int iterations = Integer.valueOf(arguments[3]);
        
        ShortestPathRuntime pathTest = new ShortestPathRuntime();
        
        return pathTest.shortestPathChart(getMap(mapFileName), getScenarios(scenFileName), iterations, mapFileName);
    }
    
    private BarChart bfsPathLengthTest() {
        if ((arguments.length - 1) % 2 != 0) {
            System.out.println("Cannot run performance test: there should be an equal amount of map and scen files");
            return null;
        }
        
        BFSPathLength bfsPathTest = new BFSPathLength();
        
        // Last map file == args.get(argDivider)
        int argDivider = (arguments.length - 1) / 2;
        
        ArrayList<GridMap> maps = new ArrayList<>();
        ArrayList<String> mapNames = new ArrayList<>();
        for (int i = 1; i <= argDivider; i++) {
            maps.add(getMap(arguments[i]));
            mapNames.add(arguments[i]);
        }
        
        ArrayList<ArrayList<Scenario>> mapScens = new ArrayList<>();
        for (int i = argDivider + 1; i < arguments.length; i++) {
            mapScens.add(getScenarios(arguments[i]));
        }
        
        return bfsPathTest.pathLengthChart(maps, mapScens, mapNames);
    }
    
    private void arrayCopyTest() {
        ArrayCopy.arrayCopyPerformanceTest();
    }
    
    private GridMap getMap(String fileName) {
        FileGridMapReader mapReader = new FileGridMapReader();
        
        return mapReader.getGridMap(new File(fileName));
    }
    
    private ArrayList<Scenario> getScenarios(String fileName) {
        FileScenarioReader scenReader = new FileScenarioReader();
        
        return scenReader.collectScenarios(new File(fileName));
    }
}
