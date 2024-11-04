package Model.FoodAndStuff;

import Model.Utils.ObservableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Menu extends ObservableModel {
    private List<Pizza> pizzas;

    private final Lock lock;
    public Menu(Lock lock) {
        this.lock = lock;

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
        lock.lock();
        pizzas.add(pizza);
        lock.unlock();

        eventContext.forceFirePropertyChange("menuPizzaAdded", -1, pizza);
    }

    public void removePizza(String pizzaName) {
        int ind = -1;
        lock.lock();
        for (int i = 0; i < pizzas.size(); i++) {
            if (pizzas.get(i).getName().equals(pizzaName)) {
                pizzas.remove(i);
                ind = i;
                break;
            }
        }
        lock.unlock();

        eventContext.forceFirePropertyChange("menuPizzaDeleted", -1, ind);
    }

    public void removePizza(int index) {
        lock.lock();
        pizzas.remove(index);
        lock.unlock();
        eventContext.forceFirePropertyChange("menuPizzaDeleted", -1, index);
    }
}
