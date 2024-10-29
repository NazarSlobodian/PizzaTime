package Model.Utils;

/**
 * Configuration of simulator time management
 */
public class TimeProperties {

    private int timeSpeed;
    private final long stepMs;
    //------------------------------------------------
    public TimeProperties(int timeSpeed, long stepMs) {
        this.timeSpeed = timeSpeed;
        this.stepMs = stepMs;
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
        //fire event
    }
    // - - - - - - - - - - - - - -
}
