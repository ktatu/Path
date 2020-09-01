package algorithms;

import static algorithms.AlgorithmSetup.sqrtTwo;
import tiralabra.path.algorithms.Algorithm;
import tiralabra.path.logic.Scenario;

/**
 * Contains data and methods used by Dijkstra-variation test classes (dijkstra, a*)
 * @author Tatu
 */
public class TestUtils {
    
    public float[] expectedResults = {sqrtTwo, 2*sqrtTwo, 2, sqrtTwo};
    
    public float diagonalDistance(int startY, int startX, int goalY, int goalX) {
        int diagonalMoves = Math.min(Math.abs(goalY - startY), Math.abs(goalX - startX));
        int horAndVerMoves = Math.abs(Math.abs(goalY - startY) - Math.abs(goalX - startX));
        return diagonalMoves * sqrtTwo + horAndVerMoves;
    }
    
    public boolean correctDistance(float algorithmCalculation, Scenario scen) {
        if (Math.abs(algorithmCalculation - diagonalDistance(scen.getStartY(), scen.getStartX(), scen.getGoalY(), scen.getGoalX())) > 0.1) {
            return false;
        }
        return true;
    }
    
    public float resultDistance(Algorithm algo, Scenario scen) {
        //return algo.getDistanceFromGoal(scen.getGoalY(), scen.getGoalX());
        return algo.getPathLength();
    }
}
