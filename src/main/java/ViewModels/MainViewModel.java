package ViewModels;

import Model.FoodAndStuff.Pizza;
import Model.Pizzeria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class MainViewModel {
    // ideally, this should be split
    private final SimTimeViewModel simTimeViewModel;
    private final MenuViewModel menuViewModel;

    private final ObservableList<SingularPizzaViewModel> pizzasInKitchen;
    // Time data

    //------------------------------------------------
    public MainViewModel(Pizzeria simulator) {
        simTimeViewModel = new SimTimeViewModel(simulator.getClock(), simulator.getTimeProperties());

        menuViewModel = new MenuViewModel(simulator.getMenu());

        this.pizzasInKitchen = FXCollections.observableArrayList();

        for (Pizza pizza : simulator.getPizzas()) {
            pizzasInKitchen.add(new SingularPizzaViewModel(pizza));
        }
    }

    public ObservableList<SingularPizzaViewModel> getPizzasInKitchen() {
        return pizzasInKitchen;
    }
}
