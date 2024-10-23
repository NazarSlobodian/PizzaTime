package Model.Utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Clock {
    private long currentTime;

    public Clock(long currentTime) {
        this.currentTime = currentTime;
    }
    public long getCurrentTime() {
        return currentTime;
    }
    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), ZoneId.systemDefault());
    }
    public void addMs(long ms) {
        currentTime += ms;
    }

    @Override
    public String toString() {
        return this.getLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss"));
    }
}
