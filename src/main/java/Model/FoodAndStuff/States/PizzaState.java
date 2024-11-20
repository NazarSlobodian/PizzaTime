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
    public boolean isInitial() {
        return false;
    }
    @Override
    public boolean isBad() {
        return false;
    }
    @Override
    public boolean canBeAutocooked() {
        return false;
    }
    @Override
    public abstract String toString();

    @Override
    public PizzaState clone() {
        try {
            return (PizzaState) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }
}
