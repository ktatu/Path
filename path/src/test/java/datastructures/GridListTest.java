package datastructures;

import java.util.ArrayList;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.path.datastructures.GridList;

/**
 *
 * @author Tatu
 */
public class GridListTest {
    
    private GridList testList;
    Random r = new Random(1337);

    @Test
    public void gridsCanBeAddedToGridList() {
        testList = new GridList(3);
        
        testList.add(1);
        testList.add(2);
        testList.add(3);
    }
    
    @Test
    public void gridListSizeIncreasesDynamically() {
        testList = new GridList(2);
        
        for (int i = 0; i < 2048; i++) {
            testList.add(r.nextInt(1000000));
        }
    }
    
    @Test
    public void gridListCanBeIterated() {
        testList = new GridList(100);
        
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
        testList = new GridList(1000);
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
}
