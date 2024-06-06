// package database;

// import config.GameTime;
// import features.Loan;
// import features.LoanManager;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import ui.CityMap;
// import ui.Tile;
// import ui.TileType;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// public class GameSaverTest {

//     private Connection connMock;
//     private Statement stmtMock;
//     private PreparedStatement pstmtGameStateMock;
//     private PreparedStatement pstmtTileMock;
//     private PreparedStatement pstmtLoanMock;

//     @BeforeEach
//     public void setUp() throws Exception {
//         connMock = mock(Connection.class);
//         stmtMock = mock(Statement.class);
//         pstmtGameStateMock = mock(PreparedStatement.class);
//         pstmtTileMock = mock(PreparedStatement.class);
//         pstmtLoanMock = mock(PreparedStatement.class);

//         when(connMock.createStatement()).thenReturn(stmtMock);

//         doAnswer(invocation -> pstmtGameStateMock).when(connMock).prepareStatement(anyString(), anyInt());
//         doAnswer(invocation -> pstmtTileMock).when(connMock).prepareStatement(eq("INSERT INTO tiles(x, y, type, game_id) VALUES(?, ?, ?, ?)"));
//         doAnswer(invocation -> pstmtLoanMock).when(connMock).prepareStatement(eq("INSERT INTO loans(principal, interest_rate, balance, game_id) VALUES(?, ?, ?, ?)"));

//         // Mocking executeUpdate and executeBatch to avoid actual database interactions
//         doAnswer(invocation -> 1).when(stmtMock).executeUpdate(anyString());
//         doAnswer(invocation -> 1).when(pstmtGameStateMock).executeUpdate();
//         doAnswer(invocation -> new int[]{1}).when(pstmtTileMock).executeBatch();
//         doAnswer(invocation -> new int[]{1}).when(pstmtLoanMock).executeBatch();
//     }

//     @Test
//     public void testSaveGame() throws Exception {
//         CityMap cityMapMock = mock(CityMap.class);
//         LoanManager loanManagerMock = mock(LoanManager.class);
//         GameTime gameTimeMock = mock(GameTime.class);

//         when(cityMapMock.getHeight()).thenReturn(2);
//         when(cityMapMock.getWidth()).thenReturn(2);

//         Tile tileMock = mock(Tile.class);
//         when(tileMock.getType()).thenReturn(TileType.RESIDENTIAL);
//         when(cityMapMock.getTile(anyInt(), anyInt())).thenReturn(tileMock);

//         List<Loan> loans = new ArrayList<>();
//         Loan loanMock = mock(Loan.class);
//         when(loanMock.getPrincipal()).thenReturn(10000);
//         when(loanMock.getInterestRate()).thenReturn(5.0);
//         when(loanMock.getBalance()).thenReturn(8000);
//         loans.add(loanMock);

//         when(loanManagerMock.getLoans()).thenReturn(loans);
//         when(gameTimeMock.getCurrentDate()).thenReturn("01-01-2023");

//         GameSaver.saveGame(1000, 5, cityMapMock, loanManagerMock, gameTimeMock);

//         verify(stmtMock, times(1)).executeUpdate("DELETE FROM game_state");
//         verify(stmtMock, times(1)).executeUpdate("DELETE FROM tiles");
//         verify(stmtMock, times(1)).executeUpdate("DELETE FROM loans");

//         verify(pstmtGameStateMock, times(1)).setInt(1, 1000);
//         verify(pstmtGameStateMock, times(1)).setInt(2, 5);
//         verify(pstmtGameStateMock, times(1)).setString(3, "01-01-2023");
//         verify(pstmtGameStateMock, times(1)).executeUpdate();

//         verify(pstmtTileMock, times(4)).setInt(anyInt(), anyInt());
//         verify(pstmtTileMock, times(4)).setString(anyInt(), anyString());
//         verify(pstmtTileMock, times(4)).setLong(anyInt(), anyLong());
//         verify(pstmtTileMock, times(1)).executeBatch();

//         verify(pstmtLoanMock, times(1)).setInt(1, 10000);
//         verify(pstmtLoanMock, times(1)).setDouble(2, 5.0);
//         verify(pstmtLoanMock, times(1)).setInt(3, 8000);
//         verify(pstmtLoanMock, times(1)).setLong(4, 1);
//         verify(pstmtLoanMock, times(1)).executeBatch();
//     }
// }
