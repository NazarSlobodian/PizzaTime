package Model;

import Model.FoodAndStuff.Menu;
import Model.Generators.OrderGenerator;
import Model.KitchenStuff.KitchenManager;
import Model.Utils.Clock;
import Model.Utils.EventFiringContext;
import Model.Utils.TimeProperties;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pizzeria {

    private Clock clock;
    TimeProperties timeProperties;

    OrderGenerator orderGenerator;
    Menu menu;
    KitchenManager kitchenManager;
    // support should be used in model classes which fire update events
    private EventFiringContext eventContext;
    private Lock lock;
    //------------------------------------------------
    public Pizzeria() {
        Initialize();
    }
    // - - - - - - - - - - - - - -
    public void update(long elapsedMs) {
        lock.lock();

        eventContext.forbidEventFiring();
        long remainingMs = elapsedMs * timeProperties.getTimeSpeed();
        long step = timeProperties.getStepMs();

        while (remainingMs > step) {
            clock.addMs(step);

            updateStuff(step);

            remainingMs -= step;
        }
        if (remainingMs > 0) {
            eventContext.allowEventFiring();
            clock.addMs(step);

            updateStuff(step);
        }
        // FOR TESTING
        timeProperties.setTimeSpeed(timeProperties.getTimeSpeed()+1);
        //

        lock.unlock();
    }
    private void updateStuff(long elapsedMs) {
        orderGenerator.generateOrder();
        kitchenManager.update(elapsedMs);
    }
    // - - - - - - - - - - - - - -
    public void setTimeSpeed(int timeSpeed) {
        lock.lock();
        timeProperties.setTimeSpeed(timeSpeed);
        lock.unlock();
    }
    // - - - - - - - - - - - - - -
    private void Initialize() {
        eventContext = new EventFiringContext();
        lock = new ReentrantLock(true);

        clock = new Clock(ZonedDateTime.of(
                        LocalDateTime.of(2024, 10, 1, 9, 0, 0),
                        ZoneId.systemDefault())
                .toInstant().toEpochMilli(), eventContext);
        timeProperties = new TimeProperties(60, 1000, eventContext);
    }
    // - - - - - - - - - - - - - -
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        eventContext.addPropertyChangeListener(listener);
    }
}
