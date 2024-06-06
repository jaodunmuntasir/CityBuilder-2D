package config;

import features.Economy;
import features.PopulationManager;
import features.ServiceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CityMap;
import ui.Tile;
import ui.TileType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DemolishManagerTest {

    private DemolishManager demolishManager;
    private CityMap cityMapMock;
    private Economy economyMock;
    private ServiceManager serviceManagerMock;
    private PopulationManager populationManagerMock;

    @BeforeEach
    public void setUp() {
        demolishManager = new DemolishManager();
        cityMapMock = mock(CityMap.class);
        economyMock = mock(Economy.class);
        serviceManagerMock = mock(ServiceManager.class);
        populationManagerMock = mock(PopulationManager.class);
    }

    @Test
    public void testSetTypeToDemolish() {
        demolishManager.setTypeToDemolish(TileType.RESIDENTIAL);
        assertEquals(TileType.RESIDENTIAL, demolishManager.getTypeToDemolish());
    }

    @Test
    public void testDemolishSetup() {
        int x = 80;
        int y = 80;
        int TILE_SIZE = 40;

        Tile tileMock = mock(Tile.class);
        when(cityMapMock.getTile(2, 2)).thenReturn(tileMock);
        when(tileMock.getType()).thenReturn(TileType.COMMERCIAL);

        TileType result = demolishManager.demolishSetup(x, y, TILE_SIZE, cityMapMock);

        assertEquals(TileType.COMMERCIAL, result);
        assertEquals(TileType.COMMERCIAL, demolishManager.getTypeToDemolish());
    }

    @Test
    public void testDemolishProcess() {
        // Setup mocks and initial state
        TileType tileType = TileType.HOSPITAL;
        demolishManager.setTypeToDemolish(tileType);

        Tile tileMock = mock(Tile.class);
        when(cityMapMock.getTile(anyInt(), anyInt())).thenReturn(tileMock);
        when(tileMock.getType()).thenReturn(tileType);
        GameConfig.STRUCTURE_SIZES.put(tileType, 1);

        demolishManager.demolishProcess(cityMapMock, economyMock, serviceManagerMock, populationManagerMock);

        // Verify interactions and state changes
        // verify(cityMapMock, times(1)).setTile(anyInt(), anyInt(), eq(TileType.EMPTY));
        // verify(serviceManagerMock, times(1)).setHealthPresent(anyInt());
        // verify(serviceManagerMock, times(1)).setHealthCoverage(anyInt());
    }

    @Test
    public void testRefund() {
        demolishManager.refund(economyMock);
        verify(economyMock, times(1)).earn(anyInt());
    }
}