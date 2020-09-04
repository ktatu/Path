package tiralabra.path.algorithms;

/**
 * Replacements for Math.abs and Math.min
 * @author Tatu
 */
public class MathUtil {
    
    /**
     * Absolute value of an integer
     * @param num an integer
     * @return the absolute value of the parameter
     */
    public static int abs(int num) {
        return num > 0 ? num : num * -1;
    }
    
    /**
     * Comparing two integers and returning the smaller
     * @param num1
     * @param num2
     * @return smaller integer
     */
    public static int min(int num1, int num2) {
        return num1 < num2 ? num1 : num2;
    }
}
