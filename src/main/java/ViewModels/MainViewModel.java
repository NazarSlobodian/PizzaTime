package ViewModels;

import Model.FoodAndStuff.Pizza;
import Model.Pizzeria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class MainViewModel {
    // ideally, this should be split
    private final SimTimeViewModel simTimeViewModel;
    private final MenuViewModel menuViewModel;
    private final KitchenViewModel kitchenViewModel;
    // Time data

    //------------------------------------------------
    public MainViewModel(Pizzeria simulator) {
        simTimeViewModel = new SimTimeViewModel(simulator.getClock(), simulator.getTimeProperties());

        menuViewModel = new MenuViewModel(simulator.getMenu());
        kitchenViewModel = new KitchenViewModel(simulator.getKitchen()); // replace with real list/object later
    }

    public SimTimeViewModel getSimTimeViewModel() {
        return simTimeViewModel;
    }
}
