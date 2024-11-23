package Model.KitchenStuff;

import java.util.ArrayList;
import java.util.List;

import Model.FoodAndStuff.Cookable;
import Model.Utils.Clock;
import Model.Utils.ObservableModel;

/**
 * Orders are given to kitchen manager
 */
public class Order extends ObservableModel {

    private final Clock clock;
    private final List<Cookable> items;
    private final long orderTime;
    private boolean ready = false;

    public Order(List<Cookable> items, Clock clock) {
        this.items = new ArrayList<>(items);
        this.orderTime = clock.getCurrentTime();
        this.clock = clock;
    }

    public Order(Order other, Clock clock) {
        this.items = new ArrayList<>(other.items);
        this.orderTime = other.orderTime;
        this.clock = clock;
    }

    public List<Cookable> getItems() {
        return items;
    }

    public boolean updateStatus() {
        for (Cookable c : items) {
            if (!c.isCooked()) {
                return false;
            }
        }
        markAsReady();
        return true;
    }

    private void markAsReady() {
        ready = true;
        eventContext.forceFirePropertyChange("orderReady", null, clock.getCurrentTime());
    }

    public long getOrderTime() {
        return orderTime;
    }

    public boolean isDone() {
        return ready;
    }

    @Override
    public String toString() {
        return "Order{" +
                "items=" + items +
                ", orderTime=" + orderTime +
                '}';
    }
}
