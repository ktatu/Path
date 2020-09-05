package tiralabra.path.performance;

import java.util.ArrayList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * Comparing BFS runtime to other algorithms
 * @author Tatu
 */
public class BFSRuntime {
    private ArrayList<Scenario> scens;
    
    private AlgorithmRunner runner;
    
    public LineChart<Number, Number> shortestPathChart(GridMap map, ArrayList<Scenario> scens, int iterations, String mapName) {
        this.scens = scens;
        this.runner = new AlgorithmRunner(iterations, map);
        
        NumberAxis pathLength = new NumberAxis();
        pathLength.setLabel("Scenario num in file");
        
        NumberAxis runtime = new NumberAxis();
        runtime.setLabel("Algorithm runtime (ms)");
        
        LineChart<Number, Number> lineChart = new LineChart<>(pathLength, runtime);
        lineChart.setCreateSymbols(false);
        lineChart.setTitle("A*, Dijkstra and JPS performance comparison on " + mapName);
        
        lineChart.getData().add(getLineChartData("A*"));
        System.out.println("A* done");
        lineChart.getData().add(getLineChartData("Dijkstra"));
        System.out.println("Dijkstra done");
        lineChart.getData().add(getLineChartData("JPS"));
        System.out.println("JPS done");
        lineChart.getData().add(getLineChartData("bfs"));
        System.out.println("BFS done");
        
        return lineChart;
    }
    
    private XYChart.Series getLineChartData(String algoId) {
        XYChart.Series data = new XYChart.Series();
        data.setName(algoId);
        
        for (int i = 0; i < scens.size(); i += 10) {
            runner.algorithmPerformanceTest(algoId, scens.get(i));

            double runtime = runner.getMedianRuntime();
            
            data.getData().add(new XYChart.Data(i, runtime));
        }
        return data;
    }
}
