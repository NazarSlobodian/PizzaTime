package Model;

import Model.Utils.Clock;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class SimulatorUpdater {
    //
    private Clock clock;                // this,
    private int timeSpeed = 60;         // this
    private final long oneStepMs = 1000;// and this may be moved somewhere else
    // other classes will probably only need current time (clock object)
    // PizzeriaSimulator pizzeriaSimulator; should it be a separate class, so that this one will only deal with time change management?
    // no idea how to set up view-models for this.
    // Using Facade?

    //
    private final PropertyChangeSupport support;
    //
    public SimulatorUpdater() {
        Initialize();
        support = new PropertyChangeSupport(this);
    }

    public void update(long elapsedMs) {
        // test
        String before = clock.toString();
        //

        long remainingMs = elapsedMs*timeSpeed;

        while (remainingMs > oneStepMs) {
            clock.addMs(oneStepMs);         // if in-game elapsed is one hour,
            remainingMs -= oneStepMs;       // this will go over each second to correctly update state
            //call update for something
            //pizzeriaSimulator.update(); ?
        }
        if (remainingMs > 0) {
            clock.addMs(oneStepMs);
            //.update();
        }

        //   For now fireProperty is at end of update. If it was called in while loop,
        // there would be more propertyChange events fired than needed.
        //   One way to solve it - pass bool lastUpdateInLoop to other methods, which they will take into account
        // when deciding to fire event or not.
        support.firePropertyChange("simDateTime", before, clock.toString());
    }

    public String getDateTime() {
        return clock.toString();
    }

    public void setTimeSpeed(int timeSpeed) {
        if (timeSpeed <= 0) {
            throw new IllegalArgumentException("Don't.");
        }
        this.timeSpeed = timeSpeed;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private void Initialize() {
        clock = new Clock(ZonedDateTime.of(
                        LocalDateTime.of(2024, 10, 1, 9, 0, 0),
                        ZoneId.systemDefault())
                .toInstant().toEpochMilli());
    }
}
