package features;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CityMap;
import ui.Tile;
import ui.TileType;

import static org.junit.jupiter.api.Assertions.*;

public class SatisfactionManagerTest {

    private SatisfactionManager satisfactionManager;
    private MockCityMap cityMap;
    private UtilityManager utilityManager;
    private ServiceManager serviceManager;
    private PopulationManager populationManager;
    private TaxManager taxManager;

    @BeforeEach
    public void setUp() {
        satisfactionManager = new SatisfactionManager();
        cityMap = new MockCityMap(10, 10);
        utilityManager = new UtilityManager();
        serviceManager = new ServiceManager();
        populationManager = new PopulationManager();
        taxManager = new TaxManager(5);
    }

    @Test
    public void testInitialSatisfaction() {
        assertEquals(70, SatisfactionManager.getSatisfaction());
    }

    @Test
    public void testUpdateSatisfaction() {
        utilityManager.setPower(true);
        utilityManager.setWater(true);
        serviceManager.setPoliceCoverage(80);
        serviceManager.setFireCoverage(80);
        serviceManager.setHealthCoverage(80);
        serviceManager.setEducationCoverage(80);
        populationManager.setPopulation(1000);
        populationManager.setResidentialDemand(0);
        populationManager.setResidentialZoneCapacity(1000);
        populationManager.setResidentialZonePresent(10);

        satisfactionManager.update(cityMap, utilityManager, serviceManager, populationManager, taxManager);

        assertTrue(SatisfactionManager.getSatisfaction() >= 0 && SatisfactionManager.getSatisfaction() <= 100);
    }

    @Test
    public void testSetSatisfaction() {
        satisfactionManager.setSatisfaction(85);
        assertEquals(85, SatisfactionManager.getSatisfaction());
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
