package Model;

import Model.FoodAndStuff.Menu;
import Model.FoodAndStuff.Pizza;
import Model.Generators.OrderGenerator;
import Model.KitchenStuff.Cook;
import Model.KitchenStuff.KitchenManager;
import Model.Utils.Clock;
import Model.Utils.ObservableModel;
import Model.Utils.Schedule;
import Model.Utils.TimeProperties;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pizzeria extends ObservableModel {

    private Clock clock;
    TimeProperties timeProperties;
    Schedule schedule;

    OrderGenerator orderGenerator;
    Menu menu;
    KitchenManager kitchenManager;

    //TESTING
    List<Pizza> pizzas;
    //TESTING IN PROD

    private Lock lock;

    //------------------------------------------------
    public Pizzeria() {
        Initialize();
    }

    // - - - - - - - - - - - - - -
    public void update(long elapsedMs) {

        lock.lock();

        setAllNotifications(false);
        if (!schedule.isOpen(clock.getLocalDateTime())) {
            timeProperties.setSkippingTime(true);

        } else if (timeProperties.isSkippingTime()) {
            timeProperties.setSkippingTime(false);
        }

        long remainingMs = elapsedMs * timeProperties.getTimeSpeed();
        long step = timeProperties.getStepMs();

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

    private void updateStuff(long elapsedMs) {
        //orderGenerator.generateOrder();
        //kitchenManager.update(elapsedMs);
        if (timeProperties.isSkippingTime()) { // fine for now
            return;
        }
        // TESTING

        Cook cook = new Cook();
        cook.cook(pizzas.get(0), elapsedMs);
        if (pizzas.get(0).isCooked()) {
            System.out.println("TIME OF FINISH: " + clock.toString());
            pizzas.remove(0);
        }
    }

    public Clock getClock() {
        return clock;
    }

    public TimeProperties getTimeProperties() {
        return timeProperties;
    }

    //TESTING
    public List<Pizza> getPizzas() {
        return pizzas;
    }
    //TESTING

    public Menu getMenu() {
        return menu;
    }
    // - - - - - - - - - - - - - -
    private void Initialize() {
        lock = new ReentrantLock(true);

        clock = new Clock(ZonedDateTime.of(
                        LocalDateTime.of(2024, 10, 1, 9, 0, 1),
                        ZoneId.systemDefault())
                .toInstant().toEpochMilli());
        timeProperties = new TimeProperties(60, 1000, lock);
        schedule = new Schedule();
        menu = new Menu(lock);

        //
        pizzas = menu.getPizzas();
        //
    }

    private void setAllNotifications(boolean setting) {
        this.eventContext.setEventFiring(setting);
        clock.setNotifications(setting);
        timeProperties.setNotifications(setting);
        for (Pizza pizza : pizzas) {
            pizza.setNotifications(setting);
        }
        menu.setNotifications(setting);
    }
}
