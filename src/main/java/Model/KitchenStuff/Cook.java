package Model.KitchenStuff;

import java.util.HashMap;
import java.util.Map;
import Model.FoodAndStuff.Cookable;
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
        isActive = true; // Початково кухар активний
    }
    private void initializeStates() {
        stateMap.put("Dough preparation", true); // Стан підготовки тіста
        stateMap.put("Preparing topping", true); // Стан додавання начинки
        stateMap.put("Baking", true); // Стан випікання
        stateMap.put("Done", true); // Стан готовності
        stateMap.put("Burned", false); // Стан "підгоріло", до якого не можна свідомо перейти
    }

    // Метод для перевірки, чи можна готувати до зазначеного стану
    private boolean canCook(String stateName) {
        return stateMap.getOrDefault(stateName, false); // Повертаємо true, якщо можна готувати, інакше false
    }

    public boolean cook(Cookable cookable, boolean cookPresent, long elapsedTime) {
        isActive = false;

        // Перевірка, чи дозволяє стан приготування
        if (canCook(cookable.getStateName()))
        { // Викликаємо canCook з поточним станом cookable
            double increaseFactor = ((double) (elapsedTime * 3) / cookable.getTotalPrepTimeMs()) * 100;
            cookable.increaseReadiness(increaseFactor); // Збільшуємо рівень готовності

            isActive = true; // Після приготування кухар стає активним
            return cookNextState(cookable); // Переходимо до наступного стану
        } else {
            isActive = true; // Кухар активний, якщо не можна готувати до заданого стану
            return false;
        }
    }
    // Рекурсивний метод для обробки наступного стану приготування
    private boolean cookNextState(Cookable cookable) {

     return false;
    }



}


