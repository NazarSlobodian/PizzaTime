package Model.FoodAndStuff;

public class DishReadiness {
    private Cookable cookable;
    private boolean isReady;

    public Cookable getCookable() {
        return cookable;
    }

    public void setCookable(Cookable cookable) {
        this.cookable = cookable;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
