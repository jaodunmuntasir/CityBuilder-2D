package config;

import ui.*;
import features.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class DisasterManager {

    private CityMap cityMap;
    private TileType disasterType;
    public static final TileType[] disasters = { TileType.FIRE, TileType.METEOR, TileType.CHEMICAL, TileType.GODZILLA };

    public DisasterManager(CityMap cityMap) {
        this.cityMap = cityMap;
    }

    public void disaster(int tileSize, ServiceManager serviceManager, PopulationManager populationManager,
            DemolishManager demolishManager, Economy economy) {
        int structurePresent = TileCounter.totalUsableTileCount(cityMap);

        double disasterPossible = structurePresent * 0.4;
        int disasterAffected = (int) Math.round(disasterPossible);

        if (disasterAffected == 0) {
            triggerDisaster(tileSize, serviceManager, populationManager, demolishManager, economy);
        } else {
            for (int i = 0; i < disasterAffected; i++) {
                triggerDisaster(tileSize, serviceManager, populationManager, demolishManager, economy);
            }
        }
    }

    public void triggerDisaster(int tileSize, ServiceManager serviceManager, PopulationManager populationManager,
            DemolishManager demolishManager, Economy economy) {
        Random rand = new Random();
        disasterType = disasters[rand.nextInt(disasters.length)];

        int[] coords = selectNonEmptyTile();
        if (coords != null) {
            applyDisasterEffect(coords[0], coords[1], disasterType, tileSize, economy, serviceManager,
                    populationManager, demolishManager);
        }
    }

    private int[] selectNonEmptyTile() {
        ArrayList<int[]> nonEmptyTiles = new ArrayList<>();
        for (int x = 0; x < cityMap.getWidth(); x++) {
            for (int y = 0; y < cityMap.getHeight(); y++) {
                if (cityMap.getTile(x, y).getType() != TileType.EMPTY) {
                    nonEmptyTiles.add(new int[] { x, y });
                }
            }
        }
        if (!nonEmptyTiles.isEmpty()) {
            return nonEmptyTiles.get(new Random().nextInt(nonEmptyTiles.size()));
        }
        return null;
    }

    private void applyDisasterEffect(int x, int y, TileType disasterType, int tileSize, Economy economy,
            ServiceManager serviceManager, PopulationManager populationManager, DemolishManager demolishManager) {

        TileType originalType = cityMap.getTile(x, y).getType();
        Integer structureSize = GameConfig.STRUCTURE_SIZES.get(originalType);

        if (structureSize != null) {
            // Correctly find the top-left corner of the structure
            int topLeftX = x;
            int topLeftY = y;
            while (topLeftX > 0 && cityMap.getTile(topLeftX - 1, topLeftY).getType() == originalType) {
                topLeftX--;
            }
            while (topLeftY > 0 && cityMap.getTile(topLeftX, topLeftY - 1).getType() == originalType) {
                topLeftY--;
            }

            // Apply the disaster type to the entire structure
            for (int i = 0; i < structureSize; i++) {
                for (int j = 0; j < structureSize; j++) {
                    int targetX = topLeftX + i;
                    int targetY = topLeftY + j;
                    if (targetX < cityMap.getWidth() && targetY < cityMap.getHeight()) {
                        cityMap.setTile(targetX, targetY, disasterType);
                    }
                }
            }

            // Final coordinates to be used inside the lambda must be effectively final
            final int finalTopLeftX = topLeftX;
            final int finalTopLeftY = topLeftY;

            // Simulate the disaster effect and then clear the area
            new Timer(5000, e -> {
                clearDisasterArea(finalTopLeftX, finalTopLeftY, structureSize);
                ((Timer) e.getSource()).stop();
            }).start();
        }
    }

    private void clearDisasterArea(int x, int y, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (targetX < cityMap.getWidth() && targetY < cityMap.getHeight()) {
                    cityMap.setTile(targetX, targetY, TileType.EMPTY);
                }
            }
        }
    }

    public TileType getDisasterType() {
        return disasterType;
    }
}