package ViewModels;

import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.Pizza;
import Model.KitchenStuff.Cook;
import Model.KitchenStuff.Cooker;
import Model.KitchenStuff.KitchenManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class KitchenViewModel {
    private KitchenManager kitchenManager;

    private final ObservableList<KitchenPizzaViewModel> pizzasInKitchen;
    private final ObservableList<CookerViewModel> cooks;

    public KitchenViewModel(KitchenManager kitchenManager) {
        this.pizzasInKitchen = FXCollections.observableArrayList();
        this.cooks = FXCollections.observableArrayList();

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
                    System.out.println("Cook added");
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
}
