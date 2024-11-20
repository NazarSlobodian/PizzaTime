package Model.KitchenStuff;

import java.util.ArrayList;
import java.util.List;

import Model.FoodAndStuff.Cookable;

/**
 * Orders are given to kitchen manager
 */
public class Order {

    private List<Cookable> items;
    private final long orderTime;

    public Order(List<Cookable> items, long orderTime) {
        this.items = new ArrayList<>(items);
        this.orderTime = orderTime;
    }

    public Order(Order other) {
        this.items = new ArrayList<>(other.items);
        this.orderTime = other.orderTime;
    }

    public List<Cookable> getItems() {
        return items;
    }

    public long getOrderTime() {
        return orderTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "items=" + items +
                ", orderTime=" + orderTime +
                '}'; 
    }
}
