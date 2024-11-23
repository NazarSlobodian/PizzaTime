package Model.KitchenStuff;

import java.util.Map;

import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.StateRegistry;
import Model.Utils.ObservableModel;

/**
 * Let him cook
 */
public class Cook extends ObservableModel implements Cooker {
    private final Map<String, Boolean> stateMap;
    private boolean cookPresent;

    public Cook() {
        stateMap = StateRegistry.getStateMap();
        cookPresent = true;
    }

    @Override
    public boolean canCook(Cookable cookable) {
        return stateMap.getOrDefault(cookable.getStateName(), false);
    }


    @Override
    public boolean isCookPresent() {
        return cookPresent;
    }

    @Override
    public void setCookPresent(boolean cookPresent) {
        this.cookPresent = cookPresent;
        eventContext.forceFirePropertyChange("statusChanged", null, cookPresent);
    }

    // Метод готовки
    @Override
    public void cook(Cookable cookable, long elapsedTime) {
        // Перевірка, чи можна готувати поточний стан

        // Розрахунок приросту готовності
        double increaseFactor = ((double) (elapsedTime * 3) / cookable.getTotalPrepTimeMs()) * 100;
        cookable.increaseReadiness(increaseFactor, cookPresent);

    }
    @Override
    public Map<String, Boolean> getSkills() {
        return stateMap;
    }
    @Override
    public void setSkill(String name, boolean flag) {
        stateMap.put(name, flag);
        eventContext.forceFirePropertyChange("skillChanged", name, flag);
    }
}
