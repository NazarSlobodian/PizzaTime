package Model.Utils;

import java.util.concurrent.locks.Lock;

/**
 * Configuration of simulator time management
 */
public class TimeProperties extends ObservableModel {

    private int timeSpeed;
    private final long stepMs;
    private boolean skippingTime;
    private int skippingTimeSpeed;
    private final Lock lock;
    //------------------------------------------------
    public TimeProperties(int timeSpeed, long stepMs, Lock lock) {

        this.timeSpeed = timeSpeed;
        this.stepMs = stepMs;
        this.skippingTime = false;
        this.skippingTimeSpeed = 60*60;
        this.lock = lock;
    }
    //------------------------------------------------
    public int getTimeSpeed() {
        if (skippingTime) {
            return skippingTimeSpeed;
        }
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
    public void setSkippingTime(boolean skippingTime) {
        this.skippingTime = skippingTime;
    }
    public boolean isSkippingTime() {
        return skippingTime;
    }
}
