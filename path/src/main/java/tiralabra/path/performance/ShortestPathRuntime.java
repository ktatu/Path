package tiralabra.path.performance;

import java.util.ArrayList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * Performance test comparing runtimes of Dijkstra, AStar and Jump Point Search in a linechart
 * @author Tatu
 */
public class ShortestPathRuntime {
    
    private ArrayList<Scenario> scens;
    
    private AlgorithmRunner runner;
    
    public LineChart<Number, Number> shortestPathChart(GridMap map, ArrayList<Scenario> scens, int iterations, String mapName) {
        this.scens = scens;
        this.runner = new AlgorithmRunner(iterations, map);
        
        NumberAxis pathLength = new NumberAxis();
        pathLength.setLabel("Path length");
        
        NumberAxis runtime = new NumberAxis();
        runtime.setLabel("Algorithm runtime (ms)");
        
        LineChart<Number, Number> lineChart = new LineChart<>(pathLength, runtime);
        lineChart.setTitle("A*, Dijkstra and JPS performance comparison on " + mapName);
        lineChart.setCreateSymbols(false);
        
        lineChart.getData().add(getLineChartData("A*"));
        System.out.println("A* done");
        lineChart.getData().add(getLineChartData("Dijkstra"));
        System.out.println("Dijkstra done");
        lineChart.getData().add(getLineChartData("JPS"));
        System.out.println("JPS done");
        
        return lineChart;
    }
    
    private XYChart.Series getLineChartData(String algoId) {
        XYChart.Series data = new XYChart.Series();
        data.setName(algoId);
        
        for (int i = 0; i < scens.size(); i += 10) {
            runner.algorithmPerformanceTest(algoId, scens.get(i));
            
            float pathLength = runner.getDistance();
            double runtime = runner.getMedianRuntime();
            
            data.getData().add(new XYChart.Data(pathLength, runtime));
        }
        return data;
    }

}
