package Model.KitchenStuff;

import Model.Utils.Observable;

import java.util.List;
import java.util.Queue;

public interface Lobby extends Observable {
    void manageOrderFlow();

    void setGenerationStrategy(String name);

    List<String> getStrategies();

    Queue<Order> getRejectedOrders();

    List<Order> getAllOrders();

    void deleteQueue();

    void addQueue();

    int getQueuesCount();
}
