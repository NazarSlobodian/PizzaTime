package Model.FoodAndStuff;

import Model.FoodAndStuff.States.InitialPizzaState;
import Model.FoodAndStuff.States.PizzaState;
import Model.FoodAndStuff.States.BakedPizzaState;

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

    protected void setState(PizzaState state) {
        this.state = state;
    }

    @Override
    public Pizza clone() {
        try {
            Pizza cloned = (Pizza) super.clone();
            // Глибоке клонування стану
            cloned.state = this.state.getClass()
                    .getDeclaredConstructor()
                    .newInstance();
            return cloned;
        } catch (Exception e) {
            throw new RuntimeException("Failed to clone Pizza", e);
        }
    }

    @Override
    public void cook(boolean controlledCooking, long elapsedTime) {
        // Розрахунок збільшення готовності на основі часу
        double increaseFactor = (elapsedTime / (double) preparationTimeLeft) * 100;
        state.increaseReadiness(increaseFactor);

        if (controlledCooking) {
            // Контрольоване приготування
            if (state.getReadiness() >= 100 && !state.isFinal()) {
                setState(state.getNextSuccessful());
            }
        } else {
            // Неконтрольоване приготування
            if (state.getReadiness() >= 100) {
                if (state instanceof BakedPizzaState) {
                    setState(state.getNextFailed()); // Перехід до стану Burned
                } else if (!state.isFinal()) {
                    setState(state.getNextSuccessful());
                }
            }
        }

        // Перевірка на невдалий стан
        if (state.isFinal() && state.isBad()) {
            setState(state.getNextFailed());
        }

        // Оновлення часу приготування
        preparationTimeLeft -= elapsedTime;
        if (preparationTimeLeft < 0) {
            preparationTimeLeft = 0;
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