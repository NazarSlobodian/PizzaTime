package ViewModels;

import Model.FoodAndStuff.Pizza;
import Model.Pizzeria;
import Model.Utils.Clock;
import Model.Utils.TimeProperties;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimTimeViewModel {

    private TimeProperties timeProperties;

    private final StringProperty simDateTimeProperty;
    private final IntegerProperty simTimeSpeedProperty;

    public SimTimeViewModel(Clock clock, TimeProperties timeProperties) {

        simDateTimeProperty = new SimpleStringProperty(clock.toString());
        simTimeSpeedProperty = new SimpleIntegerProperty(timeProperties.getTimeSpeed());

        clock.addPropertyChangeListener(evt-> {
            if ("simDateTime".equals(evt.getPropertyName())) {
                Platform.runLater(()->simDateTimeProperty.set((String)evt.getNewValue()));

                System.out.println(simDateTimeProperty.get());
            }
        });
        timeProperties.addPropertyChangeListener(evt-> {
            if ("simTimeSpeed".equals(evt.getPropertyName())) {
                Platform.runLater(()->simTimeSpeedProperty.set((int)evt.getNewValue()));

                System.out.println("Time speed " + simTimeSpeedProperty.get());
            }
        });
    }
    public StringProperty simDateTimeProperty() {
        return simDateTimeProperty;
    }

    public IntegerProperty simTimeSpeedProperty() {
        return simTimeSpeedProperty;
    }
    //------------------------------------------------

    public void setSimTimeSpeed(int simTimeSpeed) {
        timeProperties.setTimeSpeed(simTimeSpeed);
    }
}
