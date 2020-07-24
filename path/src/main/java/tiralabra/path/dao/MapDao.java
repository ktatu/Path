package tiralabra.path.dao;

import java.util.HashSet;

/**
 *
 * @author Tatu
 */
public interface MapDao {
    void getTerrainTypes();
    int[][] createMap(Object object);
}
