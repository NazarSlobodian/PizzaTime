package Model.KitchenStuff;

import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.DishReadiness;

public interface Cooker {
    // Перевіряє, чи можна готувати до заданого стану
    boolean canCook(String stateName);

    // Геттер для cookPresent
    boolean isCookPresent();

    boolean isActive();

    // Сеттер для cookPresent з викликом forceFirePropertyChange
    void setCookPresent(boolean cookPresent);

    // Метод готовки
    DishReadiness cook(Cookable cookable, long elapsedTime);
}
