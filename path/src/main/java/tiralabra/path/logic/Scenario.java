package tiralabra.path.logic;

/**
 * Scenario gives an algorithm start- and endpoints on a map
 * @author Tatu
 */
public class Scenario {
    private int startX;
    private int startY;
    private int goalX;
    private int goalY;
    
    public Scenario(int startY, int startX, int goalY, int goalX) {
        this.startY = startY;
        this.startX = startX;
        this.goalY = goalY;
        this.goalX = goalX;
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
