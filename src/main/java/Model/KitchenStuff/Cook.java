package Model.KitchenStuff;

import java.util.HashMap;
import java.util.Map;
import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.DishReadiness;
import Model.FoodAndStuff.States.CookableState;

/**
 *  Let him cook
 */
public class Cook {
    private final Map<String, Boolean> stateMap;
    private boolean isActive;
    public Cook() {
        stateMap = new HashMap<>();
        initializeStates();
        isActive = true;
    }
    private void initializeStates() {
        stateMap.put("Dough preparation", true);
        stateMap.put("Preparing topping", true);
        stateMap.put("Baking", true);
        stateMap.put("Done", true);
        stateMap.put("Burned", false);
    }

    // Метод для перевірки, чи можна готувати певний стан
    private boolean canCook(String stateName) {
        return stateMap.getOrDefault(stateName, false);
    }
    // Метод готовки
    public DishReadiness cook(Cookable cookable, boolean cookPresent, long elapsedTime) {
        isActive = false;
        boolean isReady = false;
        // цикл для проходу всіх станів які може пригутувати кухар
        while (canCook(cookable.getStateName()))
        {
            double increaseFactor = ((double) (elapsedTime * 3) / cookable.getTotalPrepTimeMs()) * 100;
            cookable.increaseReadiness(increaseFactor);
            isReady = true;
        }
        isActive = true;
        return new DishReadiness(cookable, isReady);
    }
}


