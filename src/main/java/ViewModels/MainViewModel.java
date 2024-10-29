package ViewModels;

import Model.SimulatorUpdater;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainViewModel {
    private final SimulatorUpdater simulator;

    private final StringProperty simDateTime;
    public StringProperty simDateTimeProperty() {
        return simDateTime;
    }

    public MainViewModel(SimulatorUpdater simulator) {
        this.simulator = simulator;

        simDateTime  = new SimpleStringProperty(simulator.getDateTime());

        simulator.addPropertyChangeListener(evt-> {
            if ("simDateTime".equals(evt.getPropertyName())) {
                simDateTime.set((String)evt.getNewValue());
                System.out.println(simDateTime.get());
            }
        });



    }




}
