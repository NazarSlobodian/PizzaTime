package Model.Utils;

import java.beans.PropertyChangeSupport;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Keeps track of time
 */
public class Clock {

    private long currentTime;

    private final EventFiringContext eventContext;

    //------------------------------------------------
    public Clock(long currentTime, EventFiringContext eventContext) {
        this.currentTime = currentTime;
        this.eventContext = eventContext;
        eventContext.firePropertyChange("simDateTime", 0, toString());
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
        currentTime += ms;
        if (eventContext.canFireEvent()) {
            eventContext.firePropertyChange("simDateTime", 0, toString());
        }
    }
    // - - - - - - - - - - - - - -
    @Override
    public String toString() {
        return this.getLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss"));
    }
    // - - - - - - - - - - - - - -
}
