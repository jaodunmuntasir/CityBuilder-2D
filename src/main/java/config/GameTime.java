package config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Timer;

public class GameTime {
    private LocalDateTime currentTime;
    private Timer timer;
    private static final int DAY_DURATION = duration(1); // 5 minutes in milliseconds
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public GameTime(LocalDateTime startTime) {
        this.currentTime = startTime;
    }

    public void start() {
        timer = new Timer(DAY_DURATION, e -> {
            incrementDay();
        });
        timer.start();
    }

    public void incrementDay() {
        currentTime = currentTime.plusDays(1);

        System.out.println("Current Game Date: " + getCurrentDate());
    }

    public String getCurrentDate() {
        return currentTime.format(dateFormatter);
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public LocalDateTime setCurrentTime(LocalDateTime time) {
        return currentTime = time;
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }

    public static int duration(int time) {
        int secondToMilisecond = 1000;

        int minuteToSecond = 60 * secondToMilisecond;

        int duration = time * minuteToSecond;

        return duration;
    }
}
