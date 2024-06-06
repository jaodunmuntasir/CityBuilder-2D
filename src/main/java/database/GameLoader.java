package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ui.CityMap;
import ui.TileType;
import features.Loan;
import features.LoanManager;
import config.GameTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameLoader {

    public static GameState loadGame() {
        String selectGameState = "SELECT population, tax_rate, game_date FROM game_state LIMIT 1";
        String selectTiles = "SELECT x, y, type FROM tiles WHERE game_id = 1";
        String selectLoans = "SELECT principal, interest_rate, balance FROM loans WHERE game_id = 1";

        GameState gameState = null;
        CityMap cityMap = null;
        LoanManager loanManager = new LoanManager(5, 5000); // Set with default values

        try (Connection conn = DatabaseHelper.connect();
                PreparedStatement pstmtGameState = conn.prepareStatement(selectGameState);
                PreparedStatement pstmtTiles = conn.prepareStatement(selectTiles);
                PreparedStatement pstmtLoans = conn.prepareStatement(selectLoans)) {

            // Load game state
            ResultSet rsGameState = pstmtGameState.executeQuery();
            if (rsGameState.next()) {
                int population = rsGameState.getInt("population");
                int taxRate = rsGameState.getInt("tax_rate");
                String gameDate = rsGameState.getString("game_date");

                // Create an empty CityMap (assuming default size, adjust as necessary)
                cityMap = new CityMap(20, 20); // Replace with actual size if dynamic

                // Load tiles
                ResultSet rsTiles = pstmtTiles.executeQuery();
                while (rsTiles.next()) {
                    int x = rsTiles.getInt("x");
                    int y = rsTiles.getInt("y");
                    String type = rsTiles.getString("type");
                    if (cityMap.isWithinBounds(x, y)) {
                        cityMap.setTile(x, y, TileType.valueOf(type));
                    } else {
                        System.out.println("Tile coordinates out of bounds: (" + x + ", " + y + ")");
                    }
                }

                // Load loans
                ResultSet rsLoans = pstmtLoans.executeQuery();
                while (rsLoans.next()) {
                    int principal = rsLoans.getInt("principal");
                    double interestRate = rsLoans.getDouble("interest_rate");
                    int balance = rsLoans.getInt("balance");
                    Loan loan = new Loan(principal, interestRate);
                    loan.repay(principal - balance); // Set the balance
                    loanManager.getLoans().add(loan);
                }

                // Convert gameDate to LocalDateTime (defaulting to MIDNIGHT)
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate currentDate = LocalDate.parse(gameDate, dateFormatter);
                LocalDateTime currentTime = currentDate.atStartOfDay();

                GameTime gameTime = new GameTime(currentTime);
                gameState = new GameState(population, taxRate, cityMap, loanManager, gameTime);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return gameState;
    }
}