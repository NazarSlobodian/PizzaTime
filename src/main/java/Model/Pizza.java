package Model;

public class Pizza implements Cloneable {
    private String name;
    private int preparationTimeLeft;
    private PizzaState state;

    public Pizza() {
        this.state = PizzaState.INITIAL;
    }

    public Pizza(String name, int preparationTimeLeft) {
        this.name = name;
        this.preparationTimeLeft = preparationTimeLeft;
        this.state = PizzaState.INITIAL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPreparationTimeLeft() {
        return preparationTimeLeft;
    }

    public void setPreparationTimeLeft(int preparationTimeLeft) {
        this.preparationTimeLeft = preparationTimeLeft;
    }

    public PizzaState getState() {
        return state;
    }

    public void setState(PizzaState state) {
        this.state = state;
    }

    @Override
    public Pizza clone() {
        try {
            return (Pizza) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Failed to clone Pizza", e);
        }
    }
}