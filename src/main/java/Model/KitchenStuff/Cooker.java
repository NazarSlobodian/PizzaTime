package Model.KitchenStuff;

import Model.FoodAndStuff.Cookable;
import Model.Utils.Observable;

import java.util.Map;

public interface Cooker extends Observable {
    // Перевіряє, чи можна готувати до заданого стану
    boolean canCook(Cookable cookable);

    // Геттер для cookPresent
    boolean isCookPresent();
    // Сеттер для cookPresent з викликом forceFirePropertyChange
    void setCookPresent(boolean cookPresent);

    // Метод готовки
    void cook(Cookable cookable, long elapsedTime);

    Map<String, Boolean> getSkills();

    void setSkill(String name, boolean flag);
}
