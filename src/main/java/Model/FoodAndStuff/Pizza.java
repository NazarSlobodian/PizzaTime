package Model.FoodAndStuff;

import Model.FoodAndStuff.States.InitialPizzaState;
import Model.FoodAndStuff.States.PizzaState;

public class Pizza implements Cloneable, Cookable {

    private String name;
    private long preparationTimeLeft;
    private PizzaState state;

    public Pizza() {
        this.state = new InitialPizzaState();
    }

    public Pizza(String name, int preparationTimeLeft) {
        this();
        this.name = name;
        this.preparationTimeLeft = preparationTimeLeft;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPreparationTimeLeft() {
        return preparationTimeLeft;
    }

    public void setPreparationTimeLeft(int preparationTimeLeft) {
        this.preparationTimeLeft = preparationTimeLeft;
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