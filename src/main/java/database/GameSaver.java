package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ui.CityMap;
import features.Loan;
import features.LoanManager;
import config.GameTime;

public class GameSaver {

    public static void saveGame(int population, int taxRate, CityMap cityMap, LoanManager loanManager,
            GameTime gameTime) {
        String deleteOldGameState = "DELETE FROM game_state";
        String deleteOldTiles = "DELETE FROM tiles";
        String deleteOldLoans = "DELETE FROM loans";

        String insertGameState = "INSERT INTO game_state(population, tax_rate, game_date) VALUES(?, ?, ?)";
        String insertTile = "INSERT INTO tiles(x, y, type, game_id) VALUES(?, ?, ?, ?)";
        String insertLoan = "INSERT INTO loans(principal, interest_rate, balance, game_id) VALUES(?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
                Statement stmt = conn.createStatement();
                PreparedStatement pstmtGameState = conn.prepareStatement(insertGameState,
                        Statement.RETURN_GENERATED_KEYS)) {

            // Delete old game state, tiles, and loans
            stmt.executeUpdate(deleteOldGameState);
            stmt.executeUpdate(deleteOldTiles);
            stmt.executeUpdate(deleteOldLoans);

            // Save game state
            pstmtGameState.setInt(1, population);
            pstmtGameState.setInt(2, taxRate);
            pstmtGameState.setString(3, gameTime.getCurrentDate());
            pstmtGameState.executeUpdate();

            // Get the generated game state id (always 1 because there's only one game
            // state)
            long gameId = 1;

            // Save tiles
            try (PreparedStatement pstmtTile = conn.prepareStatement(insertTile)) {
                for (int y = 0; y < cityMap.getHeight(); y++) {
                    for (int x = 0; x < cityMap.getWidth(); x++) {
                        pstmtTile.setInt(1, x);
                        pstmtTile.setInt(2, y);
                        pstmtTile.setString(3, cityMap.getTile(x, y).getType().toString());
                        pstmtTile.setLong(4, gameId);
                        pstmtTile.addBatch();
                    }
                }
                pstmtTile.executeBatch();
            }

            // Save loans
            try (PreparedStatement pstmtLoan = conn.prepareStatement(insertLoan)) {
                List<Loan> loans = loanManager.getLoans();
                for (Loan loan : loans) {
                    pstmtLoan.setInt(1, loan.getPrincipal());
                    pstmtLoan.setDouble(2, loan.getInterestRate());
                    pstmtLoan.setInt(3, loan.getBalance());
                    pstmtLoan.setLong(4, gameId);
                    pstmtLoan.addBatch();
                }
                pstmtLoan.executeBatch();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}