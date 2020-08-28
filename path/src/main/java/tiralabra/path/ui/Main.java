package tiralabra.path.ui;


import tiralabra.path.performance.PerformanceTest;
/**
 * This class is for using jar. More info here (in Finnish): https://github.com/mluukkai/ohjelmistotekniikka-kevat-2020/blob/master/web/maven.md
 * @author Tatu
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("performance")) {
            PerformanceTest.main(args);
        } else {
            Gui.main(args);
        }
    }
}