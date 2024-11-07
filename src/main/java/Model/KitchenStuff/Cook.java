package Model.KitchenStuff;

import java.util.HashMap;
import java.util.Map;
import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.DishReadiness;
import Model.Utils.ObservableModel;

/**
 *  Let him cook
 */
public class Cook extends ObservableModel { // Успадковуємо ObservableModel
    private final Map<String, Boolean> stateMap; // Карта для зберігання доступних станів приготування
    private boolean isActive; // Показує, чи активний кухар
    private boolean cookPresent; // Показує, чи присутній кухар

    public Cook() {
        stateMap = new HashMap<>();
        initializeStates();
        isActive = true; // Початково кухар активний
        cookPresent = true; // За замовчуванням кухар присутній
    }

    private void initializeStates() {
        stateMap.put("Dough preparation", true);
        stateMap.put("Preparing topping", true);
        stateMap.put("Baking", true);
        stateMap.put("Done", true);
        stateMap.put("Burned", false);
    }

    // Перевіряє, чи можна готувати до заданого стану
    private boolean canCook(String stateName) {
        return stateMap.getOrDefault(stateName, false);
    }

    // Геттер для cookPresent
    public boolean isCookPresent() {
        return cookPresent;
    }

    // Сеттер для cookPresent з викликом forceFirePropertyChange
    public void setCookPresent(boolean cookPresent) {
        boolean oldCookPresent = this.cookPresent;
        this.cookPresent = cookPresent;
        eventContext.forceFirePropertyChange("cookPresent", oldCookPresent, cookPresent); // Виклик forceFirePropertyChange
    }

    // Метод готовки
    public DishReadiness cook(Cookable cookable, long elapsedTime) {
        isActive = false; // Встановлюємо, що кухар неактивний на час приготування

        // Перевірка, чи можна готувати поточний стан
        if (canCook(cookable.getStateName())) {
            // Розрахунок приросту готовності
            double increaseFactor = ((double) (elapsedTime * 3) / cookable.getTotalPrepTimeMs()) * 100;

            // Викликаємо increaseReadiness, передаючи тільки приріст готовності
            cookable.increaseReadiness(increaseFactor, cookPresent);

            // Перевіряємо, чи страва готова після збільшення рівня готовності
            boolean isReady = cookable.getReadiness() >= 100 && cookable.isCooked();

            isActive = true; // Після приготування кухар стає активним
            return new DishReadiness(cookable, isReady);
        }

        // Якщо кухар не може готувати цей стан, повертаємо DishReadiness з готовністю false
        isActive = true; // Після завершення кухар стає активним
        return new DishReadiness(cookable, false);

    }

}
