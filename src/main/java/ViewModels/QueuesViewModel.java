package ViewModels;

import Model.KitchenStuff.Lobby;
import Model.KitchenStuff.Order;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class QueuesViewModel {
    private final ObservableList<OrderViewModel> allOrders;


    private final Lobby lobby;
    private final IntegerProperty queuesCount;

    private final IntegerProperty totalOrdersGenerated;

    private final ObservableList<String> strats;

    public QueuesViewModel(Lobby lobby) {
        this.lobby = lobby;

        totalOrdersGenerated = new SimpleIntegerProperty(0);
        allOrders = FXCollections.observableArrayList();
        strats = FXCollections.observableArrayList();

        List<String> strts = lobby.getStrategies();
        for (String str : strts) {
            strats.add(str);
        }

        lobby.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("orderAdded")) {
                Platform.runLater(() -> {
                    allOrders.add(new OrderViewModel((Order)evt.getNewValue()));
                    totalOrdersGenerated.set(totalOrdersGenerated.get() + 1);
                    System.out.println("ORDER ADDED");
                });
            }
        });

        queuesCount = new SimpleIntegerProperty(lobby.getQueuesCount());
        lobby.addPropertyChangeListener(evt-> {
            if (evt.getPropertyName().equals("queuesCountChanged")) {
                Platform.runLater(() -> {
                    queuesCount.set((int)evt.getNewValue());
                });
            }
        });
    }

    public ObservableList<OrderViewModel> getAllOrders() {
        return allOrders;
    }


    public IntegerProperty queuesCount() {
        return queuesCount;
    }

    public IntegerProperty totalOrdersGenerated() {
        return totalOrdersGenerated;
    }

    public void deleteQueue() {
        lobby.deleteQueue();
    }
    public void addQueue() {
        lobby.addQueue();
    }

    public ObservableList<String> getStrats() {
        return strats;
    }
    public void setStrat(String name){
        lobby.setGenerationStrategy(name);
    }

}
