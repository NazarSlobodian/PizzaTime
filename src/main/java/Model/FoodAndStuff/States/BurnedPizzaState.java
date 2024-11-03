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
        return  new InitialPizzaState();
    }

    @Override
    public boolean isFinal() {
        return true;
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
