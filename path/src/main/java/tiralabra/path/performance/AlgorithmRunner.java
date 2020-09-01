package tiralabra.path.performance;

import tiralabra.path.algorithms.AStar;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.algorithms.BreadthFirstSearch;
import tiralabra.path.algorithms.Dijkstra;
import tiralabra.path.algorithms.JumpPointSearch;
import tiralabra.path.logic.GridMap;
import tiralabra.path.logic.Scenario;

/**
 * Running algorithms and collecting data for performance tests
 * @author Tatu
 */
public class AlgorithmRunner {
    
    private final int iterations;
    
    private double[] runtimes;
    
    private float distance;
    
    GridMap map;
    
    public AlgorithmRunner(int iterations, GridMap map) {
        this.iterations = iterations;
        this.map = map;
    }
    
    public void algorithmPerformanceTest(String algoId, Scenario scen) {
        this.runtimes = new double[iterations];
        
        runtimes = new double[iterations];
        
        Algorithm algo = null;
        for (int i = 0; i < iterations; i++) {
            // Need to reset datastructures
            algo = getAlgorithm(algoId);
            
            long start = System.nanoTime();
            algo.runAlgorithm(map, scen);
            long end = System.nanoTime();
            
            runtimes[i] = (end - start) / 1e7;
        }
        distance = algo.getPathLength();
    }
    
    public float getDistance() {
        return this.distance;
    }
    
    public double getMedianRuntime() {
        return runtimes[runtimes.length / 2];
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
