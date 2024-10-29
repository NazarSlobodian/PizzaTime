package Model.FoodAndStuff.States;

/**
 * State of pizza being baked
 */
public class BakedPizzaState extends PizzaState {

    @Override
    public PizzaState getNextSuccessful() {
        return new CookedPizzaState();
        // Someone has to call isFinal. If it is, they shouldn't call this
    }
    @Override
    public PizzaState getNextFailed() {
        return new BurnedPizzaState();
    }
}
