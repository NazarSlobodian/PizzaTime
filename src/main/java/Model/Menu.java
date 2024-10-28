package Model;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Pizza> pizzas;

    public Menu() {
        pizzas = new ArrayList<>();
    }

    public List<Pizza> getPizzas() {
        return new ArrayList<>(pizzas);
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }
}
