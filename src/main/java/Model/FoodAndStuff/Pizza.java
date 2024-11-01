package Model.FoodAndStuff;

import Model.FoodAndStuff.States.InitialPizzaState;
import Model.FoodAndStuff.States.PizzaState;

public class Pizza extends Dish implements Cloneable, Cookable {

    private PizzaState state;

    public Pizza() {
        this.state = new InitialPizzaState();
    }

    public Pizza(String name, long preparationTimeLeft) {
        super(name,preparationTimeLeft);
        this.state = new InitialPizzaState();
    }
    public Pizza(String name, long preparationTimeLeft, CookingDifficulty difficulty) {
        super(name,preparationTimeLeft, difficulty);
        this.state = new InitialPizzaState();
    }

    public PizzaState getState() {
        return state;
    }

    @Override
    public Pizza clone() {
        //that's a shallow copy, not deep
        try {
            return (Pizza) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Failed to clone Pizza", e);
        }
    }

    @Override
    public void cook(boolean controlledCooking, long elapsedTime) {
        if (controlledCooking) {
            // cook normally
        }
        else {
            // under some circumstances, go to bad state
        }
    }

    /**
     *
     * @return true if pizza can be served
     */
    @Override
    public boolean isCooked() {
        return state.isFinal() && !state.isBad();
    }

    /**
     *
     * @return readiness of pizza (%)
     */
    @Override
    public double getReadiness() {
        return state.getReadiness();
    }
}