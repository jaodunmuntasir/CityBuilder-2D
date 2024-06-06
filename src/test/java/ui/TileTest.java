package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    private Tile tile;

    @BeforeEach
    public void setUp() {
        tile = new Tile(TileType.EMPTY);
    }

    @Test
    public void testInitialType() {
        assertEquals(TileType.EMPTY, tile.getType());
    }

    @Test
    public void testSetType() {
        tile.setType(TileType.RESIDENTIAL);
        assertEquals(TileType.RESIDENTIAL, tile.getType());
    }

    @Test
    public void testAgeNonForest() {
        tile.setType(TileType.RESIDENTIAL);
        tile.age();
        assertEquals(TileType.RESIDENTIAL, tile.getType());
    }

    @Test
    public void testAgeForest() {
        tile.setType(TileType.FOREST);
        for (int i = 0; i < 3650; i++) {
            tile.age();
        }
        assertEquals(TileType.FOREST, tile.getType());
    }

    @Test
    public void testAgeForestBeforeMaturity() {
        tile.setType(TileType.FOREST);
        for (int i = 0; i < 3649; i++) {
            tile.age();
        }
        assertEquals(TileType.FOREST, tile.getType());
    }
}