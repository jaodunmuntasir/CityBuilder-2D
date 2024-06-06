package features;

import config.GameConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.TileType;

import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

public class EconomyTest {

    private Economy economy;

    @BeforeEach
    public void setUp() {
        economy = new Economy(1000); // Initial budget of 1000
    }

    @Test
    public void testGetBudget() {
        assertEquals(1000, economy.getBudget());
    }

    @Test
    public void testChangeBudget() {
        economy.changeBudget(500);
        assertEquals(1500, economy.getBudget());

        economy.changeBudget(-300);
        assertEquals(1200, economy.getBudget());
    }

    @Test
    public void testCanAfford() {
        assertTrue(economy.canAfford(500));
        assertFalse(economy.canAfford(1500));
    }

    @Test
    public void testSpend() {
        economy.spend(200);
        assertEquals(800, economy.getBudget());

        economy.spend(1000); // Trying to spend more than available budget
        assertEquals(800, economy.getBudget());
    }

    @Test
    public void testEarn() {
        economy.earn(300);
        assertEquals(1300, economy.getBudget());
    }

    // @Test
    // public void testUpdate() {
    //     // TaxManager taxManagerMock = mock(TaxManager.class);
    //     // ServiceManager serviceManagerMock = mock(ServiceManager.class);
    //     // UtilityManager utilityManagerMock = mock(UtilityManager.class);

    //     // when(taxManagerMock.calculateTaxIncome(1000, 100, 100, 100)).thenReturn(500);
    //     // when(serviceManagerMock.calculateMaintenanceCost()).thenReturn(200);
    //     // when(utilityManagerMock.calculateMaintenanceCost()).thenReturn(100);

    //     // economy.update(1000, 100, 100, 100, taxManagerMock, serviceManagerMock, utilityManagerMock);

    //     assertEquals(1200, economy.getBudget()); // Initial 1000 + 500 income - 300 maintenance
    // }

    @Test
    public void testCalculateRefund() {
        GameConfig.TILE_COST.put(TileType.RESIDENTIAL, 1000);

        int refund = economy.calculateRefund(TileType.RESIDENTIAL);
        assertEquals(500, refund); // 50% of 1000

        refund = economy.calculateRefund(TileType.COMMERCIAL);
        assertEquals(100, refund); // No cost defined for COMMERCIAL in the test
    }
}