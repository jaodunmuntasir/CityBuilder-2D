package config;

import java.util.HashMap;
import java.util.Map;

import ui.TileType;

public class GameConfig {

    // Map each TileType to its corresponding structure size
    public static final Map<TileType, Integer> STRUCTURE_SIZES = new HashMap<>();
    static {
        STRUCTURE_SIZES.put(TileType.ROAD, 1); // 1x1 for road
        STRUCTURE_SIZES.put(TileType.RESIDENTIAL, 2); // 2x2 for residential
        STRUCTURE_SIZES.put(TileType.INDUSTRIAL, 3); // 3x3 for industrial
        STRUCTURE_SIZES.put(TileType.COMMERCIAL, 2); // 2x2 for commercial
        STRUCTURE_SIZES.put(TileType.POLICE_STATION, 2); // 2x2 for police station
        STRUCTURE_SIZES.put(TileType.FIRE_STATION, 2); // 2x2 for fire station
        STRUCTURE_SIZES.put(TileType.HOSPITAL, 2); // 2x2 for hospital
        STRUCTURE_SIZES.put(TileType.SCHOOL, 2); // 2x2 for school
        STRUCTURE_SIZES.put(TileType.POWER_PLANT, 3); // 3x3 for power plant
        STRUCTURE_SIZES.put(TileType.WATER_TOWER, 3); // 3x3 for water tower
    }

    // Define the cost of each tile type
    public static final Map<TileType, Integer> TILE_COST = new HashMap<>();
    static {
        TILE_COST.put(TileType.ROAD, 0);
        TILE_COST.put(TileType.RESIDENTIAL, 100);
        TILE_COST.put(TileType.COMMERCIAL, 200);
        TILE_COST.put(TileType.INDUSTRIAL, 250);
        TILE_COST.put(TileType.POLICE_STATION, 300);
        TILE_COST.put(TileType.FIRE_STATION, 300);
        TILE_COST.put(TileType.HOSPITAL, 800);
        TILE_COST.put(TileType.SCHOOL, 300);
        TILE_COST.put(TileType.POWER_PLANT, 900);
        TILE_COST.put(TileType.WATER_TOWER, 700);
    }
}
