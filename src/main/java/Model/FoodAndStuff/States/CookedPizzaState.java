package Model.FoodAndStuff.States;

/**
 * State of cooked pizza. Final.
 * Maybe baked should be final - if its readiness gets over 100, consider pizza cooked?
 */
public class CookedPizzaState extends PizzaState {

    @Override
    public PizzaState getNextSuccessful() {
        return this;
    }

    @Override
    public PizzaState getNextFailed() {
        return new DoughPizzaState();
    }

    @Override
    public boolean isFinal() {
        return true;
    }

    @Override
    public String toString() {
        return "Done";
    }

}
