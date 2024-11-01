package Model.FoodAndStuff;

public class Dish {

    protected String name;
    protected long preparationTimeLeft;
    private CookingDifficulty difficulty;
    public Dish() {

    }
    public Dish(String name, long preparationTimeLeft) {
        this.name = name;
        this.preparationTimeLeft = preparationTimeLeft;
        this.difficulty = CookingDifficulty.EASY;
    }
    public Dish(String name, long preparationTimeLeft, CookingDifficulty difficulty) {
        this.name = name;
        this.preparationTimeLeft = preparationTimeLeft;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPreparationTimeLeft() {
        return preparationTimeLeft;
    }

    public void setPreparationTimeLeft(int preparationTimeLeft) {
        this.preparationTimeLeft = preparationTimeLeft;
    }

    public CookingDifficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(CookingDifficulty difficulty) {
        this.difficulty = difficulty;
    }

}
