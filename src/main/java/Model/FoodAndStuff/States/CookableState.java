package Model.FoodAndStuff.States;

/**
 * Represents state of food and its relations with other states
 */
public interface CookableState extends Cloneable {

    /**
     *
     * @return readiness for current state (0-100%)
     */

    double getReadiness();

    /**
     * When cooked, pizza readiness gets increased a bit.
     * @param by Increase value. Should be calculating using elapsedMs and pizzaCooking time (which is minimum time, so it can be randomly increased a bit)
     */
    void increaseReadiness(double by);

    /**
     * Moves state to next if everything is ok
     */
    CookableState getNextSuccessful();

    /**
     * Moves state to initial or special failed (i.e. 'burned')
     */
    CookableState getNextFailed();

    /**
     *
     * @return whether state is last (either positive last ('cooked'), or negative ('failed'))
     */
    boolean isFinal();

    /**
     * If it has to be cooked anew. Maybe its redundant
     * @return is bad or not
     */
    boolean isBad();
    /**
     * Should vary depending on readiness ('baked' if 100%, 'in oven' if 0-99%)
     * @return name of state
     */
    @Override
    String toString();
}
