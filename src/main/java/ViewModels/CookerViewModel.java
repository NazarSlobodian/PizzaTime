package ViewModels;

import Model.KitchenStuff.Cook;
import Model.KitchenStuff.Cooker;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CookerViewModel {
    BooleanProperty isPresent;
    Cooker cooker;
    public CookerViewModel(Cooker cooker) {
        this.cooker = cooker;
        isPresent = new SimpleBooleanProperty(cooker.isCookPresent());

        cooker.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("statusChanged")) {
                isPresent.setValue((boolean)evt.getNewValue());
                System.out.println("Changed to "+(boolean)evt.getNewValue());
            }
        });
    }

    public BooleanProperty getIsPresent() {
        return isPresent;
    }

    public boolean boundTo(Cooker cooker) {
        return cooker == this.cooker;
    }
}
