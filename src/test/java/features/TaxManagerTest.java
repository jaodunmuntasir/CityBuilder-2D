package features;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaxManagerTest {

    private TaxManager taxManager;
    private SatisfactionManager satisfactionManager;

    @BeforeEach
    public void setUp() {
        taxManager = new TaxManager(10); // Initial tax rate of 10
        satisfactionManager = new SatisfactionManager();
    }

    @Test
    public void testInitialTaxRate() {
        assertEquals(10, taxManager.getTaxRate());
    }

    @Test
    public void testAdjustTaxRateLowSatisfaction() {
        satisfactionManager.setSatisfaction(40);
        taxManager.adjustTaxRate(1000);
        assertEquals(8, taxManager.getTaxRate()); // Decrease by 2
    }

    @Test
    public void testAdjustTaxRateMediumSatisfaction() {
        satisfactionManager.setSatisfaction(60);
        taxManager.adjustTaxRate(1000);
        assertEquals(9, taxManager.getTaxRate()); // Decrease by 1
    }

    @Test
    public void testAdjustTaxRateHighSatisfaction() {
        satisfactionManager.setSatisfaction(80);
        taxManager.adjustTaxRate(1000);
        assertEquals(11, taxManager.getTaxRate()); // Increase by 1
    }

    @Test
    public void testAdjustTaxRateClamp() {
        taxManager.setTaxRate(1);
        satisfactionManager.setSatisfaction(40);
        taxManager.adjustTaxRate(1000);
        assertEquals(1, taxManager.getTaxRate()); // Should not go below 1

        taxManager.setTaxRate(20);
        satisfactionManager.setSatisfaction(80);
        taxManager.adjustTaxRate(1000);
        assertEquals(20, taxManager.getTaxRate()); // Should not go above 20
    }

    @Test
    public void testCalculateTaxIncome() {
        int taxIncome = taxManager.calculateTaxIncome(1000, 200, 300, 400);
        assertTrue(taxIncome > 0); // Placeholder check, can adjust with expected value based on formula
    }
}