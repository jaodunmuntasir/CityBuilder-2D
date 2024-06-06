package features;

import config.GameConfig;
import ui.TileType;

public class Economy {
    private int budget;

    public Economy(int initialBudget) {
        this.budget = initialBudget; // Starting budget
    }

    public int getBudget() {
        return budget;
    }

    public void changeBudget(int amount) {
        budget += amount;
    }

    public boolean canAfford(int cost) {
        return budget >= cost;
    }

    public void spend(int cost) {
        if (canAfford(cost)) {
            changeBudget(-cost);
        }
    }

    public void earn(int amount) {
        changeBudget(amount);
    }

    public void update(int population, int residentialDemand, int commercialDemand, int industrialDemand,
            TaxManager taxManager, ServiceManager serviceManager, UtilityManager utilityManager) {

        // Calculate tax income based on population and demand
        int taxIncome = taxManager.calculateTaxIncome(population, residentialDemand, commercialDemand,
                industrialDemand);
        earn(taxIncome);

        taxManager.adjustTaxRate(population); // Adjust tax rate based on population

        int maintenanceCosts = serviceManager.calculateMaintenanceCost() + utilityManager.calculateMaintenanceCost();
        spend(maintenanceCosts);

    }

    // Calculate refund amount when demolishing a tile
    public int calculateRefund(TileType type) {
        int refundPercentage = 50; // Example: refund 50% of the construction cost.
        int constructionCost = GameConfig.TILE_COST.getOrDefault(type, 0);
        // int size = GameConfig.STRUCTURE_SIZES.getOrDefault(type, 1);

        int refundAmount = ((constructionCost * refundPercentage) / 100);

        return refundAmount;
    }
}
