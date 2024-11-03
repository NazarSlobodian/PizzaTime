package ViewModels;

import Model.Pizzeria;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainViewModel {
    private final Pizzeria simulator;

    // Time data
    private final StringProperty simDateTimeProperty;
    private final IntegerProperty simTimeSpeedProperty;

    //------------------------------------------------
    public MainViewModel(Pizzeria simulator) {
        this.simulator = simulator;

        simDateTimeProperty = new SimpleStringProperty("This shouldn't be visible on startup");
        simTimeSpeedProperty = new SimpleIntegerProperty(0);


        simulator.addPropertyChangeListener(evt-> {
            if ("simDateTime".equals(evt.getPropertyName())) {
                Platform.runLater(()->simDateTimeProperty.set((String)evt.getNewValue()));
                System.out.println(simDateTimeProperty.get());
            }
        });

        simulator.addPropertyChangeListener(evt-> {
            if ("simTimeSpeed".equals(evt.getPropertyName())) {
                Platform.runLater(()->simTimeSpeedProperty.set((int)evt.getNewValue()));
                System.out.println(simTimeSpeedProperty.get());
            }
        });
    }
    //------------------------------------------------
    public StringProperty simDateTimeProperty() {
        return simDateTimeProperty;
    }

    public IntegerProperty simTimeSpeedProperty() {
        return simTimeSpeedProperty;
    }
    public void setSimTimeSpeed(int simTimeSpeed) {
        simulator.setTimeSpeed(simTimeSpeed);
    }

}
