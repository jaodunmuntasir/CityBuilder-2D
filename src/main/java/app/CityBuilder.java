package app;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.Timer;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.awt.BorderLayout;

// Build Command
// mvn clean install

// Run Command
// java -cp "target/CityBuilder-1.0-SNAPSHOT.jar;sqlite-jdbc-3.45.3.0.jar;slf4j-api-2.1.0-alpha1.jar;slf4j-simple-2.1.0-alpha1.jar" app.CityBuilder

// YOU MUST RUN FROM THE ROOT DIRECTORY OF THE PROJECT

import features.Economy;
import features.ForestManager;
import features.TaxManager;
import features.PopulationManager;
import features.UtilityManager;
import features.ServiceManager;
import features.SatisfactionManager;
import features.LoanManager;
import config.DemolishManager;
import config.DisasterManager;
import config.GameConfig;
import config.GameTime;
import database.DatabaseHelper;
import database.GameLoader;
import database.GameSaver;
import database.GameState;
import ui.GridPanel;
import ui.TileType;
import ui.CityMap;
import ui.UIManager;

public class CityBuilder extends JPanel {

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 600;
    private CityMap cityMap;
    private static final int TILE_SIZE = 40; // This determines the size of each tile
    private TileType currentSelectedTileType = TileType.EMPTY;
    private GridPanel gridPanel;
    private int gridWidth;
    private int gridHeight;

    private Economy economy;
    private TaxManager taxManager;
    private PopulationManager populationManager;
    private Timer gameTimer;
    private Timer secondGameTimer;
    private Timer thirdGameTimer;
    private UtilityManager utilityManager;
    private ServiceManager serviceManager;
    private SatisfactionManager satisfactionManager;
    private DemolishManager demolishManager;
    private DisasterManager disasterManager;
    private UIManager uiManager;
    private LoanManager loanManager;
    private ForestManager forestManager;
    private GameTime gameTime;

    // Constructor to set up the game panel properties
    public CityBuilder() {
        setLayout(new BorderLayout()); // Use BorderLayout for the entire panel
        setBackground(Color.WHITE);

        // Create the database and tables
        DatabaseHelper.createTables();

        gridWidth = WIDTH / TILE_SIZE;
        gridHeight = HEIGHT / TILE_SIZE;
        cityMap = new CityMap(gridWidth, gridHeight);

        // Main Game Grid Design
        gridPanel = new GridPanel(this, cityMap, TILE_SIZE);
        gridPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(gridPanel, BorderLayout.CENTER);

        initializeGameTime();
        initializeManagers();
        forestManager.initializeForests(8);

        // Initialize UI Manager and ensure it completes before continuing
        uiManager = new UIManager(this);

        setUpGameLogic();
    }

    private void initializeManagers() {
        // Initialize the economy
        economy = new Economy(5000);

        // Initialize the tax manager
        taxManager = new TaxManager(5);

        // Initialize the population manager
        populationManager = new PopulationManager();

        // Initialize the utility manager
        utilityManager = new UtilityManager();

        // Initialize the service manager
        serviceManager = new ServiceManager();

        // Initialize the satisfaction manager
        satisfactionManager = new SatisfactionManager();

        // Initialize the demolish manager
        demolishManager = new DemolishManager();

        // Initialize the disaster manager
        disasterManager = new DisasterManager(cityMap);

        // Initialize the forest manager
        forestManager = new ForestManager(cityMap, economy);

        // Initialize the loan manager
        loanManager = new LoanManager(5, 5000);
    }

    private void setUpGameLogic() {

        // A mouse listener to handle tile placement and demolition
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentSelectedTileType == TileType.DEMOLISH) {
                    demolish(e.getX(), e.getY(), TILE_SIZE, cityMap, economy, serviceManager, populationManager);
                    uiManager.updateStatusBar();
                    repaint();
                } else {
                    placeTile(e.getX(), e.getY());
                }
            }
        });

        // Create a game timer to update the population and demand
        gameTimer = new Timer(5000, e -> updateGame());
        gameTimer.start();

        secondGameTimer = new Timer(5000, e -> secondUpdateGame());
        secondGameTimer.start();

        thirdGameTimer = new Timer(15000, e -> thirdupdateGame());
        thirdGameTimer.start();

        Timer loanUpdateTimer = new Timer(60000, e -> loanManager.updateLoans()); // This updates loans every minute if
                                                                                  // the game runs in real-time.
        loanUpdateTimer.start();
    }

    private void initializeGameTime() {
        LocalDateTime startTime = LocalDateTime.of(2020, 5, 15, 0, 0);
        gameTime = new GameTime(startTime);
        gameTime.start();
    }

    public void setCurrentSelectedTileType(TileType tileType) {
        currentSelectedTileType = tileType;
    }

    public void placeTile(int x, int y) {

        int gridX = x / TILE_SIZE;
        int gridY = y / TILE_SIZE;

        // Get the selected tile type and structure size
        TileType selectedType = currentSelectedTileType;
        Integer structureSize = GameConfig.STRUCTURE_SIZES.getOrDefault(selectedType, 1); // Default to 1x1 size
        int cost = GameConfig.TILE_COST.getOrDefault(selectedType, 0); // Default to 0 cost

        // Check if the coordinates are within the bounds of the map and structure size
        if (gridX <= cityMap.getWidth() - structureSize && gridY <= cityMap.getHeight() - structureSize) {

            // Check if all tiles in the square area are empty
            boolean areaClear = true;

            for (int dy = 0; dy < structureSize; dy++) {
                for (int dx = 0; dx < structureSize; dx++) {
                    if (cityMap.getTile(gridX + dx, gridY + dy).getType() != TileType.EMPTY) {
                        areaClear = false;
                        break;
                    }
                }
                if (!areaClear)
                    break;
            }

            // If the area is clear, place the structure
            if (areaClear) {
                if (economy.canAfford(cost)) {
                    for (int dy = 0; dy < structureSize; dy++) {
                        for (int dx = 0; dx < structureSize; dx++) {
                            cityMap.setTile(gridX + dx, gridY + dy, selectedType);
                        }
                    }

                    economy.changeBudget(-cost); // Subtract the cost from the budget
                    uiManager.updateStatusBar();
                    repaint();

                    if (selectedType == TileType.RESIDENTIAL) {
                        populationManager.population += 100;
                        forestManager.updateResidentialZones(populationManager, satisfactionManager);
                    }

                    updateGame();
                    secondUpdateGame();

                } else {
                    alert("Not enough budget!"); // Provide feedback that there's not enough budget
                }
            } else {
                alert("The Area isn't Clear!"); // Provide feedback that the area is not clear or there's not enough
                                                // budget
            }
        } else {
            alert("Placement is out of bounds!"); // Provide feedback that the placement is out of bounds
        }
    }

    private void demolish(int x, int y, int tileSize, CityMap cityMap, Economy economy, ServiceManager serviceManager,
            PopulationManager populationManager) {

        demolishManager.demolishSetup(x, y, tileSize, cityMap);
        demolishManager.demolishProcess(cityMap, economy, serviceManager, populationManager);
        demolishManager.refund(economy);

        currentSelectedTileType = TileType.EMPTY;
        uiManager.updateStatusBar();
        repaint();

        updateGame();
    }

    // Display an alert dialog
    private void alert(String str) {
        JOptionPane.showMessageDialog(null, str);
    }

    // Update the game state
    private void updateGame() {
        utilityManager.update(cityMap, populationManager);
        serviceManager.update(cityMap, populationManager);
        satisfactionManager.update(cityMap, utilityManager, serviceManager, populationManager, taxManager);

        uiManager.updateStatusBar();
        repaint();
    }

    private void secondUpdateGame() {
        populationManager.update(cityMap, economy, utilityManager, serviceManager, taxManager, satisfactionManager);
        forestManager.applyMaintenanceAndAgeForests();

        uiManager.updateStatusBar();
        repaint();
    }

    private void thirdupdateGame() {
        taxManager.adjustTaxRate(populationManager.getPopulation());

        uiManager.updateStatusBar();
        repaint();
    }

    // Method to save the game state
    public void saveGame() {
        int population = populationManager.getPopulation();
        int taxRate = taxManager.getTaxRate();
        GameSaver.saveGame(population, taxRate, cityMap, loanManager, gameTime);
        JOptionPane.showMessageDialog(this, "Game saved successfully!");
    }

    // Method to load the game state
    public void loadGame() {
        GameState gameState = GameLoader.loadGame();
        if (gameState != null) {
            populationManager.setPopulation(gameState.getPopulation());
            taxManager.setTaxRate(gameState.getTaxRate());
            loanManager = gameState.getLoanManager();
            cityMap = gameState.getCityMap();
            gridPanel.setCityMap(cityMap); // Update the grid panel with the new city map
            gridPanel.revalidate(); // Ensure the panel is validated
            gridPanel.repaint(); // Repaint the grid to reflect the loaded state
            gameTime.setCurrentTime(gameState.getGameTime().getCurrentTime());
            JOptionPane.showMessageDialog(this, "Game loaded successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to load game state.");
        }
    }

    public int getTileSize() {
        return TILE_SIZE;
    }

    public CityMap getCityMap() {
        return cityMap;
    }

    public DisasterManager getDisasterManager() {
        return disasterManager;
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    public PopulationManager getPopulationManager() {
        return populationManager;
    }

    public DemolishManager getDemolishManager() {
        return demolishManager;
    }

    public Economy getEconomy() {
        return economy;
    }

    public TaxManager getTaxManager() {
        return taxManager;
    }

    public UtilityManager getUtilityManager() {
        return utilityManager;
    }

    public LoanManager getLoanManager() {
        return loanManager;
    }

    public SatisfactionManager getSatisfactionManager() {
        return satisfactionManager;
    }

    public ForestManager getForestManager() {
        return forestManager;
    }

    public GameTime getGameTime() {
        return gameTime;
    }

    public int getGameWidth() {
        return WIDTH;
    }

    public int getGameHeight() {
        return HEIGHT;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("City Builder Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(true);
        frame.add(new CityBuilder(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
