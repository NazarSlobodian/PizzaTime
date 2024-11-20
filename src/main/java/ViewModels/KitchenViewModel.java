package ViewModels;

import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.Pizza;
import Model.KitchenStuff.KitchenManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class KitchenViewModel {
    private KitchenManager kitchenManager;

    private final ObservableList<KitchenPizzaViewModel> pizzasInKitchen;

    public KitchenViewModel(KitchenManager kitchenManager) {
        this.pizzasInKitchen = FXCollections.observableArrayList();

        this.kitchenManager = kitchenManager;
        kitchenManager.addPropertyChangeListener(evt-> {
            if (evt.getPropertyName().equals("cookableAdded")) {
                Platform.runLater(() -> {
                    pizzasInKitchen.add(new KitchenPizzaViewModel((Cookable) evt.getNewValue()));
                    System.out.println("Cookable added to kitchen");
                });
            }
        });
        kitchenManager.addPropertyChangeListener(evt-> {
            if (evt.getPropertyName().equals("cookableDeleted")) {
                Platform.runLater(()-> {
                    Cookable deleted = (Cookable)evt.getNewValue();
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
    }
    public ObservableList<KitchenPizzaViewModel> getPizzasInKitchen() {
        return pizzasInKitchen;
    }
}
