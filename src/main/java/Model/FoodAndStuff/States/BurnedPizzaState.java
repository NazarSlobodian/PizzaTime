package Model.FoodAndStuff.States;

/**
 * 🔥🔥🔥
 */
public class BurnedPizzaState extends PizzaState {

    @Override
    public PizzaState getNextSuccessful() {
        return new InitialPizzaState();
    }

    @Override
    public PizzaState getNextFailed() {
        return this;
    }

    @Override
    public boolean isFinal() {
        return true;
    }
    @Override
    public boolean isBad() {
        return true;
    }
}
