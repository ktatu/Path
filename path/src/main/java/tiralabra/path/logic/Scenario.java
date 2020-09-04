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
    
    /**
     * Parameterless constructor is used in PathService where actions in gui directly change the Scenario's coordinates
     */
    public Scenario() {
        startX = -1;
        startY = -1;
        goalX = -1;
        goalY = -1;
    }
    
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

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setGoalX(int goalX) {
        this.goalX = goalX;
    }

    public void setGoalY(int goalY) {
        this.goalY = goalY;
    }
}
