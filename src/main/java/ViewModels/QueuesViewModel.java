package ViewModels;

import Model.KitchenStuff.Lobby;
import Model.KitchenStuff.Order;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class QueuesViewModel {
    private final ObservableList<OrderViewModel> allOrders;
    Lobby lobby;
    public QueuesViewModel(Lobby lobby) {
        this.lobby = lobby;
        allOrders = FXCollections.observableArrayList();
        lobby.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("orderAdded")) {
                Platform.runLater(() -> {
                    allOrders.add(new OrderViewModel((Order)evt.getNewValue()));
                    System.out.println("ORDER ADDED");
                });
            }
        });

    }

    public ObservableList<OrderViewModel> getAllOrders() {
        return allOrders;
    }
    public void deleteQueue() {
        lobby.deleteQueue();
    }
    public void addQueue() {
        lobby.addQueue();
    }
}
