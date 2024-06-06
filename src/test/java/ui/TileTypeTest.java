package ui;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

public class TileTypeTest {

    @Test
    public void testTileTypeAttributes() {
        TileType tile = TileType.RESIDENTIAL;

        assertEquals(Color.GREEN, tile.getDrawColor());
        assertNotNull(tile.getImage());
    }

    @Test
    public void testLoadAllImages() {
        for (TileType tileType : TileType.values()) {
            assertNotNull(tileType.getImage(), "Image should be loaded for " + tileType.name());
        }
    }

    @Test
    public void testGetDrawColor() {
        assertEquals(Color.LIGHT_GRAY, TileType.EMPTY.getDrawColor());
        assertEquals(Color.DARK_GRAY, TileType.ROAD.getDrawColor());
        assertEquals(Color.GREEN, TileType.RESIDENTIAL.getDrawColor());
        assertEquals(Color.BLUE, TileType.COMMERCIAL.getDrawColor());
        assertEquals(Color.ORANGE, TileType.INDUSTRIAL.getDrawColor());
        assertEquals(Color.BLUE, TileType.POLICE_STATION.getDrawColor());
        assertEquals(Color.RED, TileType.FIRE_STATION.getDrawColor());
        assertEquals(Color.WHITE, TileType.HOSPITAL.getDrawColor());
        assertEquals(Color.YELLOW, TileType.SCHOOL.getDrawColor());
        assertEquals(Color.BLACK, TileType.POWER_PLANT.getDrawColor());
        assertEquals(Color.CYAN, TileType.WATER_TOWER.getDrawColor());
        assertEquals(Color.GRAY, TileType.DEMOLISH.getDrawColor());
        assertEquals(Color.RED, TileType.FIRE.getDrawColor());
        assertEquals(Color.RED, TileType.METEOR.getDrawColor());
        assertEquals(Color.RED, TileType.CHEMICAL.getDrawColor());
        assertEquals(Color.RED, TileType.GODZILLA.getDrawColor());
        assertEquals(Color.GREEN, TileType.FOREST.getDrawColor());
    }

    @Test
    public void testGetImage() {
        assertNotNull(TileType.EMPTY.getImage());
        assertNotNull(TileType.ROAD.getImage());
        assertNotNull(TileType.RESIDENTIAL.getImage());
        assertNotNull(TileType.COMMERCIAL.getImage());
        assertNotNull(TileType.INDUSTRIAL.getImage());
        assertNotNull(TileType.POLICE_STATION.getImage());
        assertNotNull(TileType.FIRE_STATION.getImage());
        assertNotNull(TileType.HOSPITAL.getImage());
        assertNotNull(TileType.SCHOOL.getImage());
        assertNotNull(TileType.POWER_PLANT.getImage());
        assertNotNull(TileType.WATER_TOWER.getImage());
        assertNotNull(TileType.DEMOLISH.getImage());
        assertNotNull(TileType.FIRE.getImage());
        assertNotNull(TileType.METEOR.getImage());
        assertNotNull(TileType.CHEMICAL.getImage());
        assertNotNull(TileType.GODZILLA.getImage());
        assertNotNull(TileType.FOREST.getImage());
    }
}
