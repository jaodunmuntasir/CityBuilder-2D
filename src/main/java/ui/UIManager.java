package ui;

import javax.swing.*;
import java.awt.*;
import app.CityBuilder;
import config.*;
import features.*;

public class UIManager {
    private CityBuilder cityBuilder;
    private JToolBar toolBar1;
    private JToolBar toolBar2;
    private JLabel statusBar;
    private JLabel statusBar2;
    private TileType currentSelectedTileType = TileType.EMPTY;

    private int TILE_SIZE;
    private DisasterManager disasterManager;
    private ServiceManager serviceManager;
    private PopulationManager populationManager;
    private DemolishManager demolishManager;
    private Economy economy;
    private TaxManager taxManager;
    private UtilityManager utilityManager;
    private LoanManager loanManager;
    private LoanPanel loanPanel;
    private GameTime gameTime;

    public UIManager(CityBuilder cityBuilder) {
        this.cityBuilder = cityBuilder;
        this.TILE_SIZE = cityBuilder.getTileSize();
        this.disasterManager = cityBuilder.getDisasterManager();
        this.serviceManager = cityBuilder.getServiceManager();
        this.populationManager = cityBuilder.getPopulationManager();
        this.demolishManager = cityBuilder.getDemolishManager();
        this.economy = cityBuilder.getEconomy();
        this.taxManager = cityBuilder.getTaxManager();
        this.utilityManager = cityBuilder.getUtilityManager();
        this.loanManager = cityBuilder.getLoanManager();
        this.gameTime = cityBuilder.getGameTime();

        // Set modern look and feel
        setLookAndFeel();

        initializeUI(TILE_SIZE, disasterManager, serviceManager, populationManager, demolishManager, economy);
    }

    private void setLookAndFeel() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeUI(int TILE_SIZE, DisasterManager disasterManager, ServiceManager serviceManager,
            PopulationManager populationManager, DemolishManager demolishManager, Economy economy) {
        loanManager = new LoanManager(5.0, 10000); // Sample interest rate and max loan amount
        loanPanel = new LoanPanel(economy, loanManager);

        setupToolbars(TILE_SIZE, disasterManager, serviceManager, populationManager, demolishManager, economy);
        setupStatusPanel();
    }

    private void setupToolbars(int TILE_SIZE, DisasterManager disasterManager, ServiceManager serviceManager,
            PopulationManager populationManager, DemolishManager demolishManager, Economy economy) {
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
        toolBar1 = new JToolBar();
        toolBar1.setLayout(new GridLayout(1, 0)); // Adjust to the number of buttons dynamically
        toolBar2 = new JToolBar();
        toolPanel.add(toolBar1);
        toolPanel.add(toolBar2);
        cityBuilder.add(toolPanel, BorderLayout.NORTH);
        addToolbarButtons(TILE_SIZE, disasterManager, serviceManager, populationManager, demolishManager, economy);
    }

    private void addToolbarButtons(int TILE_SIZE, DisasterManager disasterManager, ServiceManager serviceManager,
            PopulationManager populationManager, DemolishManager demolishManager, Economy economy) {
        // Add buttons to the toolbar for different tile types
        addToolbarButton("assets/road.png", TileType.ROAD, "Road");
        addToolbarButton("assets/residential.png", TileType.RESIDENTIAL, "Residential");
        addToolbarButton("assets/commercial.png", TileType.COMMERCIAL, "Commercial");
        addToolbarButton("assets/industrial.png", TileType.INDUSTRIAL, "Industrial");
        addToolbarButton("assets/police_station.png", TileType.POLICE_STATION, "Police Station");
        addToolbarButton("assets/fire_station.png", TileType.FIRE_STATION, "Fire Station");
        addToolbarButton("assets/hospital.jpg", TileType.HOSPITAL, "Hospital");
        addToolbarButton("assets/school.jpg", TileType.SCHOOL, "School");
        addToolbarButton("assets/power_plant.png", TileType.POWER_PLANT, "Power Plant");
        addToolbarButton("assets/water_tower.png", TileType.WATER_TOWER, "Water Tower");
        addToolbarButton("assets/empty.jpg", TileType.DEMOLISH, "Demolish");

        JButton disasterButton = new JButton("Disaster");
        disasterButton.addActionListener(e -> {
            disasterManager.disaster(TILE_SIZE, serviceManager, populationManager, demolishManager, economy);
            currentSelectedTileType = disasterManager.getDisasterType();
            cityBuilder.setCurrentSelectedTileType(currentSelectedTileType);
            updateStatusBar();
        });
        toolBar2.add(disasterButton);

        JButton loanButton = new JButton("Loans");
        loanButton.addActionListener(e -> openLoanPanel());
        toolBar2.add(loanButton);

        // Create Save button
        JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener(e -> cityBuilder.saveGame());
        toolBar2.add(saveButton);

        // Create Load button
        JButton loadButton = new JButton("Load Game");
        loadButton.addActionListener(e -> cityBuilder.loadGame());
        toolBar2.add(loadButton);

        // Add tax control UI
        addTaxControlUI();
    }

    private void addToolbarButton(String iconPath, TileType tileType, String tooltip) {
        JButton button = new JButton();
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setPreferredSize(new Dimension(100, 100)); // Set button size to 100x100 pixels
        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(iconPath));
            Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
            button.setText(tileType.toString());
        } catch (Exception e) {
            button.setText(tileType.toString());
        }
        button.setToolTipText(tooltip);
        button.addActionListener(e -> {
            currentSelectedTileType = tileType;
            cityBuilder.setCurrentSelectedTileType(currentSelectedTileType);
            statusBar.setText("Selected Tile: " + tileType.toString());
            updateStatusBar();
        });
        toolBar1.add(button);
    }

    private void addTaxControlUI() {
        JPanel taxRatePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        taxRatePanel.setBorder(BorderFactory.createEtchedBorder());

        JLabel taxRateLabel = new JLabel("Tax Rate:");
        JButton decreaseButton = new JButton("-");
        JLabel taxRateValueLabel = new JLabel(String.valueOf(cityBuilder.getTaxManager().getTaxRate()));
        JButton increaseButton = new JButton("+");

        taxRatePanel.add(taxRateLabel);
        taxRatePanel.add(decreaseButton);
        taxRatePanel.add(taxRateValueLabel);
        taxRatePanel.add(increaseButton);

        toolBar2.add(taxRatePanel);

        // Assuming taxManager is accessible via getTaxManager()
        setupTaxButtons(decreaseButton, increaseButton, taxRateValueLabel);
    }

    private void setupTaxButtons(JButton decreaseButton, JButton increaseButton, JLabel taxRateValueLabel) {
        decreaseButton.addActionListener(e -> {
            int currentRate = Integer.parseInt(taxRateValueLabel.getText());
            if (currentRate > 0) {
                currentRate--;
                taxRateValueLabel.setText(String.valueOf(currentRate));
                cityBuilder.getTaxManager().setTaxRate(currentRate);
                updateStatusBar();
            }
        });

        increaseButton.addActionListener(e -> {
            int currentRate = Integer.parseInt(taxRateValueLabel.getText());
            if (currentRate < 20) {
                currentRate++;
                taxRateValueLabel.setText(String.valueOf(currentRate));
                cityBuilder.getTaxManager().setTaxRate(currentRate);
                updateStatusBar();
            }
        });
    }

    private void setupStatusPanel() {
        JPanel statusPanel = new JPanel(new GridLayout(2, 1));
        statusBar = new JLabel("Selected Tile: NONE");
        statusBar2 = new JLabel("Taxrate: 0% | Budget: $5000");
        statusBar.setFont(new Font("Arial", Font.BOLD, 14));
        statusBar2.setFont(new Font("Arial", Font.BOLD, 14));
        statusPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        statusPanel.add(statusBar);
        statusPanel.add(statusBar2);
        cityBuilder.add(statusPanel, BorderLayout.SOUTH);
    }

    // Update the status bar to include the budget
    public void updateStatusBar() {
        // Format the status bar message with population and demand information
        String statusMessage = String.format(
                "Population: %d | Satisfaction: %d%% | Power: %s | Water: %s | Police: %d%% | Fire: %d%% | Health: %d%% | Education: %d%% | Selected Tile: %s",
                populationManager.getPopulation(),
                SatisfactionManager.getSatisfaction(),
                utilityManager.hasPower() ? "Yes" : "No",
                utilityManager.hasWater() ? "Yes" : "No",
                serviceManager.getPoliceCoverage(),
                serviceManager.getFireCoverage(),
                serviceManager.getHealthCoverage(),
                serviceManager.getEducationCoverage(),
                currentSelectedTileType);
        statusBar.setText(statusMessage);

        String statusMessage2 = String.format(
                "Taxrate: %d%% | Budget: $%d | Date: %s",
                taxManager.getTaxRate(),
                economy.getBudget(),
                gameTime.getCurrentDate());
        statusBar2.setText(statusMessage2);
    }

    private void openLoanPanel() {
        JDialog loanDialog = new JDialog((Frame) null, "Loan Management", true);

        loanDialog.setContentPane(loanPanel);
        loanDialog.setSize(700, 300); // Set the size depending on your content
        loanDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        loanDialog.setLocationRelativeTo(null); // Centers the dialog relative to the main window
        loanDialog.setVisible(true);
    }

    public JToolBar getToolBar1() {
        return toolBar1;
    }

    public JToolBar getToolBar2() {
        return toolBar2;
    }

    public JLabel getStatusBar() {
        return statusBar;
    }

    public JLabel getStatusBar2() {
        return statusBar2;
    }

    public TileType getCurrentSelectedTileType() {
        return currentSelectedTileType;
    }

    public void setCurrentSelectedTileType(TileType currentSelectedTileType) {
        this.currentSelectedTileType = currentSelectedTileType;
    }
}