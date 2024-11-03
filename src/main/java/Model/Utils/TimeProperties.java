package Model.Utils;

import java.beans.PropertyChangeSupport;

/**
 * Configuration of simulator time management
 */
public class TimeProperties {

    private int timeSpeed;
    private final long stepMs;

    private final EventFiringContext eventContext;
    //------------------------------------------------
    public TimeProperties(int timeSpeed, long stepMs, EventFiringContext eventContext) {
        this.timeSpeed = timeSpeed;
        this.stepMs = stepMs;

        this.eventContext = eventContext;
    }
    //------------------------------------------------
    public int getTimeSpeed() {
        return timeSpeed;
    }
    // - - - - - - - - - - - - - -
    public long getStepMs() {
        return stepMs;
    }
    // - - - - - - - - - - - - - -
    public void setTimeSpeed(int timeSpeed) {
        if (timeSpeed < 0) {
            throw new IllegalArgumentException("Don't.");
        }
        this.timeSpeed = timeSpeed;
        eventContext.firePropertyChange("simTimeSpeed", 0, timeSpeed);
    }
    // - - - - - - - - - - - - - -
}
