package config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CityMap;
import ui.Tile;
import ui.TileType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TileCounterTest {

    private CityMap cityMapMock;

    @BeforeEach
    public void setUp() {
        cityMapMock = mock(CityMap.class);
    }

    @Test
    public void testCountTiles() {
        when(cityMapMock.getHeight()).thenReturn(3);
        when(cityMapMock.getWidth()).thenReturn(3);

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                Tile tileMock = mock(Tile.class);
                when(cityMapMock.getTile(x, y)).thenReturn(tileMock);
                when(tileMock.getType()).thenReturn(TileType.RESIDENTIAL);
            }
        }

        Map<TileType, Integer> tileCount = TileCounter.countTiles(cityMapMock);
        assertEquals(9, tileCount.get(TileType.RESIDENTIAL));
    }

    @Test
    public void testCountSpecificTileType() {
        when(cityMapMock.getHeight()).thenReturn(3);
        when(cityMapMock.getWidth()).thenReturn(3);

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                Tile tileMock = mock(Tile.class);
                when(cityMapMock.getTile(x, y)).thenReturn(tileMock);
                when(tileMock.getType()).thenReturn(TileType.RESIDENTIAL);
            }
        }

        int count = TileCounter.countSpecificTileType(cityMapMock, TileType.RESIDENTIAL);
        assertEquals(2, count); // Adjusted by 2x2 structure size
    }

    @Test
    public void testTotalUsableTileCount() {
        when(cityMapMock.getHeight()).thenReturn(3);
        when(cityMapMock.getWidth()).thenReturn(3);

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                Tile tileMock = mock(Tile.class);
                when(cityMapMock.getTile(x, y)).thenReturn(tileMock);
                when(tileMock.getType()).thenReturn(TileType.RESIDENTIAL);
            }
        }

        int count = TileCounter.totalUsableTileCount(cityMapMock);
        assertEquals(2, count); // Adjusted by 2x2 structure size
    }
}