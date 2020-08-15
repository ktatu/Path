package tiralabra.path.logic.exceptions;

/**
 * Exception thrown when user tries to run the program without submitting all required information
 * @author Tatu
 */
public class MissingUserInputException extends Exception {
    public MissingUserInputException(String message) {
        super(message);
    }
}
