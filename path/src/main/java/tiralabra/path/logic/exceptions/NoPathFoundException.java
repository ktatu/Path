package tiralabra.path.logic.exceptions;

/**
 * Thrown by AlgorithmService when algorithm has gone through all available grids but goal has not been visited
 * @author Tatu
 */
public class NoPathFoundException extends Exception {
    public NoPathFoundException(String message) {
        super(message);
    }
}
