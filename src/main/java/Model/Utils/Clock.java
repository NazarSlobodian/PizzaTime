package Model.Utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Keeps track of time
 */
public class Clock extends ObservableModel{

    private long currentTime;

    //------------------------------------------------
    public Clock(long currentTime) {
        this.currentTime = currentTime;
        this.eventContext = new EventFiringContext(this);

    }
    //------------------------------------------------
    public long getCurrentTime() {
        return currentTime;
    }
    // - - - - - - - - - - - - - -
    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), ZoneId.systemDefault());
    }
    // - - - - - - - - - - - - - -
    public void addMs(long ms) {
        String oldTime = toString();
        currentTime += ms;
        if (eventContext.canFireEvent()) {
            eventContext.firePropertyChange("simDateTime", oldTime, toString());
        }
    }
    // - - - - - - - - - - - - - -
    @Override
    public String toString() {
        return this.getLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd,\nHH:mm:ss"));
    }
    // - - - - - - - - - - - - - -
}
