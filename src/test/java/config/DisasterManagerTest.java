package config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CityMap;
import ui.Tile;
import ui.TileType;
import features.Economy;
import features.PopulationManager;
import features.ServiceManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DisasterManagerTest {

    private DisasterManager disasterManager;
    private CityMap cityMapMock;
    private ServiceManager serviceManagerMock;
    private PopulationManager populationManagerMock;
    private DemolishManager demolishManagerMock;
    private Economy economyMock;

    @BeforeEach
    public void setUp() {
        cityMapMock = mock(CityMap.class);
        serviceManagerMock = mock(ServiceManager.class);
        populationManagerMock = mock(PopulationManager.class);
        demolishManagerMock = mock(DemolishManager.class);
        economyMock = mock(Economy.class);
        disasterManager = new DisasterManager(cityMapMock);
    }

    @Test
    public void testGetDisasterType() {
        disasterManager.triggerDisaster(40, serviceManagerMock, populationManagerMock, demolishManagerMock, economyMock);
        TileType disasterType = disasterManager.getDisasterType();
        assertTrue(isValidDisasterType(disasterType));
    }

    @Test
    public void testTriggerDisaster() {
        int tileSize = 40;
        when(cityMapMock.getWidth()).thenReturn(10);
        when(cityMapMock.getHeight()).thenReturn(10);

        Tile tileMock = mock(Tile.class);
        when(cityMapMock.getTile(anyInt(), anyInt())).thenReturn(tileMock);
        when(tileMock.getType()).thenReturn(TileType.RESIDENTIAL);

        disasterManager.triggerDisaster(tileSize, serviceManagerMock, populationManagerMock, demolishManagerMock, economyMock);

        verify(cityMapMock, atLeastOnce()).setTile(anyInt(), anyInt(), any(TileType.class));
    }

    @Test
    public void testDisaster() {
        // int tileSize = 40;
        when(cityMapMock.getWidth()).thenReturn(10);
        when(cityMapMock.getHeight()).thenReturn(10);

        // disasterManager.disaster(tileSize, serviceManagerMock, populationManagerMock, demolishManagerMock, economyMock);

        // verify(cityMapMock, atLeastOnce()).setTile(anyInt(), anyInt(), any(TileType.class));
    }

    private boolean isValidDisasterType(TileType type) {
        for (TileType disaster : DisasterManager.disasters) {
            if (disaster == type) {
                return true;
            }
        }
        return false;
    }
}