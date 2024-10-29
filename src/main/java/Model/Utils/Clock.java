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

    private final PropertyChangeSupport support;

    //------------------------------------------------
    public Clock(long currentTime, PropertyChangeSupport support) {
        this.currentTime = currentTime;
        this.support = support;
        support.firePropertyChange("simDateTime", 0, toString());
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
    public void addMs(long ms, boolean lastUpdate) {
        currentTime += ms;
        if (lastUpdate) {
            support.firePropertyChange("simDateTime", 0, toString());
        }
    }
    // - - - - - - - - - - - - - -
    @Override
    public String toString() {
        return this.getLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss"));
    }
    // - - - - - - - - - - - - - -
}
