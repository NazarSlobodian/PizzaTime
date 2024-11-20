package Model.FoodAndStuff;

import Model.FoodAndStuff.States.DoughPizzaState;
import Model.FoodAndStuff.States.PizzaState;
import Model.Utils.EventFiringContext;

import javax.naming.event.EventContext;

public class Pizza extends Dish  {

    private PizzaState state;

    public Pizza(String name, long totalPrepTimeMs) {
        super(name, totalPrepTimeMs);
        this.state = new DoughPizzaState();
    }

    public String getStateName() {
        return state.toString();
    }

    @Override
    public void increaseReadiness(double value, boolean controlledCooking) {

        if (!cookableWithoutCook() && !controlledCooking) {
            return;
        }
        state.increaseReadiness(value);
        if (eventContext.canFireEvent()) {
            eventContext.firePropertyChange("pizzaStateReadinessChanged", 0, this.state.getReadiness());
        }
        if (state.getReadiness() >= 100) {
            if (controlledCooking) {
                setNextSuccessfulState();
            } else {
                setNextFailureState();
            }
        }
        if (state.isBad()) {
            setNextFailureState();
        }
    }

    @Override
    public boolean cookableWithoutCook() {
        return state.canBeAutocooked();
    }

    @Override
    public Cookable clone() {
        try {
            Pizza cloned = (Pizza) super.clone();
            cloned.state = this.state.clone();
            cloned.eventContext = new EventFiringContext(cloned);
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
    public boolean isInitial() {
        return state.isInitial() && state.getReadiness() < 0.01;
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