package ViewModels;

import Model.FoodAndStuff.Pizza;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MenuPizzaViewModel {

    private final StringProperty name;
    private final StringProperty stateProperty;
    private final StringProperty minTimeProperty;

    public MenuPizzaViewModel(Pizza pizza) {
        this.name = new SimpleStringProperty(pizza.getName());
        this.stateProperty = new SimpleStringProperty(pizza.getState().toString());
        this.minTimeProperty = new SimpleStringProperty(pizza.getTotalPrepTimeMs()/1000/60 + " minutes");
    }

    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty stateProperty() {
        return stateProperty;
    }
    public StringProperty minTimeProperty() {
        return minTimeProperty;
    }
}
