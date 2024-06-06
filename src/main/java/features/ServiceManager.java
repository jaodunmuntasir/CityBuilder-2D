package features;

import ui.*;
import config.*;

public class ServiceManager {
    private int policeCoverage;
    private int fireCoverage;
    private int healthCoverage;
    private int educationCoverage;
    private int policeMaintenanceCostPerUnit;
    private int fireMaintenanceCostPerUnit;
    private int healthMaintenanceCostPerUnit;
    private int educationMaintenanceCostPerUnit;
    private int policeMaintenanceCost;
    private int fireMaintenanceCost;
    private int healthMaintenanceCost;
    private int educationMaintenanceCost;
    private int totalMaintenanceCost;
    private int policePresent;
    private int firePresent;
    private int healthPresent;
    private int educationPresent;

    public ServiceManager() {
        this.policeCoverage = 0;
        this.fireCoverage = 0;
        this.healthCoverage = 0;
        this.educationCoverage = 0;
        this.policeMaintenanceCostPerUnit = 10;
        this.fireMaintenanceCostPerUnit = 10;
        this.healthMaintenanceCostPerUnit = 10;
        this.educationMaintenanceCostPerUnit = 10;
        this.policeMaintenanceCost = 0;
        this.fireMaintenanceCost = 0;
        this.healthMaintenanceCost = 0;
        this.educationMaintenanceCost = 0;
        this.totalMaintenanceCost = 0;
        this.policePresent = 0;
        this.firePresent = 0;
        this.healthPresent = 0;
        this.educationPresent = 0;
    }

    public int getPoliceCoverage() {
        return policeCoverage;
    }

    public void calculatePoliceCoverage(CityMap cityMap, TileType tileType, int population) {

        int policeCapability = 200; // Number of people a police station can serve

        policePresent = (TileCounter.countSpecificTileType(cityMap, TileType.POLICE_STATION));

        // System.out.println("Policia: " + policePresent);

        if (population >= policeCapability) {

            double initialPoliceNeeded = (Double.valueOf(population)) / (Double.valueOf(policeCapability));

            int secondaryPoliceNeeded = population / policeCapability;

            int policeNeeded = 0;

            if (initialPoliceNeeded > secondaryPoliceNeeded) {
                policeNeeded = secondaryPoliceNeeded + 1;
            } else {
                policeNeeded = secondaryPoliceNeeded;
            }

            // System.out.println("Police needed: " + policeNeeded);

            // System.out.println("Police present: " + policePresent);

            double initialPoliceCoverage = ((Double.valueOf(policePresent) / Double.valueOf(policeNeeded)) * 100.0);
            // System.out.println("Police coverage Initial: " + initialPoliceCoverage);

            int finalPoliceCoverage = (int) initialPoliceCoverage;
            // System.out.println("Police coverage Final: " + finalPoliceCoverage);

            policeCoverage = Math.min(finalPoliceCoverage, 100); // Ensure coverage doesn't exceed 100%
            // System.out.println("Police coverage Math: " + policeCoverage);
        } else {
            policeCoverage = 100;
        }

        policeMaintenanceCost = policePresent * policeMaintenanceCostPerUnit;
    }

    public int getFireCoverage() {
        return fireCoverage;
    }

    public void calculateFireCoverage(CityMap cityMap, TileType tileType, int population) {

        int fireCapability = 150; // Number of people a fire station can serve

        firePresent = (TileCounter.countSpecificTileType(cityMap, TileType.FIRE_STATION));

        if (population >= fireCapability) {

            double initialFireNeeded = (Double.valueOf(population)) / (Double.valueOf(fireCapability));

            int secondaryFireNeeded = population / fireCapability;

            int fireNeeded = 0;

            if (initialFireNeeded > secondaryFireNeeded) {
                fireNeeded = secondaryFireNeeded + 1;
            } else {
                fireNeeded = secondaryFireNeeded;
            }

            // System.out.println("Fire needed: " + fireNeeded);

            // System.out.println("Fire present: " + firePresent);

            double initialFireCoverage = ((Double.valueOf(firePresent) / Double.valueOf(fireNeeded)) * 100.0);
            // System.out.println("Fire coverage Initial: " + initialFireCoverage);

            int finalFireCoverage = (int) initialFireCoverage;
            // System.out.println("Fire coverage Final: " + finalFireCoverage);

            fireCoverage = Math.min(finalFireCoverage, 100); // Ensure coverage doesn't exceed 100%
            // System.out.println("Fire coverage Math: " + fireCoverage);
        } else {
            fireCoverage = 100;
        }

        fireMaintenanceCost = firePresent * fireMaintenanceCostPerUnit;
    }

    public int getHealthCoverage() {
        return healthCoverage;
    }

    public void calculateHealthCoverage(CityMap cityMap, TileType tileType, int population) {

        int healthCapability = 150; // Number of people a hospital can serve

        healthPresent = (TileCounter.countSpecificTileType(cityMap, TileType.HOSPITAL));

        if (population >= healthCapability) {

            double initialHealthNeeded = (Double.valueOf(population)) / (Double.valueOf(healthCapability));

            int secondaryHealthNeeded = population / healthCapability;

            int healthNeeded = 0;

            if (initialHealthNeeded > secondaryHealthNeeded) {
                healthNeeded = secondaryHealthNeeded + 1;
            } else {
                healthNeeded = secondaryHealthNeeded;
            }

            // System.out.println("Health needed: " + healthNeeded);

            // System.out.println("Health present: " + healthPresent);

            double initialHealthCoverage = ((Double.valueOf(healthPresent) / Double.valueOf(healthNeeded)) * 100.0);
            // System.out.println("Health coverage Initial: " + initialHealthCoverage);

            int finalHealthCoverage = (int) initialHealthCoverage;
            // System.out.println("Health coverage Final: " + finalHealthCoverage);

            healthCoverage = Math.min(finalHealthCoverage, 100); // Ensure coverage doesn't exceed 100%
            // System.out.println("Health coverage Math: " + healthCoverage);
        } else {
            healthCoverage = 100;
        }

        healthMaintenanceCost = healthPresent * healthMaintenanceCostPerUnit;
    }

    public int getEducationCoverage() {
        return educationCoverage;
    }

    public void calculateEducationCoverage(CityMap cityMap, TileType tileType, int population) {

        int educationCapability = 500; // Number of people a school can serve

        educationPresent = (TileCounter.countSpecificTileType(cityMap, TileType.SCHOOL));

        if (population >= educationCapability) {

            double initialEducationNeeded = (Double.valueOf(population)) / (Double.valueOf(educationCapability));

            int secondaryEducationNeeded = population / educationCapability;

            int educationNeeded = 0;

            if (initialEducationNeeded > secondaryEducationNeeded) {
                educationNeeded = secondaryEducationNeeded + 1;
            } else {
                educationNeeded = secondaryEducationNeeded;
            }

            // System.out.println("Education needed: " + educationNeeded);

            // System.out.println("Education present: " + educationPresent);

            double initialEducationCoverage = ((Double.valueOf(educationPresent) / Double.valueOf(educationNeeded))
                    * 100.0);
            // System.out.println("Education coverage Initial: " +
            // initialEducationCoverage);

            int finalEducationCoverage = (int) initialEducationCoverage;
            // System.out.println("Education coverage Final: " + finalEducationCoverage);

            educationCoverage = Math.min(finalEducationCoverage, 100); // Ensure coverage doesn't exceed 100%
            // System.out.println("Education coverage Math: " + educationCoverage);
        } else {
            educationCoverage = 100;
        }

        educationMaintenanceCost = educationPresent * educationMaintenanceCostPerUnit;
    }

    public int calculateMaintenanceCost() {

        // Total maintenance cost is the sum of individual service costs
        totalMaintenanceCost = policeMaintenanceCost + fireMaintenanceCost + healthMaintenanceCost
                + educationMaintenanceCost;

        return totalMaintenanceCost;
    }

    // Add method to update services based on city infrastructure
    public void update(CityMap cityMap, PopulationManager populationManager) {

        int population = populationManager.getPopulation();

        // Scan the cityMap for service buildings and update coverage
        for (int y = 0; y < cityMap.getHeight(); y++) {
            for (int x = 0; x < cityMap.getWidth(); x++) {
                Tile tile = cityMap.getTile(x, y);
                switch (tile.getType()) {
                    case POLICE_STATION:
                        calculatePoliceCoverage(cityMap, tile.getType(), population);
                        break;
                    case FIRE_STATION:
                        calculateFireCoverage(cityMap, tile.getType(), population);
                        break;
                    case HOSPITAL:
                        calculateHealthCoverage(cityMap, tile.getType(), population);
                        break;
                    case SCHOOL:
                        calculateEducationCoverage(cityMap, tile.getType(), population);
                        break;
                    default:
                        break;
                }
            }
        }
        System.out.println("Rendorseg: " + policePresent);
    }

    public int getPolicePresent() {
        return policePresent;
    }

    public int getFirePresent() {
        return firePresent;
    }

    public int getHealthPresent() {
        return healthPresent;
    }

    public int getEducationPresent() {
        return educationPresent;
    }

    public void setPolicePresent(int policePresent) {
        this.policePresent = policePresent;
    }

    public void setFirePresent(int firePresent) {
        this.firePresent = firePresent;
    }

    public void setHealthPresent(int healthPresent) {
        this.healthPresent = healthPresent;
    }

    public void setEducationPresent(int educationPresent) {
        this.educationPresent = educationPresent;
    }

    public void setPoliceCoverage(int policeCoverage) {
        this.policeCoverage = policeCoverage;
    }

    public void setFireCoverage(int fireCoverage) {
        this.fireCoverage = fireCoverage;
    }

    public void setHealthCoverage(int healthCoverage) {
        this.healthCoverage = healthCoverage;
    }

    public void setEducationCoverage(int educationCoverage) {
        this.educationCoverage = educationCoverage;
    }
}
