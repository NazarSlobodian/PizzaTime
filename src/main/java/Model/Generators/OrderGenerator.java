package Model.Generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.Menu;
import Model.KitchenStuff.Order;

/**
 * An interface for generating orders based on different strategies
 */
public interface OrderGenerator {
    Order generateRandomOrder();
    Order generateFixedOrder(int itemCount);
    Order generatePromoOrder(List<String> promoPizzaTypes);
}

/**
 * Generates orders based on different strategies
 */
class OrderGeneratorImpl implements OrderGenerator {

    private Menu menu;
    private Random random;

    public OrderGeneratorImpl(Menu menu) {
        this.menu = menu;
        this.random = new Random();
    }

    // Generates an order with a random number of pizzas (1-5)
    @Override
    public Order generateRandomOrder() {
        int itemCount = random.nextInt(5) + 1;
        List<Cookable> items = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            items.add(menu.getRandomPizza());
        }
        return new Order(items, System.currentTimeMillis());
    }

    // Generates an order with a fixed number of pizzas
    @Override
    public Order generateFixedOrder(int itemCount) {
        List<Cookable> items = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            items.add(menu.getRandomPizza());
        }
        return new Order(items, System.currentTimeMillis());
    }
    
    // Generates promotional order with specific types of pizzas
    @Override
    public Order generatePromoOrder(List<String> promoPizzaTypes) {
        List<Cookable> items = new ArrayList<>();
        for (String type : promoPizzaTypes) {
            items.add(menu.getPizzaByType(type));
        }
        return new Order(items, System.currentTimeMillis());
    }
}