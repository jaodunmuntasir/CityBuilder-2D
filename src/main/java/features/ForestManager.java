package features;

import ui.*;

import java.util.Random;

public class ForestManager {
    private CityMap cityMap;
    private Economy economy;
    private final int FOREST_MAINTENANCE_COST = 5;

    public ForestManager(CityMap cityMap, Economy economy) {
        this.cityMap = cityMap;
        this.economy = economy;
    }

    public void initializeForests(int numberOfForests) {
        Random rand = new Random();
        for (int i = 0; i < numberOfForests; i++) {
            int x = rand.nextInt(cityMap.getWidth());
            int y = rand.nextInt(cityMap.getHeight());
            if (cityMap.getTile(x, y).getType() == TileType.EMPTY) {
                cityMap.setTile(x, y, TileType.FOREST);
            }
        }
    }

    public void updateResidentialZones(PopulationManager populationManager, SatisfactionManager satisfactionManager) {
        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {
                if (cityMap.getTile(x, y).getType() == TileType.RESIDENTIAL) {
                    if (hasAdjacentForest(x, y)) {
                        int population = populationManager.getPopulation();
                        int newPopulation = population + 100;
                        populationManager.setPopulation(newPopulation);

                        int satisfaction = SatisfactionManager.getSatisfaction();
                        int newSatisfaction = satisfaction + 10;
                        satisfactionManager.setSatisfaction(newSatisfaction);
                    }
                }
            }
        }
    }

    private boolean hasAdjacentForest(int x, int y) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx != 0 || dy != 0) {
                    if (cityMap.isWithinBounds(x + dx, y + dy)
                            && cityMap.getTile(x + dx, y + dy).getType() == TileType.FOREST) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void applyMaintenanceAndAgeForests() {
        int maintenanceCost = 0;
        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {
                if (cityMap.getTile(x, y).getType() == TileType.FOREST) {
                    cityMap.getTile(x, y).age(); // Tile class has an age() method that potentially turns it to EMPTY
                    if (cityMap.getTile(x, y).getType() == TileType.FOREST) {
                        maintenanceCost += FOREST_MAINTENANCE_COST;
                    }
                }
            }
        }
        economy.spend(maintenanceCost);
    }
}