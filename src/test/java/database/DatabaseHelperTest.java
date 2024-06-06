package database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;

import java.sql.Connection;
// import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DatabaseHelperTest {

    @SuppressWarnings("unused")
    private DatabaseHelper dbHelper;

    @BeforeEach
    public void setUp() {
        dbHelper = new DatabaseHelper();
    }

    @Test
    public void testConnect() {
        try (Connection conn = DatabaseHelper.connect()) {
            assertNotNull(conn);
            assertFalse(conn.isClosed());
        } catch (SQLException e) {
            fail("Connection should not throw an exception.");
        }
    }

    @SuppressWarnings("static-access")
    @Test
    public void testCreateTables() {
        Connection connMock = mock(Connection.class);
        Statement stmtMock = mock(Statement.class);

        try {
            when(connMock.createStatement()).thenReturn(stmtMock);
            // Stubbing void method to do nothing
            // doNothing().when(stmtMock).execute(anyString());

            DatabaseHelper dbHelperSpy = spy(DatabaseHelper.class);
            doReturn(connMock).when(dbHelperSpy).connect();

            dbHelperSpy.createTables();

            // verify(stmtMock, times(3)).execute(anyString());
        } catch (SQLException e) {
            fail("No exception should be thrown during table creation.");
        }
    }
}