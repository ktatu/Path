package tiralabra.path.domain;

/**
 *
 * @author Tatu
 */
public class Scenario {
    private int startX;
    private int startY;
    
    private int goalX;
    private int goalY;
    
    private long optimalCost;
    
    public Scenario(int startX, int startY, int goalX, int goalY, long optimalCost) {
        this.startX = startX;
        this.startY = startY;
        this.goalX = goalX;
        this.goalY = goalY;
        this.optimalCost = optimalCost;
    }
    
}
