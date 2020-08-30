package tiralabra.path.performance;

/**
 * Datastructures GridList and PrioQueue utilize System.arraycopy
 * Benchmarking performance difference between loop copying and System.arraycopy as instructed in course FAQ
 * @author Tatu
 */
public class ArrayCopy {
    
    private static long start;
    private static long end;
    
    static void arrayCopyPerformanceTest() {
        int[] testList;
        for (int i = 1; i <= 5; i++){
            testList = new int[i * 100000];
            sysCopyExpand(testList);
            loopCopyExpand(testList);
            System.out.println("");
        }
    }
    
    private static void sysCopyExpand(int[] list) {
        int[] newList = new int[list.length * 2];
        
        start = System.nanoTime();
        System.arraycopy(list, 0, newList, 0, list.length - 1);
        end = System.nanoTime();
        
        System.out.println("Copying with System.arrayCopy took " + (end - start) / 1e7 + " milliseconds. Copied list's length was " + list.length);
    }
    
    private static void loopCopyExpand(int[] list) {
        int[] newList = new int[list.length * 2];
        
        start = System.nanoTime();
        for (int i = 0; i < list.length; i++) {
            newList[i] = list[i];
        }
        end = System.nanoTime();
        
        System.out.println("Copying with a loop took " + (end - start) / 1e7 + " milliseconds. Copied list's length was " + list.length);
    }
}
