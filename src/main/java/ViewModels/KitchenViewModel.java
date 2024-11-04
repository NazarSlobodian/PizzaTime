package ViewModels;

import Model.FoodAndStuff.Pizza;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class KitchenViewModel {
    private final ObservableList<KitchenPizzaViewModel> pizzasInKitchen;

    public KitchenViewModel(List<Pizza> pizzas) {
        this.pizzasInKitchen = FXCollections.observableArrayList();

        for (Pizza pizza :pizzas) {
            pizzasInKitchen.add(new KitchenPizzaViewModel(pizza));
        }
    }
    public ObservableList<KitchenPizzaViewModel> getPizzasInKitchen() {
        return pizzasInKitchen;
    }
}
