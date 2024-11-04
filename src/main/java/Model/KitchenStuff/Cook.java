package Model.KitchenStuff;

import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.States.CookableState;

/**
 * Let him cook
 */
public class Cook {

    /**
     *
     * @param cookable food item
     * @return whether cook can cook some item (based on his skills and state of pizza)
     */
    public boolean canCook(Cookable cookable) {
        return false;
    }
    public void cook(Cookable cookable, boolean cookPresent, long elapsedTime) {

        if (!cookable.cookableWithoutCook() && !cookPresent) {
            return;
        }
        double increaseFactor = ((double) (elapsedTime * 3) / cookable.getTotalPrepTimeMs()) * 100;
        cookable.increaseReadiness(increaseFactor);
    }
}
