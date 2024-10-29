package Model;

import Model.Utils.Clock;
import Model.Utils.TimeProperties;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Pizzeria {

    private Clock clock;
    TimeProperties timeProperties;

    // support should be used in model classes which fire update events
    private final PropertyChangeSupport support;

    //------------------------------------------------
    public Pizzeria() {
        support = new PropertyChangeSupport(this);

        Initialize();
    }
    // - - - - - - - - - - - - - -
    public void update(long elapsedMs) {
        long remainingMs = elapsedMs * timeProperties.getTimeSpeed();
        long step = timeProperties.getStepMs();

        while (remainingMs > step) {
            clock.addMs(step, false);

            updateStuff(step, false);

            remainingMs -= step;
        }
        if (remainingMs > 0) {
            clock.addMs(step, true);

            updateStuff(step,true);
        }
    }
    private void updateStuff(long elapsedMs, boolean lastUpdate) {
        //
    }
    // - - - - - - - - - - - - - -
    public void setTimeSpeed(int timeSpeed) {
        timeProperties.setTimeSpeed(timeSpeed);
    }
    // - - - - - - - - - - - - - -
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    // - - - - - - - - - - - - - -
    private void Initialize() {
        clock = new Clock(ZonedDateTime.of(
                        LocalDateTime.of(2024, 10, 1, 9, 0, 0),
                        ZoneId.systemDefault())
                .toInstant().toEpochMilli(), support);
        timeProperties = new TimeProperties(60, 1000);
    }
}
