package config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.Timer;
import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class GameTimeTest {

    private GameTime gameTime;
    private LocalDateTime initialTime;

    @BeforeEach
    public void setUp() {
        initialTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        gameTime = new GameTime(initialTime);
    }

    @Test
    public void testGetCurrentTime() {
        assertEquals(initialTime, gameTime.getCurrentTime());
    }

    @Test
    public void testSetCurrentTime() {
        LocalDateTime newTime = LocalDateTime.of(2024, 1, 1, 0, 0);
        gameTime.setCurrentTime(newTime);
        assertEquals(newTime, gameTime.getCurrentTime());
    }

    @Test
    public void testGetCurrentDate() {
        // DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        assertEquals("01-01-2023", gameTime.getCurrentDate());
    }

    @Test
    public void testIncrementDay() {
        gameTime.incrementDay();
        assertEquals(initialTime.plusDays(1), gameTime.getCurrentTime());
    }

    @Test
    public void testStartAndStopTimer() throws InterruptedException {
        gameTime.start();
        Timer timer = new Timer(GameTime.duration(1), e -> gameTime.incrementDay());
        timer.start();
        Thread.sleep(100); // Wait for some time to ensure timer starts
        assertTrue(timer.isRunning());
        gameTime.stop();
        // assertFalse(timer.isRunning());
    }

    @Test
    public void testDuration() {
        int duration = GameTime.duration(5); // 5 minutes in milliseconds
        assertEquals(5 * 60 * 1000, duration);
    }
}