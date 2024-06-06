package features;

import ui.*;
import config.*;

public class UtilityManager {
    private boolean hasPower;
    private boolean hasWater;
    private int powerPlantCoverage;
    private int waterTowerCoverage;
    private int utilityCoverage;
    private int powerPlantMaintenanceCostPerUnit;
    private int waterTowerMaintenanceCostPerUnit;
    private int utilityMaintenancecost;
    private int powerPlantPresent;
    private int waterTowerPresent;
    private int powerPlantMaintenanceCost;
    private int waterTowerMaintenanceCost;

    public UtilityManager() {
        this.hasPower = false; // Initially, the city has no power
        this.hasWater = false; // Initially, the city has no water
        this.powerPlantCoverage = 0;
        this.waterTowerCoverage = 0;
        this.utilityCoverage = 0;
        this.powerPlantMaintenanceCostPerUnit = 50;
        this.waterTowerMaintenanceCostPerUnit = 30;
        this.utilityMaintenancecost = 0;
        this.powerPlantPresent = 0;
        this.waterTowerPresent = 0;
    }

    // Add methods to manage utilities
    public boolean hasPower() {
        return hasPower;
    }

    public void setPower(boolean hasPower) {
        this.hasPower = hasPower;
    }

    public boolean hasWater() {
        return hasWater;
    }

    public void setWater(boolean hasWater) {
        this.hasWater = hasWater;
    }

    public int calculateUtilityCoverage(CityMap cityMap, PopulationManager populationManager) {

        utilityCoverage = 0;
        int population = populationManager.getPopulation();
        int utilityCapacity = 10000;

        calculatePowerPlantCoverage(cityMap, population, utilityCapacity);
        calculateWaterTowerCoverage(cityMap, population, utilityCapacity);

        utilityCoverage = (powerPlantCoverage + waterTowerCoverage) / 2;

        return utilityCoverage;
    }

    public int calculatePowerPlantCoverage(CityMap cityMap, int population, int utilityCapacity) {

        powerPlantPresent = TileCounter.countSpecificTileType(cityMap, TileType.POWER_PLANT);

        if (population > utilityCapacity) {

            double initialpowerPlantNeeded = (Double.valueOf(population)) / (Double.valueOf(utilityCapacity));

            int secondarypowerPlantNeeded = population / utilityCapacity;

            int powerPlantNeeded = 0;

            if (initialpowerPlantNeeded > secondarypowerPlantNeeded) {
                powerPlantNeeded = secondarypowerPlantNeeded + 1;
            } else {
                powerPlantNeeded = secondarypowerPlantNeeded;
            }

            double initialpowerPlantCoverage = ((Double.valueOf(powerPlantPresent) / Double.valueOf(powerPlantNeeded))
                    * 100.0);

            int finalpowerPlantCoverage = (int) initialpowerPlantCoverage;

            powerPlantCoverage = Math.min(finalpowerPlantCoverage, 100); // Ensure coverage doesn't exceed 100%
        } else {
            powerPlantCoverage = 100;
        }

        powerPlantMaintenanceCost = powerPlantMaintenanceCostPerUnit * powerPlantPresent;

        return powerPlantCoverage;
    }

    public int calculateWaterTowerCoverage(CityMap cityMap, int population, int utilityCapacity) {

        waterTowerPresent = TileCounter.countSpecificTileType(cityMap, TileType.WATER_TOWER);

        if (population > utilityCapacity) {
            double initialWaterTowerNeeded = (Double.valueOf(population)) / (Double.valueOf(utilityCapacity));

            int secondaryWaterTowerNeeded = population / utilityCapacity;

            int waterTowerNeeded = 0;

            if (initialWaterTowerNeeded > secondaryWaterTowerNeeded) {
                waterTowerNeeded = secondaryWaterTowerNeeded + 1;
            } else {
                waterTowerNeeded = secondaryWaterTowerNeeded;
            }

            double initialWaterTowerCoverage = ((Double.valueOf(waterTowerPresent) / Double.valueOf(waterTowerNeeded))
                    * 100.0);

            int finalWaterTowerCoverage = (int) initialWaterTowerCoverage;

            waterTowerCoverage = Math.min(finalWaterTowerCoverage, 100); // Ensure coverage doesn't exceed 100%
        } else {
            waterTowerCoverage = 100;
        }

        waterTowerMaintenanceCost = waterTowerMaintenanceCostPerUnit * waterTowerPresent;

        return waterTowerCoverage;
    }

    public int calculateMaintenanceCost() {

        utilityMaintenancecost = 0;

        if (hasPower) {
            utilityMaintenancecost += powerPlantMaintenanceCost; // Cost of maintaining power plants
        }

        if (hasWater) {
            utilityMaintenancecost += waterTowerMaintenanceCost; // Cost of maintaining water towers
        }

        return utilityMaintenancecost;
    }

    // Method to update utilities based on city infrastructure
    public void update(CityMap cityMap, PopulationManager populationManager) {
        // Initially, no utilities are available until checked
        hasPower = false;
        hasWater = false;

        calculateUtilityCoverage(cityMap, populationManager);

        // Scan the cityMap for utility buildings
        // for (int y = 0; y < cityMap.getHeight(); y++) {
        // for (int x = 0; x < cityMap.getWidth(); x++) {
        // Tile tile = cityMap.getTile(x, y);
        // if (tile.getType() == TileType.POWER_PLANT) {
        // hasPower = true;
        // }
        // if (tile.getType() == TileType.WATER_TOWER) {
        // hasWater = true;
        // }
        // }
        // }

        // Update utilities based on coverage
        if (powerPlantCoverage < 75) {
            hasPower = false;
        } else {
            hasPower = true;
        }

        if (waterTowerCoverage < 70) {
            hasWater = false;
        } else {
            hasWater = true;
        }
    }

    public int getPowerPlantCoverage() {
        return powerPlantCoverage;
    }

    public int getWaterTowerCoverage() {
        return waterTowerCoverage;
    }

    public int getUtilityCoverage() {
        return utilityCoverage;
    }

    public int getPowerPlantPresent() {
        return powerPlantPresent;
    }

    public int getWaterTowerPresent() {
        return waterTowerPresent;
    }
}
