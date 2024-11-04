package Model.FoodAndStuff;

import Model.Utils.EventFiringContext;
import Model.Utils.ObservableModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Menu extends ObservableModel {
    private List<Pizza> pizzas;

    public Menu() {

        pizzas = new ArrayList<>();
        pizzas.add(new Pizza("Quattro Formaggi", 20*60*1000));
        pizzas.add(new Pizza("Diavolo", 25*60*1000));
        pizzas.add(new Pizza("Hawaii", 15*60*1000));
        pizzas.add(new Pizza("Margherita", 14*60*1000));
        pizzas.add(new Pizza("Salami", 18*60*1000));
    }


    public List<Pizza> getPizzas() {
        return new ArrayList<>(pizzas);
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }
}
