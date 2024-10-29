package Model.FoodAndStuff.States;

public abstract class PizzaState implements CookableState {

    double readiness = 0.0;

    @Override
    public double getReadiness() {
        return readiness;
    }

    @Override
    public void increaseReadiness(double by) {
        readiness += by;
    }

    @Override
    public abstract PizzaState getNextSuccessful();

    @Override
    public abstract PizzaState getNextFailed();

    @Override
    public boolean isFinal() {
        return false;
    }
    @Override
    public boolean isBad() {
        return false;
    }
}
