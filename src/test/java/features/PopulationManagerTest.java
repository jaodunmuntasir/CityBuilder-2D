package features;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ui.CityMap;
import ui.Tile;
import ui.TileType;

import static org.junit.jupiter.api.Assertions.*;

public class PopulationManagerTest {

    private PopulationManager populationManager;
    private MockCityMap cityMap;
    private Economy economy;
    private UtilityManager utilityManager;
    private ServiceManager serviceManager;
    private TaxManager taxManager;
    private SatisfactionManager satisfactionManager;

    @BeforeEach
    public void setUp() {
        populationManager = new PopulationManager();
        cityMap = new MockCityMap(10, 10);
        economy = new Economy(10000);
        utilityManager = new UtilityManager();
        serviceManager = new ServiceManager();
        taxManager = new TaxManager(5);
        satisfactionManager = new SatisfactionManager();
    }

    @Test
    public void testInitialPopulation() {
        assertEquals(0, populationManager.getPopulation());
    }

    @Test
    public void testUpdate() {
        cityMap.setTile(0, 0, TileType.RESIDENTIAL);
        cityMap.setTile(1, 0, TileType.COMMERCIAL);
        cityMap.setTile(2, 0, TileType.INDUSTRIAL);

        utilityManager.setPower(true);
        utilityManager.setWater(true);

        populationManager.update(cityMap, economy, utilityManager, serviceManager, taxManager, satisfactionManager);

        // assertTrue(populationManager.getPopulation() > 0);
        assertTrue(populationManager.getResidentialDemand() >= 0);
        assertTrue(populationManager.getCommercialDemand() >= 0);
        assertTrue(populationManager.getIndustrialDemand() >= 0);
    }

    @Test
    public void testCalculateResidentialDemand() {
        cityMap.setTile(0, 0, TileType.RESIDENTIAL);
        populationManager.setPopulation(1000);

        int demand = populationManager.calculateResidentialDemand(cityMap, 1000, TileType.RESIDENTIAL);
        assertTrue(demand >= 0);
    }

    @Test
    public void testCalculateCommercialDemand() {
        cityMap.setTile(0, 0, TileType.COMMERCIAL);
        populationManager.setPopulation(1000);

        int demand = populationManager.calculateCommercialDemand(cityMap, 1000, TileType.COMMERCIAL);
        assertTrue(demand >= 0);
    }

    @Test
    public void testCalculateIndustrialDemand() {
        cityMap.setTile(0, 0, TileType.INDUSTRIAL);
        populationManager.setPopulation(1000);

        int demand = populationManager.calculateIndustrialDemand(cityMap, 1000, TileType.INDUSTRIAL);
        assertTrue(demand >= 0);
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
