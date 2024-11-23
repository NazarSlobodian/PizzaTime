package ViewModels;

import Model.KitchenStuff.Lobby;
import Model.KitchenStuff.Order;
import Model.KitchenStuff.Queues;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class QueuesViewModel {
    private final ObservableList<OrderViewModel> allOrders;
    public QueuesViewModel(Lobby lobby) {
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
}
