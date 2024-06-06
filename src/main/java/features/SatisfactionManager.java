package features;

import ui.*;

public class SatisfactionManager {
    private static int satisfaction;

    public SatisfactionManager() {
        SatisfactionManager.satisfaction = 70; // Start with a neutral satisfaction level, on a scale of 0 to 100
    }

    public static int getSatisfaction() {
        return satisfaction;
    }

    // Call this method to update the city's satisfaction score
    public void update(CityMap cityMap, UtilityManager utilityManager, ServiceManager serviceManager,
            PopulationManager populationManager, TaxManager taxManager) {

        int serviceScore = calculateServiceSatisfaction(serviceManager);
        int utilityScore = calculateUtilitySatisfaction(utilityManager);
        int populationScore = calculatePopulationSatisfaction(populationManager);

        // Combine the various scores to update overall satisfaction
        satisfaction = (serviceScore + utilityScore + populationScore) / 3;

        // Clamp satisfaction between 0 and 100
        satisfaction = Math.max(0, Math.min(100, satisfaction));
    }

    private int calculateServiceSatisfaction(ServiceManager serviceManager) {
        // Calculate satisfaction based on service coverage
        int averageCoverage = (serviceManager.getPoliceCoverage() + serviceManager.getFireCoverage() +
                serviceManager.getHealthCoverage() + serviceManager.getEducationCoverage()) / 4;

        return averageCoverage;
    }

    private int calculateUtilitySatisfaction(UtilityManager utilityManager) {
        // Calculate satisfaction based on utility availability
        int score = 0;
        score += utilityManager.hasPower() ? 50 : 0;
        score += utilityManager.hasWater() ? 50 : 0;

        return score;
    }

    private int calculatePopulationSatisfaction(PopulationManager populationManager) {

        // higher population leads to lower satisfaction due to overpopulation
        int score = 0;
        int population = populationManager.getPopulation();
        int residentialDemand = populationManager.getResidentialDemand();
        int residentialZoneCapacity = populationManager.getResidentialZoneCapacity();
        int residentialZonePresent = populationManager.getResidentialZonePresent();

        if (residentialDemand > 0) {

            score = ((residentialZoneCapacity * residentialZonePresent) / population) * 100;

        } else {
            score = 100;
        }

        return score;
    }

    public void setSatisfaction(int satisfaction) {
        SatisfactionManager.satisfaction = satisfaction;
    }
}
