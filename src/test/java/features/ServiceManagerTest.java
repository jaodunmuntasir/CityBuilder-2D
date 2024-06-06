package features;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CityMap;
import ui.Tile;
import ui.TileType;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceManagerTest {

    private ServiceManager serviceManager;
    private MockCityMap cityMap;
    private static final int POPULATION = 2000;
    private PopulationManager populationManager;

    @BeforeEach
    public void setUp() {
        serviceManager = new ServiceManager();
        cityMap = new MockCityMap(10, 10);
        populationManager = new PopulationManager();
        populationManager.setPopulation(POPULATION);
    }

    @Test
    public void testInitialValues() {
        assertEquals(0, serviceManager.getPoliceCoverage());
        assertEquals(0, serviceManager.getFireCoverage());
        assertEquals(0, serviceManager.getHealthCoverage());
        assertEquals(0, serviceManager.getEducationCoverage());
        assertEquals(0, serviceManager.getPolicePresent());
        assertEquals(0, serviceManager.getFirePresent());
        assertEquals(0, serviceManager.getHealthPresent());
        assertEquals(0, serviceManager.getEducationPresent());
    }

    @Test
    public void testCalculatePoliceCoverage() {
        cityMap.setTile(0, 0, TileType.POLICE_STATION);
        cityMap.setTile(0, 1, TileType.POLICE_STATION);
        cityMap.setTile(1, 0, TileType.POLICE_STATION);
        cityMap.setTile(1, 1, TileType.POLICE_STATION);
        serviceManager.calculatePoliceCoverage(cityMap, TileType.POLICE_STATION, POPULATION);
        assertTrue(serviceManager.getPoliceCoverage() > 0);
    }

    @Test
    public void testCalculateFireCoverage() {
        cityMap.setTile(0, 0, TileType.FIRE_STATION);
        cityMap.setTile(0, 1, TileType.FIRE_STATION);
        cityMap.setTile(1, 0, TileType.FIRE_STATION);
        cityMap.setTile(1, 1, TileType.FIRE_STATION);
        serviceManager.calculateFireCoverage(cityMap, TileType.FIRE_STATION, POPULATION);
        assertTrue(serviceManager.getFireCoverage() > 0);
    }

    @Test
    public void testCalculateHealthCoverage() {
        cityMap.setTile(0, 0, TileType.HOSPITAL);
        cityMap.setTile(0, 1, TileType.HOSPITAL);
        cityMap.setTile(1, 0, TileType.HOSPITAL);
        cityMap.setTile(1, 1, TileType.HOSPITAL);
        serviceManager.calculateHealthCoverage(cityMap, TileType.HOSPITAL, POPULATION);
        assertTrue(serviceManager.getHealthCoverage() > 0);
    }

    @Test
    public void testCalculateEducationCoverage() {
        cityMap.setTile(0, 0, TileType.SCHOOL);
        cityMap.setTile(0, 1, TileType.SCHOOL);
        cityMap.setTile(1, 0, TileType.SCHOOL);
        cityMap.setTile(1, 1, TileType.SCHOOL);
        serviceManager.calculateEducationCoverage(cityMap, TileType.SCHOOL, POPULATION);
        assertTrue(serviceManager.getEducationCoverage() > 0);
    }

    @Test
    public void testUpdateServices() {
        cityMap.setTile(0, 0, TileType.POLICE_STATION);
        cityMap.setTile(0, 1, TileType.POLICE_STATION);
        cityMap.setTile(1, 0, TileType.POLICE_STATION);
        cityMap.setTile(1, 1, TileType.POLICE_STATION);

        cityMap.setTile(2, 2, TileType.FIRE_STATION);
        cityMap.setTile(2, 3, TileType.FIRE_STATION);
        cityMap.setTile(3, 2, TileType.FIRE_STATION);
        cityMap.setTile(3, 3, TileType.FIRE_STATION);

        cityMap.setTile(4, 4, TileType.HOSPITAL);
        cityMap.setTile(4, 5, TileType.HOSPITAL);
        cityMap.setTile(5, 4, TileType.HOSPITAL);
        cityMap.setTile(5, 5, TileType.HOSPITAL);

        cityMap.setTile(6, 6, TileType.SCHOOL);
        cityMap.setTile(6, 7, TileType.SCHOOL);
        cityMap.setTile(7, 6, TileType.SCHOOL);
        cityMap.setTile(7, 7, TileType.SCHOOL);

        serviceManager.update(cityMap, populationManager);

        assertTrue(serviceManager.getPoliceCoverage() > 0);
        assertTrue(serviceManager.getFireCoverage() > 0);
        assertTrue(serviceManager.getHealthCoverage() > 0);
        assertTrue(serviceManager.getEducationCoverage() > 0);
    }

    class MockCityMap extends CityMap {
        private Tile[][] tiles;

        MockCityMap(int width, int height) {
            super(width, height);
            tiles = new Tile[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    tiles[x][y] = new Tile(TileType.EMPTY);
                }
            }
        }

        @Override
        public Tile getTile(int x, int y) {
            return tiles[x][y];
        }

        @Override
        public void setTile(int x, int y, TileType type) {
            tiles[x][y].setType(type);
        }
    }
}
