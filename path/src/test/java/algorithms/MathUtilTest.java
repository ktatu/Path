package algorithms;

import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.algorithms.MathUtil;

/**
 *
 * @author Tatu
 */
public class MathUtilTest {

    @Test
    public void AbsReturnsPositiveIntWhenParamIsNegative() {
        assertEquals(1, MathUtil.abs(-1));
    }
    
    @Test
    public void AbsReturnsPositiveIntWhenParamIsPositive() {
        assertEquals(1, MathUtil.abs(1));
    }
    
    @Test
    public void MinReturnsTheNegativeIntWhenOtherParamIsPositive() {
        assertEquals(-1, MathUtil.min(-1, 1));
    }
    
    @Test
    public void MinReturnsTheNegativeIntWhenOtherParamIsZero() {
        assertEquals(-1, MathUtil.min(-1, 0));
    }
    
    @Test
    public void MinReturnsZeroWhenOtherParamIsPositive() {
        assertEquals(0, MathUtil.min(0, 1));
    }
    
    @Test
    public void MinReturnsSmallerPositive() {
        assertEquals(1, MathUtil.min(1, 2));
    }
    
    @Test
    public void MinReturnsSmallerNegative() {
        assertEquals(-2, MathUtil.min(-2, -1));
    }
}
