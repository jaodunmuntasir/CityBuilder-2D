package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:citybuilder.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createTables() {
        String createGameStateTable = "CREATE TABLE IF NOT EXISTS game_state (" +
                "id INTEGER PRIMARY KEY," +
                "population INTEGER," +
                "tax_rate REAL," +
                "game_date TEXT" +
                ");";

        String createTileTable = "CREATE TABLE IF NOT EXISTS tiles (" +
                "id INTEGER PRIMARY KEY," +
                "x INTEGER," +
                "y INTEGER," +
                "type TEXT," +
                "game_id INTEGER," +
                "FOREIGN KEY(game_id) REFERENCES game_state(id)" +
                ");";

        String createLoanTable = "CREATE TABLE IF NOT EXISTS loans (" +
                "id INTEGER PRIMARY KEY," +
                "principal INTEGER," +
                "interest_rate REAL," +
                "balance INTEGER," +
                "game_id INTEGER," +
                "FOREIGN KEY(game_id) REFERENCES game_state(id)" +
                ");";

        try (Connection conn = connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(createGameStateTable);
            stmt.execute(createTileTable);
            stmt.execute(createLoanTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}