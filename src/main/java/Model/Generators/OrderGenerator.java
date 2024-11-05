package Model.Generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.Menu;
import Model.KitchenStuff.Order;

/**
 * Generates orders based on different strategies.
 */
public class OrderGenerator {

    private Menu menu;
    private Random random;

    public OrderGenerator(Menu menu) {
        this.menu = menu;
        this.random = new Random();
    }

    // Generates an order with a random number of pizzas (1-5)
    public Order generateRandomOrder() {
        int itemCount = random.nextInt(5) + 1;
        List<Cookable> items = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            items.add(menu.getRandomPizza());
        }
        return new Order(items, System.currentTimeMillis());
    }

    // Generates an order with a fixed number of pizzas
    public Order generateFixedOrder(int itemCount) {
        List<Cookable> items = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            items.add(menu.getRandomPizza());
        }
        return new Order(items, System.currentTimeMillis());
    }
    
    // Generates promotional order with specific types of pizzas
    public Order generatePromoOrder(List<String> promoPizzaTypes) {
        List<Cookable> items = new ArrayList<>();
        for (String type : promoPizzaTypes) {
            items.add(menu.getPizzaByType(type));
        }
        return new Order(items, System.currentTimeMillis());
    }
}