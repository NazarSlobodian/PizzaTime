package ViewModels;

import Model.KitchenStuff.Cook;
import Model.KitchenStuff.Cooker;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

import java.util.LinkedHashMap;
import java.util.Map;

public class CookerViewModel {
    BooleanProperty isPresent;
    Cooker cooker;
    MapProperty<String, Boolean> skills;

    public CookerViewModel(Cooker cooker) {
        this.cooker = cooker;
        isPresent = new SimpleBooleanProperty(cooker.isCookPresent());
        skills = new SimpleMapProperty<>(FXCollections.observableMap(cooker.getSkills()));


        cooker.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("statusChanged")) {
                isPresent.setValue((boolean)evt.getNewValue());
                System.out.println("Changed to "+(boolean)evt.getNewValue());
            }
        });
        cooker.addPropertyChangeListener((evt-> {
            if (evt.getPropertyName().equals("skillChanged")) {
                skills.put((String)evt.getOldValue(), (Boolean)evt.getNewValue());
                System.out.println("SKILL CHANGED "+(String)evt.getOldValue() +" TO " + (boolean)evt.getNewValue());
            }
        }));
    }

    public BooleanProperty getIsPresent() {
        return isPresent;
    }
    public MapProperty<String, Boolean> getSkills() {
        return skills;
    }
    public void setSkill(String name, boolean value) {
        cooker.setSkill(name, value);
    }
    public void setSkills(Map<String, Boolean> skills) {
        for (Map.Entry<String, Boolean> entry : skills.entrySet()) {
            setSkill(entry.getKey(), entry.getValue());
        }
    }

    public boolean boundTo(Cooker cooker) {
        return cooker == this.cooker;
    }
}
