package features;

import ui.*;
import config.*;

public class PopulationManager {
    public int population;
    private int residentialDemand;
    private int commercialDemand;
    private int industrialDemand;
    private int baseGrowth;
    private int residentialZoneCapacity;
    private int commercialZoneCapacity;
    private int industrialZoneCapacity;
    private int residentialZonePresent;
    private int commercialZonePresent;
    private int industrialZonePresent;

    public PopulationManager() {
        // this.population = 0;
        this.residentialDemand = 0;
        this.commercialDemand = 0;
        this.industrialDemand = 0;
        this.baseGrowth = 0;
        this.residentialZoneCapacity = 1000;
        this.commercialZoneCapacity = 3000;
        this.industrialZoneCapacity = 8000;
        this.residentialZonePresent = 0;
        this.commercialZonePresent = 0;
        this.industrialZonePresent = 0;
    }

    public void update(CityMap cityMap, Economy economy, UtilityManager utilityManager, ServiceManager serviceManager,
            TaxManager taxManager, SatisfactionManager satisfactionManager) {

        // Clear existing demands before recalculating
        residentialDemand = 0;
        commercialDemand = 0;
        industrialDemand = 0;

        // Loop through the cityMap to calculate demand based on the type of zones
        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {
                Tile tile = cityMap.getTile(x, y);
                switch (tile.getType()) {
                    case RESIDENTIAL:
                        // Increase population based on utility and service availability
                        if (utilityManager.hasPower() && utilityManager.hasWater()) {
                            population = calculatePopulationGrowth(tile.getType(), serviceManager, satisfactionManager);
                        }

                        // Residential demand based on population
                        residentialDemand += calculateResidentialDemand(cityMap, population, tile.getType());

                        break;
                    case COMMERCIAL:
                        if (utilityManager.hasPower() && utilityManager.hasWater()) {
                            population = calculatePopulationGrowth(tile.getType(), serviceManager, satisfactionManager);
                        }

                        // Commercial demand based on population
                        commercialDemand += calculateCommercialDemand(cityMap, population, tile.getType());

                        break;
                    case INDUSTRIAL:
                        if (utilityManager.hasPower() && utilityManager.hasWater()) {
                            population = calculatePopulationGrowth(tile.getType(), serviceManager, satisfactionManager);
                        }

                        // Industrial demand based on population
                        industrialDemand += calculateIndustrialDemand(cityMap, population, tile.getType());

                        break;
                    default:
                        // Other types do not directly affect population and demand
                        break;
                }
            }
        }

        population = Math.max(0, population); // Ensure population does not go below 0

        // Update economy based on new population and demands
        economy.update(population, residentialDemand, commercialDemand, industrialDemand, taxManager, serviceManager,
                utilityManager);
    }

    private int calculatePopulationGrowth(TileType tileType, ServiceManager serviceManager,
            SatisfactionManager satisfactionManager) {

        baseGrowth = 0;
        int totalPopulation = 0;
        int satisfactionCount = SatisfactionManager.getSatisfaction();

        if (satisfactionCount > 70) {

            if (tileType == TileType.RESIDENTIAL) {
                Integer size = GameConfig.STRUCTURE_SIZES.get(tileType);
                baseGrowth = residentialZoneCapacity / ((size * size) * 20);
                // System.out.println("baseGrowth residential: " + baseGrowth);
            } else if (tileType == TileType.COMMERCIAL) {
                Integer size = GameConfig.STRUCTURE_SIZES.get(tileType);
                baseGrowth = commercialZoneCapacity / ((size * size) * 20);
                // System.out.println("baseGrowth commercial: " + baseGrowth);
            } else if (tileType == TileType.INDUSTRIAL) {
                Integer size = GameConfig.STRUCTURE_SIZES.get(tileType);
                baseGrowth = industrialZoneCapacity / ((size * size) * 20);
                // System.out.println("baseGrowth industrial: " + baseGrowth);
            }

            // Population growth based on service coverage
            int educationBonus = serviceManager.getEducationCoverage();
            int healthBonus = serviceManager.getHealthCoverage();
            int policeBonus = serviceManager.getPoliceCoverage();
            int fireBonus = serviceManager.getFireCoverage();

            int serviceBonus = (educationBonus + healthBonus + policeBonus + fireBonus) / (4 * 4);

            // System.out.println("educationBonus: " + educationBonus);
            // System.out.println("healthBonus: " + healthBonus);
            // System.out.println("policeBonus: " + policeBonus);
            // System.out.println("fireBonus: " + fireBonus);
            // System.out.println("serviceBonus: " + serviceBonus);

            totalPopulation = population + baseGrowth + serviceBonus;
            // System.out.println("totalPopulation: " + totalPopulation);
        } else {

            if (tileType == TileType.RESIDENTIAL) {
                Integer size = GameConfig.STRUCTURE_SIZES.get(tileType);
                baseGrowth = residentialZoneCapacity / ((size * size) * 20);
            } else if (tileType == TileType.COMMERCIAL) {
                Integer size = GameConfig.STRUCTURE_SIZES.get(tileType);
                baseGrowth = commercialZoneCapacity / ((size * size) * 20);
            } else if (tileType == TileType.INDUSTRIAL) {
                Integer size = GameConfig.STRUCTURE_SIZES.get(tileType);
                baseGrowth = industrialZoneCapacity / ((size * size) * 20);
            }

            // Population growth based on service coverage
            int educationBonus = serviceManager.getEducationCoverage();
            int healthBonus = serviceManager.getHealthCoverage();
            int policeBonus = serviceManager.getPoliceCoverage();
            int fireBonus = serviceManager.getFireCoverage();

            int serviceBonus = (educationBonus + healthBonus + policeBonus + fireBonus) / (4 * 4);

            totalPopulation = population - baseGrowth - serviceBonus;
        }

        return totalPopulation;
    }

    int calculateResidentialDemand(CityMap cityMap, int population, TileType tileType) {

        // Residential demand based on population
        int residentialZoneDemand = population / residentialZoneCapacity;

        residentialZonePresent = TileCounter.countSpecificTileType(cityMap, tileType);

        if (residentialZoneDemand > residentialZonePresent) {
            residentialDemand = (residentialZoneDemand - residentialZonePresent);
        } else {
            residentialDemand = 0;
        }

        return residentialDemand;
    }

    int calculateCommercialDemand(CityMap cityMap, int population, TileType tileType) {

        // Commercial demand based on population
        int commercialZoneDemand = population / commercialZoneCapacity;

        commercialZonePresent = TileCounter.countSpecificTileType(cityMap, tileType);

        if (commercialZoneDemand > commercialZonePresent) {
            commercialDemand = (commercialZoneDemand - commercialZonePresent);
        } else {
            commercialDemand = 0;
        }

        return commercialDemand;
    }

    int calculateIndustrialDemand(CityMap cityMap, int population, TileType tileType) {

        // Industrial demand based on population
        int industrialZoneDemand = population / industrialZoneCapacity;

        industrialZonePresent = TileCounter.countSpecificTileType(cityMap, tileType);

        if (industrialZoneDemand > industrialZonePresent) {
            industrialDemand = (industrialZoneDemand - industrialZonePresent);
        } else {
            industrialDemand = 0;
        }

        return industrialDemand;
    }

    // getter and setter methods as needed for population and demands
    public int getPopulation() {
        return population;
    }

    public int getResidentialDemand() {
        return residentialDemand;
    }

    public int getCommercialDemand() {
        return commercialDemand;
    }

    public int getIndustrialDemand() {
        return industrialDemand;
    }

    public int getResidentialZoneCapacity() {
        return residentialZoneCapacity;
    }

    public int getCommercialZoneCapacity() {
        return commercialZoneCapacity;
    }

    public int getIndustrialZoneCapacity() {
        return industrialZoneCapacity;
    }

    public int getResidentialZonePresent() {
        return residentialZonePresent;
    }

    public int getCommercialZonePresent() {
        return commercialZonePresent;
    }

    public int getIndustrialZonePresent() {
        return industrialZonePresent;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setResidentialDemand(int i) {
        residentialDemand = i;
    }

    public void setCommercialDemand(int i) {
        commercialDemand = i;
    }

    public void setIndustrialDemand(int i) {
        industrialDemand = i;
    }

    public void setResidentialZoneCapacity(int i) {
        residentialZoneCapacity = i;
    }

    public void setCommercialZoneCapacity(int i) {
        commercialZoneCapacity = i;
    }

    public void setIndustrialZoneCapacity(int i) {
        industrialZoneCapacity = i;
    }

    public void setResidentialZonePresent(int i) {
        residentialZonePresent = i;
    }

    public void setCommercialZonePresent(int i) {
        commercialZonePresent = i;
    }

    public void setIndustrialZonePresent(int i) {
        industrialZonePresent = i;
    }
}
