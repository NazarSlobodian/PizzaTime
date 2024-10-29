package Model.FoodAndStuff.States;

/**
 * State of cooked pizza. Final.
 * Maybe baked should be final - if its readiness gets over 100, consider pizza cooked?
 */
public class CookedPizzaState extends PizzaState {

    @Override
    public PizzaState getNextSuccessful() {
        return new CookedPizzaState();
    }

    @Override
    public PizzaState getNextFailed() {
        return new InitialPizzaState();
    }

    @Override
    public boolean isFinal() {
        return true;
    }

}
