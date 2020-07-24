package tiralabra.path.ui;

import java.util.ArrayList;
import tiralabra.path.dao.FileIO;

/**
 *
 * @author Tatu
 */
public class ui {
    
    public static void main(String[] args) {
        FileIO fileIO = new FileIO("src/battleground.map");
        ArrayList<String> list = fileIO.getFileAsList();
        System.out.println(list.size());
    }
}
