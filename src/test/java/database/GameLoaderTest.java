// package database;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;

// import ui.CityMap;
// import ui.TileType;
// import features.Loan;
// import features.LoanManager;
// import config.GameTime;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// public class GameLoaderTest {

//     private Connection connMock;
//     private PreparedStatement pstmtGameStateMock;
//     private PreparedStatement pstmtTilesMock;
//     private PreparedStatement pstmtLoansMock;
//     private ResultSet rsGameStateMock;
//     private ResultSet rsTilesMock;
//     private ResultSet rsLoansMock;

//     @BeforeEach
//     public void setUp() throws SQLException {
//         connMock = mock(Connection.class);
//         pstmtGameStateMock = mock(PreparedStatement.class);
//         pstmtTilesMock = mock(PreparedStatement.class);
//         pstmtLoansMock = mock(PreparedStatement.class);
//         rsGameStateMock = mock(ResultSet.class);
//         rsTilesMock = mock(ResultSet.class);
//         rsLoansMock = mock(ResultSet.class);

//         when(connMock.prepareStatement("SELECT population, tax_rate, game_date FROM game_state LIMIT 1")).thenReturn(pstmtGameStateMock);
//         when(connMock.prepareStatement("SELECT x, y, type FROM tiles WHERE game_id = 1")).thenReturn(pstmtTilesMock);
//         when(connMock.prepareStatement("SELECT principal, interest_rate, balance FROM loans WHERE game_id = 1")).thenReturn(pstmtLoansMock);

//         when(pstmtGameStateMock.executeQuery()).thenReturn(rsGameStateMock);
//         when(pstmtTilesMock.executeQuery()).thenReturn(rsTilesMock);
//         when(pstmtLoansMock.executeQuery()).thenReturn(rsLoansMock);
//     }

//     @Test
//     public void testLoadGame() throws SQLException {
//         when(rsGameStateMock.next()).thenReturn(true);
//         when(rsGameStateMock.getInt("population")).thenReturn(1000);
//         when(rsGameStateMock.getInt("tax_rate")).thenReturn(5);
//         when(rsGameStateMock.getString("game_date")).thenReturn("01-01-2023");

//         when(rsTilesMock.next()).thenReturn(true, true, false);
//         when(rsTilesMock.getInt("x")).thenReturn(0, 1);
//         when(rsTilesMock.getInt("y")).thenReturn(0, 1);
//         when(rsTilesMock.getString("type")).thenReturn("RESIDENTIAL", "COMMERCIAL");

//         when(rsLoansMock.next()).thenReturn(true, false);
//         when(rsLoansMock.getInt("principal")).thenReturn(10000);
//         when(rsLoansMock.getDouble("interest_rate")).thenReturn(5.0);
//         when(rsLoansMock.getInt("balance")).thenReturn(8000);

//         GameState gameState = GameLoader.loadGame();
//         assertNotNull(gameState);
//         assertEquals(1000, gameState.getPopulation());
//         assertEquals(5, gameState.getTaxRate());
//         assertEquals("01-01-2023", gameState.getGameTime().getCurrentDate());

//         CityMap cityMap = gameState.getCityMap();
//         assertEquals(TileType.RESIDENTIAL, cityMap.getTile(0, 0).getType());
//         assertEquals(TileType.COMMERCIAL, cityMap.getTile(1, 1).getType());

//         LoanManager loanManager = gameState.getLoanManager();
//         assertEquals(1, loanManager.getLoans().size());
//         Loan loan = loanManager.getLoans().get(0);
//         assertEquals(10000, loan.getPrincipal());
//         assertEquals(5.0, loan.getInterestRate());
//         assertEquals(2000, loan.getBalance());
//     }
// }