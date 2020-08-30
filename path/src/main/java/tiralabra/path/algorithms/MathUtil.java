package tiralabra.path.algorithms;

/**
 * Replacements for Math.abs and Math.min
 * @author Tatu
 */
public class MathUtil {
    
    public static int abs(int num) {
        return num > 0 ? num : num * -1;
    }
    
    public static int min(int num1, int num2) {
        return num1 < num2 ? num1 : num2;
    }
}
