package Model.FoodAndStuff;

import Model.FoodAndStuff.States.CookableState;
import Model.Utils.ObservableModel;


public abstract class Dish extends ObservableModel implements Cookable {

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
    @Override
    public long getTotalPrepTimeMs() {
        return totalPrepTimeMs;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void setTotalPrepTimeMs(long totalPrepTimeMs) {
        this.totalPrepTimeMs = totalPrepTimeMs;
    }

    public CookingDifficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(CookingDifficulty difficulty) {
        this.difficulty = difficulty;
    }

}
