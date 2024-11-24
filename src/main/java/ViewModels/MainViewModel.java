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
    private final LogViewModel logViewModel;
    private final QueuesViewModel queuesViewModel;
    // Time data

    //------------------------------------------------
    public MainViewModel(Pizzeria simulator) {
        simTimeViewModel = new SimTimeViewModel(simulator.getClock(), simulator.getTimeProperties());

        menuViewModel = new MenuViewModel(simulator.getMenu());
        kitchenViewModel = new KitchenViewModel(simulator.getKitchen());
        logViewModel = new LogViewModel(simulator.getLogger());
        queuesViewModel = new QueuesViewModel(simulator.getLobby());
    }

    public SimTimeViewModel getSimTimeViewModel() {
        return simTimeViewModel;
    }

    public KitchenViewModel getKitchenViewModel() {
        return kitchenViewModel;
    }

    public MenuViewModel getMenuViewModel() {
        return menuViewModel;
    }

    public QueuesViewModel getQueuesViewModel(){
        return queuesViewModel;
    }

    public LogViewModel getLogViewModel() {
        return logViewModel;
    }
}
