package Model.FoodAndStuff;

import Model.Utils.EventFiringContext;
import Model.Utils.ObservableModel;

import java.beans.PropertyChangeSupport;

public class Dish extends ObservableModel {

    protected String name;
    protected long totalPrepTimeMs;
    private CookingDifficulty difficulty; // not needed


    public Dish(String name, long totalPrepTimeMs) {
        this.name = name;
        this.totalPrepTimeMs = totalPrepTimeMs;
        this.difficulty = CookingDifficulty.EASY;
    }
    //
    public Dish(String name, long totalPrepTimeMs, CookingDifficulty difficulty) {
        this.name = name;
        this.totalPrepTimeMs = totalPrepTimeMs;
        this.difficulty = difficulty;
    }
    //
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPreparationTimeLeftMs() {
        return totalPrepTimeMs;
    }

    public void setPreparationTimeLeftMs(int totalPrepTimeMs) {
        this.totalPrepTimeMs = totalPrepTimeMs;
    }

    public CookingDifficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(CookingDifficulty difficulty) {
        this.difficulty = difficulty;
    }

}
