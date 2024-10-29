package Model.FoodAndStuff.States;

/**
 * Initial state of pizza. Next -> ???
 */
public class InitialPizzaState extends PizzaState {

    @Override
    public double getReadiness() {
        return 100.0;
    }

    @Override
    public PizzaState getNextSuccessful() {
        return new DoughPizzaState();
    }

    @Override
    public PizzaState getNextFailed() {
        return new InitialPizzaState();
    }

}
