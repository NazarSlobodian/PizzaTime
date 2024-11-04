package Model.FoodAndStuff.States;

/**
 * 🔥🔥🔥
 */
public class BurnedPizzaState extends PizzaState {

    @Override
    public PizzaState getNextSuccessful() {
        return new DoughPizzaState();
    }

    @Override
    public PizzaState getNextFailed() {
        return  new DoughPizzaState();
    }

    @Override
    public boolean isBad() {
        return true;
    }

    @Override
    public String toString() {
        return "Burned";
    }
}
