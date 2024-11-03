package Model.FoodAndStuff.States;

public class ToppingPizzaState extends PizzaState {

    @Override
    public PizzaState getNextSuccessful() {
        return new BakedPizzaState();
    }

    @Override
    public PizzaState getNextFailed() {
        return new InitialPizzaState();
    }

    @Override
    public String toString() {
        return "Preparing topping";
    }
}
