package tiralabra.path.logic.exceptions;

/**
 * Thrown by ScenarioValidation when the path is considered unrunnable
 * @author Tatu
 */
public class InvalidScenarioException extends Exception {
    
    public InvalidScenarioException(String message) {
        super(message);
    }
}
