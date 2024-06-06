package features;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CityMap;
import ui.Tile;
import ui.TileType;

import static org.junit.jupiter.api.Assertions.*;

public class UtilityManagerTest {

    private UtilityManager utilityManager;
    private MockCityMap cityMap;
    private PopulationManager populationManager;

    @BeforeEach
    public void setUp() {
        utilityManager = new UtilityManager();
        cityMap = new MockCityMap(10, 10);
        populationManager = new PopulationManager();
    }

    @Test
    public void testInitialValues() {
        assertFalse(utilityManager.hasPower());
        assertFalse(utilityManager.hasWater());
        assertEquals(0, utilityManager.getPowerPlantCoverage());
        assertEquals(0, utilityManager.getWaterTowerCoverage());
        assertEquals(0, utilityManager.getUtilityCoverage());
        assertEquals(0, utilityManager.getPowerPlantPresent());
        assertEquals(0, utilityManager.getWaterTowerPresent());
    }

    @Test
    public void testSetPower() {
        utilityManager.setPower(true);
        assertTrue(utilityManager.hasPower());
    }

    @Test
    public void testSetWater() {
        utilityManager.setWater(true);
        assertTrue(utilityManager.hasWater());
    }

    @Test
    public void testCalculateUtilityCoverage() {
        cityMap.setTile(0, 0, TileType.POWER_PLANT);
        cityMap.setTile(1, 0, TileType.WATER_TOWER);
        populationManager.setPopulation(1000);

        int coverage = utilityManager.calculateUtilityCoverage(cityMap, populationManager);
        assertTrue(coverage > 0);
    }

    @Test
    public void testCalculatePowerPlantCoverage() {
        cityMap.setTile(0, 0, TileType.POWER_PLANT);
        populationManager.setPopulation(1000);

        int coverage = utilityManager.calculatePowerPlantCoverage(cityMap, 1000, 10000);
        assertTrue(coverage > 0);
    }

    @Test
    public void testCalculateWaterTowerCoverage() {
        cityMap.setTile(0, 0, TileType.WATER_TOWER);
        populationManager.setPopulation(1000);

        int coverage = utilityManager.calculateWaterTowerCoverage(cityMap, 1000, 10000);
        assertTrue(coverage > 0);
    }

    // @Test
    // public void testCalculateMaintenanceCost() {
    //     utilityManager.setPower(true);
    //     utilityManager.setWater(true);

    //     int maintenanceCost = utilityManager.calculateMaintenanceCost();
    //     assertTrue(maintenanceCost > 0);
    // }

    @Test
    public void testUpdate() {
        cityMap.setTile(0, 0, TileType.POWER_PLANT);
        cityMap.setTile(1, 0, TileType.WATER_TOWER);
        populationManager.setPopulation(1000);

        utilityManager.update(cityMap, populationManager);

        assertTrue(utilityManager.hasPower());
        assertTrue(utilityManager.hasWater());
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