package Model.FoodAndStuff;

public interface Cookable {
    /**
     * Cooks.
     * If controlledCooking is false and readiness gets to 100%, in some cases preparation may be failed
     * If status is 'baking', then after 100% (or a bit more), status becomes 'burned' and pizza (or other food) has to be made from start
     */
    void cook(boolean controlledCooking, long elapsedTime);

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
}
