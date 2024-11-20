package Model.FoodAndStuff;

import Model.FoodAndStuff.States.CookableState;
import Model.Utils.ObservableModel;


public abstract class Dish extends ObservableModel implements Cookable {

    protected String name;
    protected long totalPrepTimeMs;


    public Dish(String name, long totalPrepTimeMs) {
        this.name = name;
        this.totalPrepTimeMs = totalPrepTimeMs;
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
    @Override
    public Cookable clone() {
        try {
            return (Dish) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported", e);
        }
    }

}
