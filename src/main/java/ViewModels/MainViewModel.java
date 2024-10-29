package ViewModels;

import Model.Pizzeria;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainViewModel {
    private final Pizzeria simulator;

    private final StringProperty simDateTimeProperty;

    //------------------------------------------------
    public MainViewModel(Pizzeria simulator) {
        this.simulator = simulator;
        simDateTimeProperty = new SimpleStringProperty("This shouldn't be visible on startup");

        simulator.addPropertyChangeListener(evt-> {
            if ("simDateTime".equals(evt.getPropertyName())) {
                simDateTimeProperty.set((String)evt.getNewValue());

                System.out.println(simDateTimeProperty.get());
            }
        });
    }
    //------------------------------------------------
    public StringProperty simDateTimeProperty() { //view should bind to this?
        return simDateTimeProperty;
    }
}
