package features;

public class TaxManager {

    private int taxRate;

    public TaxManager(int initialTaxRate) {
        this.taxRate = initialTaxRate; // Starting tax rate
    }

    public void adjustTaxRate(int population) {

        int satisfactionCount = SatisfactionManager.getSatisfaction();

        if (satisfactionCount < 50) {

            // If satisfaction is below 50, decrease the tax rate by 2
            taxRate = taxRate - 2;

        } else if (satisfactionCount < 70) {

            // If satisfaction is below 70, decrease the tax rate by 1
            taxRate = taxRate - 1;

        } else {

            // If satisfaction is 70 or above, increase the tax rate by 1
            taxRate = taxRate + 1;
        }

        // Clamp tax rate between 1 and 20
        taxRate = Math.max(1, Math.min(20, taxRate));
    }

    public int calculateTaxIncome(int population, int residentialDemand, int commercialDemand, int industrialDemand) {

        // Placeholder for calculating tax income based on population and demand
        int taxIncome = ((population * taxRate) / 30)
                + ((residentialDemand + commercialDemand + industrialDemand) / 3) * taxRate;

        return taxIncome;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int newTaxRate) {
        taxRate = newTaxRate;
    }
}
