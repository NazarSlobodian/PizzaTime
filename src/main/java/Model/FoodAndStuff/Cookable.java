package Model.FoodAndStuff;

import Model.FoodAndStuff.States.CookableState;
import Model.Utils.Observable;

public interface Cookable extends Observable {
    /**
     *
     * @return whether cookable is ready for serving
     */
    boolean isCooked();

    /**
     *
     * @return readiness for current state (0-100%)
     */
    double getReadiness();

    long getTotalPrepTimeMs();

    void increaseReadiness(double value, boolean controlledCooking);
    void setName(String name);
    String getName();
    String getStateName();
    void setTotalPrepTimeMs(long totalPrepTimeMs);
    boolean cookableWithoutCook();
    // boolean shouldBeRecooked();
}
