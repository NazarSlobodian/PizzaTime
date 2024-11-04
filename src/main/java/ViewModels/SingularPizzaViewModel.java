package ViewModels;

import Model.FoodAndStuff.Pizza;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SingularPizzaViewModel {

    private final StringProperty name;
    private final StringProperty stateProperty;
    private final StringProperty readinessProperty;

    public SingularPizzaViewModel(Pizza pizza) {
        this.name = new SimpleStringProperty(pizza.getName());
        this.stateProperty = new SimpleStringProperty(pizza.getState().toString());
        this.readinessProperty = new SimpleStringProperty(pizza.getReadiness() + "%");

        pizza.addPropertyChangeListener(evt-> {
            if (evt.getPropertyName().equals("pizzaStateChanged")) {
                Platform.runLater(()->{
                    stateProperty.setValue(evt.getNewValue().toString());
                    System.out.println("Pizza "+ name.getValue() + " state " + stateProperty.getValue());
                });
            }
        });
        pizza.addPropertyChangeListener(evt-> {
            if (evt.getPropertyName().equals("pizzaStateReadinessChanged")) {
                Platform.runLater(()-> {
                    readinessProperty.setValue(evt.getNewValue()+"%");
                    System.out.println("Pizza " + name.getValue()+" readiness " + readinessProperty.getValue());
                });
            }
        });
    }

    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty stateProperty() {
        return stateProperty;
    }
    public StringProperty readinessProperty() {
        return readinessProperty;
    }

}
