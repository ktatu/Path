package tiralabra.path.logic;

/**
 * Grid describes a single piece of terrain on the map
 * Implemented for keeping priorityQueues sorted
 * @author Tatu
 */
public class Grid implements Comparable<Grid> {
    
    private int y;
    private int x;
    private float distance;
    private float estimation;
    
    public Grid(int y, int x, float distance) {
        this.y = y;
        this.x = x;
        this.distance = distance;
        this.estimation = -1;
    }
    /**
     * Separate constructors for Dijkstra and algorithms that use heuristic (estimation)
     * @param y coordinate
     * @param x coordinate
     * @param distance from starting grid to this one
     * @param estimation heuristic, distance from this grid to goal grid if there were no obstacles
     */
    public Grid(int y, int x, float distance, float estimation) {
        this(y, x, distance);
        this.estimation = estimation;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getEstimation() {
        return estimation;
    }

    /**
     * if statement returns a comparison used by Dijkstra (no heuristic), else is for others
     * @param o comparable object
     * @return the result of comparison
     */
    
    /*
    @Override
    public int compareTo(Grid o) {
        if (estimation == -1) {
            if (this.getDistance() - o.getDistance() <= 0) {
                return -1;
            }
            return 1;
        } else {
            if ((this.getDistance() + this.getEstimation()) - (o.getDistance() + o.getEstimation()) < 0) {
                return -1;
            } else if ((this.getDistance() + this.getEstimation()) - (o.getDistance() + o.getEstimation()) == 0) {
                return 0;
            }
            return 1;
        }
    }
    */
    
    /**
     * Comparing grids. Only distance to the grid from start is taken into account when comparing pure Dijkstra's grids
     * @param o comparable object
     * @return -1 if smaller
     */
    @Override
    public int compareTo(Grid o) {
        // Check if comparison comes from a pure Dijkstra
        if (this.getEstimation() == -1 && o.getEstimation() == -1) {
            return dijkstraComparison(o);
        } else {
            return comparisonWithHeuristic(o);
        }
    }
    
    /**
     * Comparing grids for pure Dijkstra
     * @param o comparable grid
     * @return -1 if this grid is smaller than o
     */
    private int dijkstraComparison(Grid o) {
        if (this.getDistance() < o.getDistance()) {
            return -1;
        }
        return 1;
    }
    
    /**
     * Comparing grids when they include a heuristic
     * @param o comparable grid
     * @return -1 if this grid is smaller than o
     */
    private int comparisonWithHeuristic(Grid o) {
        float sumThis = this.getDistance() + this.getEstimation();
        float sumO = o.getDistance() + o.getEstimation();
            
        if (sumThis < sumO) {
            return -1;
        } else if (sumThis == sumO) {
            if (this.getEstimation() < o.getEstimation()) {
                return -1;
            } else if (this.getEstimation() > o.getEstimation()) {
                return 1;
            }
            return -1;
        }
        return 1;
    }
}
