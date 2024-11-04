package Model.FoodAndStuff.States;

/**
 * State of dough preparation. Next -> ???
 */
public class DoughPizzaState extends PizzaState {

    @Override
    public PizzaState getNextSuccessful() {
        return new ToppingPizzaState();
    }

    @Override
    public PizzaState getNextFailed() {
        return new DoughPizzaState();
    }

    @Override
    public String toString() {
        return "Dough preparation";
    }

}
