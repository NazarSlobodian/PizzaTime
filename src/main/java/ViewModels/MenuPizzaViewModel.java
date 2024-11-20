package ViewModels;

import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.Pizza;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MenuPizzaViewModel {

    private final StringProperty name;
    private final StringProperty minTimeProperty;

    public MenuPizzaViewModel(Cookable pizza) {
        this.name = new SimpleStringProperty(pizza.getName());
        this.minTimeProperty = new SimpleStringProperty(pizza.getTotalPrepTimeMs()/1000/60 + " minutes");
    }

    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty minTimeProperty() {
        return minTimeProperty;
    }
}
