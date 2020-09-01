package datastructures;

import java.util.ArrayList;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.datastructures.List;

/**
 *
 * @author Tatu
 */
public class ListTest {
    
    private List testList;
    Random r = new Random(1337);

    @Test
    public void gridsCanBeAddedToGridList() {
        testList = new List(3);
        
        testList.add(1);
        testList.add(2);
        testList.add(3);
    }
    
    @Test
    public void gridListSizeIncreasesDynamically() {
        testList = new List(2);
        
        for (int i = 0; i < 2048; i++) {
            testList.add(r.nextInt(1000000));
        }
    }
    
    @Test
    public void gridListCanBeIterated() {
        testList = new List(100);
        
        for (int i = 0; i < 1000; i++) {
            testList.add(r.nextInt());
        }
        int iterationIdx = 0;
        while (testList.canIterate()) {
            int next = testList.getNext();
            iterationIdx++;
        }
        assertEquals(1000, iterationIdx);
    }
    
    @Test
    public void iteratedGridsAreInExpectedOrder() {
        testList = new List(1000);
        ArrayList<Integer> comparison = new ArrayList<>();
        
        for (int i = 0; i < 1000; i++) {
            int num = r.nextInt(1000000);
            testList.add(num);
            comparison.add(num);
        }
        
        int idx = 0;
        while (testList.canIterate()) {
            int next = testList.getNext();
            int fromComparison = comparison.get(idx);
            assertEquals(fromComparison, next);
            idx++;
        }
    }
    
    @Test
    public void containsReturnsFalseWhenIntegerIsNotInList() {
        testList = new List(10);
        assertFalse(testList.contains(1));
        
        testList.add(10);
        assertFalse(testList.contains(1));
    }
    
    @Test
    public void containsReturnsTrueWhenIntegerIsAlreadyInList() {
        testList = new List(10);
        
        testList.add(1);
        assertTrue(testList.contains(1));
        
        // There should never be duplicate integers but no reason not to test the interaction anyway
        testList.add(1);
        assertTrue(testList.contains(1));
    }
}
