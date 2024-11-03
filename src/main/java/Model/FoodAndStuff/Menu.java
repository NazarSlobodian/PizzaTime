package Model.FoodAndStuff;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Pizza> pizzas;

    private final PropertyChangeSupport propertyChangeSupport;
    public Menu(PropertyChangeSupport propertyChangeSupport) {
        this.propertyChangeSupport = propertyChangeSupport;

        pizzas = new ArrayList<>();
        pizzas.add(new Pizza("Quattro Formaggi", 20*60*1000, propertyChangeSupport));
        pizzas.add(new Pizza("Diavolo", 25*60*1000, propertyChangeSupport));
        pizzas.add(new Pizza("Hawaii", 15*60*1000, propertyChangeSupport));
        pizzas.add(new Pizza("Margherita", 14*60*1000, propertyChangeSupport));
        pizzas.add(new Pizza("Salami", 18*60*1000, propertyChangeSupport));
    }


    public List<Pizza> getPizzas() {
        return new ArrayList<>(pizzas);
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }
}
