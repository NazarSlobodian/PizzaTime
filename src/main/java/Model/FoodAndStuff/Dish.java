package Model.FoodAndStuff;

import java.beans.PropertyChangeSupport;

public class Dish {

    protected String name;
    protected long preparationTimeLeftMs;
    private CookingDifficulty difficulty; // not needed

    private final PropertyChangeSupport support;

    public Dish(String name, long preparationTimeLeft, PropertyChangeSupport propertyChangeSupport) {
        this.name = name;
        this.preparationTimeLeftMs = preparationTimeLeft;
        this.difficulty = CookingDifficulty.EASY;
        this.support = propertyChangeSupport;
    }
    //
    public Dish(String name, long preparationTimeLeft, CookingDifficulty difficulty, PropertyChangeSupport propertyChangeSupport) {
        this.name = name;
        this.preparationTimeLeftMs = preparationTimeLeft;
        this.difficulty = difficulty;
        this.support = propertyChangeSupport;
    }
    //
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPreparationTimeLeftMs() {
        return preparationTimeLeftMs;
    }

    public void setPreparationTimeLeftMs(int preparationTimeLeftMs) {
        this.preparationTimeLeftMs = preparationTimeLeftMs;
    }

    public CookingDifficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(CookingDifficulty difficulty) {
        this.difficulty = difficulty;
    }

}
