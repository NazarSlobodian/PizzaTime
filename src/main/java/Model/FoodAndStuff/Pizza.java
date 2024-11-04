package Model.FoodAndStuff;

import Model.FoodAndStuff.States.DoughPizzaState;
import Model.FoodAndStuff.States.PizzaState;

public class Pizza extends Dish implements Cloneable {

    private PizzaState state;

    public Pizza(String name, long totalPrepTimeMs) {
        super(name, totalPrepTimeMs);
        this.state = new DoughPizzaState();
    }

    public Pizza(String name, long preparationTimeLeftMs, CookingDifficulty difficulty) {
        super(name, preparationTimeLeftMs, difficulty);
        this.state = new DoughPizzaState();
    }

    public PizzaState getState() {
        return state;
    }

    @Override
    public void increaseReadiness(double value) {
        state.increaseReadiness(value);
        if (eventContext.canFireEvent()) {
            eventContext.firePropertyChange("pizzaStateReadinessChanged", 0, this.state.getReadiness());
        }
        if (state.getReadiness() >= 100.0) {
            updateState();
        }
    }
    private void updateState() {
        if (state.isFinal()) {
            return;
        }
        if (state.isBad()) {
            setNextFailureState();
        }
        else {
            setNextSuccessfulState();
        }
    }

    @Override
    public boolean cookableWithoutCook() {
        return state.canBeAutocooked();
    }

    @Override
    public Pizza clone() {
        try {
            Pizza cloned = (Pizza) super.clone();
            cloned.state = this.state.clone();
            return cloned;
        } catch (Exception e) {
            throw new RuntimeException("Failed to clone Pizza", e);
        }
    }

    @Override
    public boolean isCooked() {
        return state.isFinal() && !state.isBad();
    }

    @Override
    public double getReadiness() {
        return state.getReadiness();
    }

    private void setNextSuccessfulState() {
        setState(state.getNextSuccessful());
    }
    private void setNextFailureState() {
        setState(state.getNextFailed());
    }
    private void setState(PizzaState state) {
        PizzaState oldState = this.state;
        this.state = state;
        //if (eventContext.canFireEvent()) { force update
        eventContext.forceFirePropertyChange("pizzaStateChanged", oldState.toString(), this.state.toString());
        //}
    }
}