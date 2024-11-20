package Model;

import Model.FoodAndStuff.Menu;
import Model.Generators.FlowGeneratorImpl;
import Model.Generators.OrderStrategyManager;
import Model.KitchenStuff.KitchenManager;
import Model.KitchenStuff.Queues;
import Model.Utils.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pizzeria extends ObservableModel {

    private Clock clock;
    private TimeProperties timeProperties;
    private Schedule schedule;

    private Queues queues;
    private Menu menu;
    private KitchenManager kitchenManager;

    private Lock lock;

    //------------------------------------------------
    public Pizzeria() {
        Initialize();
    }

    // - - - - - - - - - - - - - -
    public void update(long elapsedMs) {
        lock.lock();

        handleDayNightCycle(); // barely works, won't fix
        long remainingMs = elapsedMs * timeProperties.getTimeSpeed();
        long step = timeProperties.getStepMs();

        setAllNotifications(false);

        while (remainingMs > step) {
            clock.addMs(step);

            updateStuff(step);

            remainingMs -= step;
        }
        if (remainingMs > 0) {
            setAllNotifications(true);
            clock.addMs(step);

            updateStuff(step);
        }

        lock.unlock();
    }

    private void handleDayNightCycle() {
        if (!schedule.isOpen(clock.getLocalDateTime())) {
            timeProperties.setSkippingTime(true);
        } else if (timeProperties.isSkippingTime()) {
            timeProperties.setSkippingTime(false);
        }
    }

    private void updateStuff(long elapsedMs) {
        if (timeProperties.isSkippingTime()) {
            return;
        }
        queues.manageOrderFlow();
        kitchenManager.update(elapsedMs);
    }

    private void setAllNotifications(boolean setting) {
        this.eventContext.setEventFiring(setting);
        clock.setNotifications(setting);
        timeProperties.setNotifications(setting);
        kitchenManager.setNotifications(setting);
        menu.setNotifications(setting);
    }

    // - - - - - - - - - - - - - -
    private void Initialize() {
        lock = new ReentrantLock(true);

        clock = new Clock(ZonedDateTime.of(
                        LocalDateTime.of(2024, 10, 1, 10, 0, 0),
                        ZoneId.systemDefault())
                .toInstant().toEpochMilli());
        timeProperties = new TimeProperties(60, 1000, lock);
        schedule = new Schedule();
        menu = new Menu(lock);

        kitchenManager = new KitchenManager(clock);
        queues = new Queues(schedule, new OrderStrategyManager(menu, clock), kitchenManager, clock);
    }

    // For view model
    public Clock getClock() {
        return clock;
    }

    public TimeProperties getTimeProperties() {
        return timeProperties;
    }

    public Menu getMenu() {
        return menu;
    }

    public KitchenManager getKitchen() {
        return kitchenManager;
    }

    public Logger getLogger() {
        return kitchenManager.getLogger();
    }
}
