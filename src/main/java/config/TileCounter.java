package config;

import java.util.HashMap;
import java.util.Map;

import ui.CityMap;
import ui.TileType;

public class TileCounter {

    public static Map<TileType, Integer> countTiles(CityMap cityMap) {

        Map<TileType, Integer> tileCount = new HashMap<>();

        // Initialize counts
        for (TileType type : TileType.values()) {
            tileCount.put(type, 0);
        }

        // Count each tile type
        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {
                TileType type = cityMap.getTile(x, y).getType();
                tileCount.put(type, tileCount.getOrDefault(type, 0) + 1);
            }
        }

        return tileCount;
    }

    // Returns the count of specific tile type adjusted by its structure size.
    public static int countSpecificTileType(CityMap cityMap, TileType tileType) {

        int count = 0;

        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {
                if (cityMap.getTile(x, y).getType() == tileType) {
                    count++;
                }
            }
        }

        Integer size = GameConfig.STRUCTURE_SIZES.get(tileType);

        if (size != null && size > 0) {
            count = count / (size * size); // Adjust count based on structure size
        }

        return count;
    }

    // Calculates the total count of usable tiles, ignoring empty tiles.
    public static int totalUsableTileCount(CityMap cityMap) {

        int totalUsableCount = 0;

        for (TileType tileType : TileType.values()) {
            if (tileType != TileType.EMPTY) {
                int count = countSpecificTileType(cityMap, tileType);
                totalUsableCount += count;
            }
        }

        return totalUsableCount;
    }
}
