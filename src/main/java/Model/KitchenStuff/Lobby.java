package Model.KitchenStuff;

import Model.Utils.Observable;

import java.util.List;
import java.util.Queue;

public interface Lobby extends Observable {
    void manageOrderFlow();

    Queue<Order> getRejectedOrders();

    List<Order> getAllOrders();
}
