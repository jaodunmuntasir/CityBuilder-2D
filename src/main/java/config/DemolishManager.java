package config;

import features.*;
import ui.*;

public class DemolishManager {

    private int gridX;
    private int gridY;
    private TileType typeToDemolish;
    private int refundAmount;

    public DemolishManager() {
        this.gridX = 0;
        this.gridY = 0;
        this.refundAmount = 0;
    }

    public TileType getTypeToDemolish() {
        return typeToDemolish;
    }

    public void setTypeToDemolish(TileType typeToDemolish) {
        this.typeToDemolish = typeToDemolish;
    }

    public TileType demolishSetup(int x, int y, int TILE_SIZE, CityMap cityMap) {
        gridX = x / TILE_SIZE;
        gridY = y / TILE_SIZE;

        // Get the tile that was clicked
        Tile currentTile = cityMap.getTile(gridX, gridY);
        typeToDemolish = currentTile.getType();

        setTypeToDemolish(typeToDemolish);

        return typeToDemolish;
    }

    public void demolishProcess(CityMap cityMap, Economy economy, ServiceManager serviceManager,
            PopulationManager populationManager) {

        // Make sure the tile is not empty and it's a type that has a defined structure
        // size
        if (typeToDemolish != TileType.EMPTY && GameConfig.STRUCTURE_SIZES.containsKey(typeToDemolish)) {
            Integer structureSize = GameConfig.STRUCTURE_SIZES.get(typeToDemolish);

            // Find the top-left corner of the structure
            int topLeftX = gridX;
            int topLeftY = gridY;
            while (topLeftX > 0 && cityMap.getTile(topLeftX - 1, gridY).getType() == typeToDemolish) {
                topLeftX--;
            }
            while (topLeftY > 0 && cityMap.getTile(gridX, topLeftY - 1).getType() == typeToDemolish) {
                topLeftY--;
            }

            // Determine the boundaries of the structure
            int bottomRightX = topLeftX + structureSize - 1;
            int bottomRightY = topLeftY + structureSize - 1;

            // Check if the whole structure is within the map bounds
            if (bottomRightX < cityMap.getWidth() && bottomRightY < cityMap.getHeight()) {

                refundAmount = economy.calculateRefund(typeToDemolish);

                // Remove the whole structure
                for (int dy = topLeftY; dy <= bottomRightY; dy++) {
                    for (int dx = topLeftX; dx <= bottomRightX; dx++) {
                        cityMap.setTile(dx, dy, TileType.EMPTY);
                    }
                }

                demolishAfterEffects(serviceManager, typeToDemolish, populationManager, cityMap);
            }
        }

    }

    public void demolishAfterEffects(ServiceManager serviceManager, TileType typeToDemolish,
            PopulationManager populationManager, CityMap cityMap) {

        switch (typeToDemolish) {
            case POLICE_STATION:
                int policePresent = serviceManager.getPolicePresent();
                int policeCoverage = serviceManager.getPoliceCoverage();

                policePresent--;

                if (policePresent == 0) {
                    policeCoverage = 0;
                } else {
                    serviceManager.calculatePoliceCoverage(cityMap, typeToDemolish, populationManager.getPopulation());
                    policeCoverage = serviceManager.getPoliceCoverage();
                }

                serviceManager.setPolicePresent(policePresent);
                serviceManager.setPoliceCoverage(policeCoverage);

                break;
            case FIRE_STATION:
                int firePresent = serviceManager.getFirePresent();
                int fireCoverage = serviceManager.getFireCoverage();

                firePresent--;

                if (firePresent == 0) {
                    fireCoverage = 0;
                } else {
                    serviceManager.calculateFireCoverage(cityMap, typeToDemolish, populationManager.getPopulation());
                    fireCoverage = serviceManager.getFireCoverage();
                }

                serviceManager.setFirePresent(firePresent);
                serviceManager.setFireCoverage(fireCoverage);

                break;
            case HOSPITAL:
                int healthPresent = serviceManager.getHealthPresent();
                int healthCoverage = serviceManager.getHealthCoverage();

                healthPresent--;

                if (healthPresent == 0) {
                    healthCoverage = 0;
                } else {
                    serviceManager.calculateHealthCoverage(cityMap, typeToDemolish, populationManager.getPopulation());
                    healthCoverage = serviceManager.getHealthCoverage();
                }

                serviceManager.setHealthPresent(healthPresent);
                serviceManager.setHealthCoverage(healthCoverage);

                break;
            case SCHOOL:
                int educationPresent = serviceManager.getEducationPresent();
                int educationCoverage = serviceManager.getEducationCoverage();

                educationPresent--;

                if (educationPresent == 0) {
                    educationCoverage = 0;
                } else {
                    serviceManager.calculateEducationCoverage(cityMap, typeToDemolish,
                            populationManager.getPopulation());
                    educationCoverage = serviceManager.getEducationCoverage();
                }

                serviceManager.setEducationPresent(educationPresent);
                serviceManager.setEducationCoverage(educationCoverage);

                break;

            default:
                break;
        }
    }

    public void refund(Economy economy) {
        economy.earn(refundAmount);
    }
}
