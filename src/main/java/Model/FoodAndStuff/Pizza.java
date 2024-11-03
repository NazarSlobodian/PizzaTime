package Model.FoodAndStuff;

import Model.FoodAndStuff.States.InitialPizzaState;
import Model.FoodAndStuff.States.PizzaState;

import java.beans.PropertyChangeSupport;

public class Pizza extends Dish implements Cloneable, Cookable {

    private PizzaState state;

    public Pizza(String name, long preparationTimeLeftMs, PropertyChangeSupport support) {
        super(name,preparationTimeLeftMs, support);
        this.state = new InitialPizzaState();
    }
    public Pizza(String name, long preparationTimeLeftMs, CookingDifficulty difficulty, PropertyChangeSupport support) {
        super(name,preparationTimeLeftMs, difficulty, support);
        this.state = new InitialPizzaState();
    }

    public PizzaState getState() {
        return state;
    }

    protected void setState(PizzaState state) {
        this.state = state;
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
    public void cook(boolean controlledCooking, long elapsedTime) {
        // Розрахунок збільшення готовності на основі часу
        double increaseFactor = (elapsedTime / (double) preparationTimeLeftMs) * 100; // divide by amount of states?
        state.increaseReadiness(increaseFactor);

        if (controlledCooking) {
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

        // Оновлення часу приготування
        preparationTimeLeftMs -= elapsedTime;
        if (preparationTimeLeftMs < 0) {
            preparationTimeLeftMs = 0;
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