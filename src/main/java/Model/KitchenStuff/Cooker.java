package Model.KitchenStuff;

import Model.FoodAndStuff.Cookable;

public interface Cooker {
    // Перевіряє, чи можна готувати до заданого стану
    boolean canCook(Cookable cookable);

    // Геттер для cookPresent
    boolean isCookPresent();
    // Сеттер для cookPresent з викликом forceFirePropertyChange
    void setCookPresent(boolean cookPresent);

    // Метод готовки
    boolean cook(Cookable cookable, long elapsedTime);
}
