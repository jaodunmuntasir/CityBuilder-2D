package config;

import org.junit.jupiter.api.Test;
import ui.TileType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GameConfigTest {

    @Test
    public void testStructureSizes() {
        Map<TileType, Integer> structureSizes = GameConfig.STRUCTURE_SIZES;

        // Debugging output to verify actual map values
        structureSizes.forEach((key, value) -> System.out.println(key + ": " + value));

        assertEquals(1, structureSizes.get(TileType.ROAD));
        assertEquals(2, structureSizes.get(TileType.RESIDENTIAL));
        assertEquals(3, structureSizes.get(TileType.INDUSTRIAL));
        assertEquals(2, structureSizes.get(TileType.COMMERCIAL));
        assertEquals(2, structureSizes.get(TileType.POLICE_STATION));
        assertEquals(2, structureSizes.get(TileType.FIRE_STATION));
        // assertEquals(2, structureSizes.get(TileType.HOSPITAL));
        assertEquals(2, structureSizes.get(TileType.SCHOOL));
        assertEquals(3, structureSizes.get(TileType.POWER_PLANT));
        assertEquals(3, structureSizes.get(TileType.WATER_TOWER));
    }

    @Test
    public void testTileCost() {
        Map<TileType, Integer> tileCost = GameConfig.TILE_COST;

        assertEquals(0, tileCost.get(TileType.ROAD));
        assertEquals(100, tileCost.get(TileType.RESIDENTIAL));
        assertEquals(200, tileCost.get(TileType.COMMERCIAL));
        assertEquals(250, tileCost.get(TileType.INDUSTRIAL));
        assertEquals(300, tileCost.get(TileType.POLICE_STATION));
        assertEquals(300, tileCost.get(TileType.FIRE_STATION));
        assertEquals(800, tileCost.get(TileType.HOSPITAL));
        assertEquals(300, tileCost.get(TileType.SCHOOL));
        assertEquals(900, tileCost.get(TileType.POWER_PLANT));
        assertEquals(700, tileCost.get(TileType.WATER_TOWER));
    }
}