package ui;

import app.CityBuilder;
import config.*;
import features.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class UIManagerTest {

    private UIManager uiManager;
    private MockCityBuilder cityBuilder;

    @BeforeEach
    public void setUp() {
        cityBuilder = new MockCityBuilder();
        uiManager = new UIManager(cityBuilder);
    }

    @Test
    public void testInitialSetup() {
        assertNotNull(uiManager);
        assertNotNull(uiManager.getToolBar1());
        assertNotNull(uiManager.getToolBar2());
        assertNotNull(uiManager.getStatusBar());
        assertNotNull(uiManager.getStatusBar2());
    }

    @Test
    public void testUpdateStatusBar() {
        uiManager.updateStatusBar();

        JLabel statusBar = uiManager.getStatusBar();
        String statusMessage = String.format(
                "Population: %d | Satisfaction: %d%% | Power: %s | Water: %s | Police: %d%% | Fire: %d%% | Health: %d%% | Education: %d%% | Selected Tile: %s",
                cityBuilder.getPopulationManager().getPopulation(),
                SatisfactionManager.getSatisfaction(),
                cityBuilder.getUtilityManager().hasPower() ? "Yes" : "No",
                cityBuilder.getUtilityManager().hasWater() ? "Yes" : "No",
                cityBuilder.getServiceManager().getPoliceCoverage(),
                cityBuilder.getServiceManager().getFireCoverage(),
                cityBuilder.getServiceManager().getHealthCoverage(),
                cityBuilder.getServiceManager().getEducationCoverage(),
                uiManager.getCurrentSelectedTileType());
        assertEquals(statusMessage, statusBar.getText());

        JLabel statusBar2 = uiManager.getStatusBar2();
        String statusMessage2 = String.format(
                "Taxrate: %d%% | Budget: $%d | Date: %s",
                cityBuilder.getTaxManager().getTaxRate(),
                cityBuilder.getEconomy().getBudget(),
                cityBuilder.getGameTime().getCurrentDate());
        assertEquals(statusMessage2, statusBar2.getText());
    }

    class MockCityBuilder extends CityBuilder {
        @SuppressWarnings("unused")
        private DisasterManager disasterManager;
        @SuppressWarnings("unused")
        private ServiceManager serviceManager;
        @SuppressWarnings("unused")
        private PopulationManager populationManager;
        @SuppressWarnings("unused")
        private DemolishManager demolishManager;
        private Economy economy;
        @SuppressWarnings("unused")
        private TaxManager taxManager;
        @SuppressWarnings("unused")
        private UtilityManager utilityManager;
        private LoanManager loanManager;
        @SuppressWarnings("unused")
        private GameTime gameTime;
        @SuppressWarnings("unused")
        private LoanPanel loanPanel;
        @SuppressWarnings("unused")
        private int tileSize;

        MockCityBuilder() {
            super();
            this.tileSize = 32;
            this.disasterManager = new DisasterManager(getCityMap());
            this.serviceManager = new ServiceManager();
            this.populationManager = new PopulationManager();
            this.demolishManager = new DemolishManager();
            this.economy = new Economy(5000);
            this.taxManager = new TaxManager(0);
            this.utilityManager = new UtilityManager();
            this.loanManager = new LoanManager(5.0, 10000);
            this.gameTime = new GameTime(null);
            this.loanPanel = new LoanPanel(economy, loanManager);
        }
    }
}