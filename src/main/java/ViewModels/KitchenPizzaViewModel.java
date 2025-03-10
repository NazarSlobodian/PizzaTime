package ViewModels;

import Model.FoodAndStuff.Cookable;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KitchenPizzaViewModel {

    private final Cookable cookable;
    private final StringProperty name;
    private final StringProperty stateProperty;
    private final StringProperty readinessProperty;

    public KitchenPizzaViewModel(Cookable cookable) {
        this.cookable = cookable;
        this.name = new SimpleStringProperty(cookable.getName());
        this.stateProperty = new SimpleStringProperty(cookable.getStateName());
        this.readinessProperty = new SimpleStringProperty(String.format("%.2f", cookable.getReadiness()) + "%");

        cookable.addPropertyChangeListener(evt-> {
            if (evt.getPropertyName().equals("pizzaStateChanged")) {
                Platform.runLater(()->{
                    stateProperty.setValue(evt.getNewValue().toString());
                    System.out.println(name.getValue() + " state " + stateProperty.getValue());
                });
            }
        });
        cookable.addPropertyChangeListener(evt-> {
            if (evt.getPropertyName().equals("pizzaStateReadinessChanged")) {
                Platform.runLater(()-> {
                    readinessProperty.setValue(String.format("%.2f", (double)evt.getNewValue())+"%");
                    System.out.println(name.getValue() + " " + stateProperty.getValue() + " " + readinessProperty.getValue());
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

    public boolean boundTo(Cookable cookable) {
        return cookable== this.cookable;
    }
}
