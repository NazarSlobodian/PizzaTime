package Model.KitchenStuff;

import java.util.Map;
import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.StateRegistry;
import Model.Utils.Logger;
import Model.Utils.ObservableModel;

/**
 *  Let him cook
 */
public class Cook extends ObservableModel implements Cooker {
    private final Map<String, Boolean> stateMap;
    private boolean isActive;
    private boolean cookPresent;

    public Cook() {
        stateMap = StateRegistry.getStateMap();
        isActive = true;
        cookPresent = true;
    }

    @Override
    public boolean canCook(String stateName) {
        return stateMap.getOrDefault(stateName, false);
    }


    @Override
    public boolean isCookPresent() {
        return cookPresent;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }


    @Override
    public void setCookPresent(boolean cookPresent) {
        boolean oldCookPresent = this.cookPresent;
        this.cookPresent = cookPresent;
        eventContext.forceFirePropertyChange("cookPresent", oldCookPresent, cookPresent);
    }

    // Метод готовки
    @Override

    public boolean cook(Cookable cookable, long elapsedTime) {
        isActive = false; // Встановлюємо, що кухар неактивний на час приготування

        // Перевірка, чи можна готувати поточний стан
        if (canCook(cookable.getStateName())) {
            // Логування початку приготування
            if ("Dough preparation".equals(cookable.getStateName())) {
                Logger.logStartCooking(cookable.getName());
            }

            // Розрахунок приросту готовності
            double increaseFactor = ((double) (elapsedTime * 3) / cookable.getTotalPrepTimeMs()) * 100;
            cookable.increaseReadiness(increaseFactor, cookPresent);

            isActive = true; // Після приготування кухар стає активним
            return true;
        }
        // Якщо кухар не може готувати цей стан, повертаємо DishReadiness з готовністю false
        isActive = true; // Після завершення кухар стає активним
        return false;
    }
}
