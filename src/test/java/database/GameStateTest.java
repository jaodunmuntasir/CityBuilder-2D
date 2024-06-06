package database;

import config.GameTime;
import features.LoanManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CityMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameStateTest {

    private GameState gameState;
    private CityMap cityMapMock;
    private LoanManager loanManagerMock;
    private GameTime gameTimeMock;

    @BeforeEach
    public void setUp() {
        cityMapMock = mock(CityMap.class);
        loanManagerMock = mock(LoanManager.class);
        gameTimeMock = mock(GameTime.class);

        gameState = new GameState(1000, 5, cityMapMock, loanManagerMock, gameTimeMock);
    }

    @Test
    public void testGetPopulation() {
        assertEquals(1000, gameState.getPopulation());
    }

    @Test
    public void testGetTaxRate() {
        assertEquals(5, gameState.getTaxRate());
    }

    @Test
    public void testGetCityMap() {
        assertEquals(cityMapMock, gameState.getCityMap());
    }

    @Test
    public void testGetLoanManager() {
        assertEquals(loanManagerMock, gameState.getLoanManager());
    }

    @Test
    public void testGetGameTime() {
        assertEquals(gameTimeMock, gameState.getGameTime());
    }
}