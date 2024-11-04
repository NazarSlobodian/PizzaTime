package Model.Utils;

import java.beans.PropertyChangeSupport;
import java.util.concurrent.locks.Lock;

/**
 * Configuration of simulator time management
 */
public class TimeProperties extends ObservableModel {

    private int timeSpeed;
    private final long stepMs;

    private final Lock lock;
    //------------------------------------------------
    public TimeProperties(int timeSpeed, long stepMs, Lock lock) {
        this.timeSpeed = timeSpeed;
        this.stepMs = stepMs;

        this.lock = lock;
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
        lock.lock();
        int oldTimeSpeed = this.timeSpeed;
        this.timeSpeed = timeSpeed;
        lock.unlock();
        eventContext.firePropertyChange("simTimeSpeed", oldTimeSpeed, timeSpeed);
    }
    // - - - - - - - - - - - - - -
}
