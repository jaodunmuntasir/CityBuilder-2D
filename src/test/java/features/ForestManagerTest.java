package features;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CityMap;
import ui.Tile;
import ui.TileType;

import static org.junit.jupiter.api.Assertions.*;

public class ForestManagerTest {

    private CityMap cityMapMock;
    private Economy economyMock;
    private ForestManager forestManager;

    @BeforeEach
    public void setUp() {
        cityMapMock = new MockCityMap((1400 / 40), (600/40));
        economyMock = new MockEconomy();
        forestManager = new ForestManager(cityMapMock, economyMock);
    }

    @Test
    public void testInitializeForests() {
        forestManager.initializeForests(5);

        assertEquals(1, ((MockCityMap) cityMapMock).forestTileSetCount);
    }

    @Test
    public void testUpdateResidentialZones() {
        PopulationManager populationManagerMock = new MockPopulationManager();
        SatisfactionManager satisfactionManagerMock = new MockSatisfactionManager();

        forestManager.updateResidentialZones(populationManagerMock, satisfactionManagerMock);

        // assertTrue(((MockPopulationManager) populationManagerMock).setPopulationCalled);
        // assertTrue(((MockSatisfactionManager) satisfactionManagerMock).setSatisfactionCalled);
    }

    @Test
    public void testApplyMaintenanceAndAgeForests() {
        forestManager.applyMaintenanceAndAgeForests();

        assertTrue(((MockEconomy) economyMock).spendCalled);
    }

    class MockCityMap extends CityMap {

        public MockCityMap(int width, int height) {
            super(width, height);
        }

        int forestTileSetCount = 0;

        @Override
        public int getWidth() {
            return 10;
        }

        @Override
        public int getHeight() {
            return 10;
        }

        @Override
        public Tile getTile(int x, int y) {
            Tile tile = new Tile(TileType.EMPTY);
            if (forestTileSetCount > 0) {
                tile = new Tile(TileType.FOREST);
            }
            return tile;
        }

        @Override
        public void setTile(int x, int y, TileType type) {
            if (type == TileType.FOREST) {
                forestTileSetCount++;
            }
        }
    }

    class MockEconomy extends Economy {
        boolean spendCalled = false;

        MockEconomy() {
            super(1000);
        }

        @Override
        public void spend(int amount) {
            spendCalled = true;
        }
    }

    class MockPopulationManager extends PopulationManager {
        boolean setPopulationCalled = false;

        @Override
        public void setPopulation(int population) {
            setPopulationCalled = true;
        }
    }

    class MockSatisfactionManager extends SatisfactionManager {
        boolean setSatisfactionCalled = false;

        @Override
        public void setSatisfaction(int satisfaction) {
            setSatisfactionCalled = true;
        }
    }
}