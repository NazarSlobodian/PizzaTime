package ViewModels;

import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.Pizza;
import Model.KitchenStuff.Cook;
import Model.KitchenStuff.Cooker;
import Model.KitchenStuff.KitchenManager;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class KitchenViewModel {
    private KitchenManager kitchenManager;

    private final ObservableList<KitchenPizzaViewModel> pizzasInKitchen;
    private final ObservableList<CookerViewModel> cooks;

    private final IntegerProperty ordersInKitchen;
    private final IntegerProperty ordersDone;

    public KitchenViewModel(KitchenManager kitchenManager) {
        this.pizzasInKitchen = FXCollections.observableArrayList();
        this.cooks = FXCollections.observableArrayList();

        ordersInKitchen = new SimpleIntegerProperty();
        ordersDone = new SimpleIntegerProperty();
        List<Cooker> cooks = kitchenManager.getCooks();
        for (Cooker cook : cooks) {
            this.cooks.add(new CookerViewModel(cook));
        }

        this.kitchenManager = kitchenManager;
        kitchenManager.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("cookableAdded")) {
                Platform.runLater(() -> {
                    pizzasInKitchen.add(new KitchenPizzaViewModel((Cookable) evt.getNewValue()));
                    System.out.println("Cookable added to kitchen");
                });
            }
        });
        kitchenManager.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("cookableDeleted")) {
                Platform.runLater(() -> {
                    Cookable deleted = (Cookable) evt.getNewValue();
                    for (KitchenPizzaViewModel v : pizzasInKitchen) {
                        if (v.boundTo(deleted)) {
                            pizzasInKitchen.remove(v);
                            break;
                        }
                    }
                    System.out.println("Cookable " + evt.getNewValue() + " removed from kitchen");
                });
            }
        });
        kitchenManager.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("cookAdded")) {
                Platform.runLater(() -> {
                    this.cooks.add(new CookerViewModel((Cooker) evt.getNewValue()));
                    System.out.println("Cook added");
                });
            }
        });
        kitchenManager.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("cookDeleted")) {
                Platform.runLater(() -> {
                    Cooker deleted = (Cook) evt.getNewValue();
                    for (CookerViewModel v : this.cooks) {
                        if (v.boundTo(deleted)) {
                            this.cooks.remove(v);
                            break;
                        }
                    }
                    System.out.println("Cook deleted");
                });
            }
        });
        kitchenManager.addPropertyChangeListener(evt-> {
            if (evt.getPropertyName().equals("ordersInKitchenChanged")) {
                Platform.runLater(() -> {
                    ordersInKitchen.setValue((int)evt.getNewValue());
                    System.out.println("Changed orders in kitchen to some number");
                });
            }
        });
        kitchenManager.addPropertyChangeListener(evt-> {
            if (evt.getPropertyName().equals("orderDone")) {
                Platform.runLater(() -> {
                    ordersDone.setValue(ordersDone.getValue() + 1);
                    System.out.println("Orders done incremented");
                });
            }
        });
    }

    public ObservableList<KitchenPizzaViewModel> getPizzasInKitchen() {
        return pizzasInKitchen;
    }
    public ObservableList<CookerViewModel> getCooks() {
        return cooks;
    }

    public IntegerProperty getOrdersInKitchen() {
        return ordersInKitchen;
    }
    public IntegerProperty getOrdersDone() {
        return ordersDone;
    }

    public void stopCook(int index) {
        kitchenManager.stopCooker(index);
    }
    public void startCook(int index) {
        kitchenManager.startCooker(index);
    }
    public void deleteCook(int index) {
        kitchenManager.deleteCook(index);
    }
    public void addCook() {
        kitchenManager.addCook();
    }
}
