package Model.FoodAndStuff.States;

/**
 * State of pizza being baked
 */
public class BakedPizzaState extends PizzaState {

    @Override
    public PizzaState getNextSuccessful() {
        return new CookedPizzaState();
    }
    @Override
    public PizzaState getNextFailed() {
        return new BurnedPizzaState();
    }

    @Override
    public String toString() {
        return "Baking";
    }
}
