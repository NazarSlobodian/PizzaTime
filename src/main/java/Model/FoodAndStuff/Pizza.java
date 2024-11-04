package Model.FoodAndStuff;

import Model.FoodAndStuff.States.DoughPizzaState;
import Model.FoodAndStuff.States.PizzaState;
import Model.Utils.EventFiringContext;

public class Pizza extends Dish implements Cloneable, Cookable {

    private PizzaState state;

    public Pizza(String name, long preparationTimeLeftMs ) {
        super(name,preparationTimeLeftMs);
        this.state = new DoughPizzaState();
    }
    public Pizza(String name, long preparationTimeLeftMs, CookingDifficulty difficulty) {
        super(name,preparationTimeLeftMs, difficulty);
        this.state = new DoughPizzaState();
    }

    public PizzaState getState() {
        return state;
    }

    protected void setState(PizzaState state) {
        PizzaState oldState = this.state;
        this.state = state;
        //if (eventContext.canFireEvent()) { force update
            eventContext.forceFirePropertyChange("pizzaStateChanged", oldState.toString(), this.state.toString());
        //}
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
    public void cook(boolean cookPresent, long elapsedTime) {
        if (!state.canBeAutocooked() && !cookPresent) {
            return;
        }
        // Розрахунок збільшення готовності на основі часу
        double increaseFactor = ((elapsedTime*3) / (double) totalPrepTimeMs) * 100;
        state.increaseReadiness(increaseFactor);

        if (cookPresent) {
            if (state.getReadiness() >= 100 && !state.isFinal()) {
                setState(state.getNextSuccessful());
            }
        } else {
            if (state.getReadiness() >= 100) {
                if (state.isBad() && !state.isFinal()) {
                    setState(state.getNextSuccessful());
                } else if (state.isBad() && state.isFinal()) {
                    setState(state.getNextFailed());
                }
            }
        }

        if (eventContext.canFireEvent()) {
            eventContext.firePropertyChange("pizzaStateReadinessChanged", 0, this.state.getReadiness());
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