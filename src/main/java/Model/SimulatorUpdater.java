package Model;

import Model.Utils.Clock;

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
    public SimulatorUpdater() {
        Initialize();
    }

    public void update(long elapsedMs) {

        long remainingMs = elapsedMs*timeSpeed;

        while (remainingMs > oneStepMs) {
            System.out.println(clock.toString());
            clock.addMs(oneStepMs);         // if ingame elapsed is one hour,
            remainingMs -= oneStepMs;       // this will go over each second to correctly update state
            //call update for something
            //pizzeriaSimulator.update(); ?
        }
        if (remainingMs > 0) {
            System.out.println(clock.toString()); // do step smaller than oneStepMs
            clock.addMs(oneStepMs);
            //.update();
        }
    }
    public void setTimeSpeed(int timeSpeed) {
        if (timeSpeed <= 0) {
            throw new IllegalArgumentException("Don't.");
        }
        this.timeSpeed = timeSpeed;
    }

    private void Initialize() {
        clock = new Clock(ZonedDateTime.of(
                        LocalDateTime.of(2024, 10, 1, 9, 0, 0),
                        ZoneId.systemDefault())
                .toInstant().toEpochMilli());
    }

}
