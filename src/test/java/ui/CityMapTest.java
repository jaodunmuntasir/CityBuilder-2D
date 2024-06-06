package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CityMapTest {

    private CityMap cityMap;
    private final int width = 10;
    private final int height = 10;

    @BeforeEach
    public void setUp() {
        cityMap = new CityMap(width, height);
    }

    @Test
    public void testInitialTiles() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                assertEquals(TileType.EMPTY, cityMap.getTile(x, y).getType());
            }
        }
    }

    @Test
    public void testGetWidth() {
        assertEquals(width, cityMap.getWidth());
    }

    @Test
    public void testGetHeight() {
        assertEquals(height, cityMap.getHeight());
    }

    @Test
    public void testSetTile() {
        cityMap.setTile(0, 0, TileType.RESIDENTIAL);
        assertEquals(TileType.RESIDENTIAL, cityMap.getTile(0, 0).getType());
    }

    @Test
    public void testClearTile() {
        cityMap.setTile(0, 0, TileType.RESIDENTIAL);
        assertEquals(TileType.RESIDENTIAL, cityMap.getTile(0, 0).getType());
        cityMap.clearTile(0, 0);
        assertEquals(TileType.EMPTY, cityMap.getTile(0, 0).getType());
    }

    @Test
    public void testClear() {
        cityMap.setTile(0, 0, TileType.RESIDENTIAL);
        cityMap.setTile(1, 1, TileType.COMMERCIAL);
        cityMap.clear();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                assertEquals(TileType.EMPTY, cityMap.getTile(x, y).getType());
            }
        }
    }

    @Test
    public void testIsWithinBounds() {
        assertTrue(cityMap.isWithinBounds(0, 0));
        assertTrue(cityMap.isWithinBounds(width - 1, height - 1));
        assertFalse(cityMap.isWithinBounds(-1, 0));
        assertFalse(cityMap.isWithinBounds(0, -1));
        assertFalse(cityMap.isWithinBounds(width, 0));
        assertFalse(cityMap.isWithinBounds(0, height));
    }
}