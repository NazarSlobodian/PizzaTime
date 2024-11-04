package ViewModels;

import Model.FoodAndStuff.Pizza;
import Model.Pizzeria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class MainViewModel {
    private final Pizzeria simulator;

    private final SimTimeViewModel simTimeViewModel;

    private final ObservableList<SingularPizzaViewModel> pizzasInKitchen;
    // Time data

    //------------------------------------------------
    public MainViewModel(Pizzeria simulator) {
        this.simulator = simulator;
        simTimeViewModel = new SimTimeViewModel(simulator, simulator.getClock(), simulator.getTimeProperties());

        this.pizzasInKitchen = FXCollections.observableArrayList();

        for (Pizza pizza : simulator.getPizzas()) {
            pizzasInKitchen.add(new SingularPizzaViewModel(pizza));
        }
    }

    public ObservableList<SingularPizzaViewModel> getPizzasInKitchen() {
        return pizzasInKitchen;
    }
}
