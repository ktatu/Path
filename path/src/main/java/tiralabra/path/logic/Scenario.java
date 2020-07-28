package tiralabra.path.logic;

/**
 *
 * @author Tatu
 */
public class Scenario {
    private int startX;
    private int startY;
    private int goalX;
    private int goalY;
    
    public Scenario(int startX, int startY, int goalX, int goalY) {
        this.startX = startX;
        this.startY = startY;
        this.goalX = goalX;
        this.goalY = goalY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getGoalX() {
        return goalX;
    }

    public int getGoalY() {
        return goalY;
    }

    @Override
    public String toString() {
        return "Start coordinates: " + startX + "," + startY + " goal coordinates: " + goalX + "," + goalY;
    }
    
    
}
