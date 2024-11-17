package Model.Generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.Menu;
import Model.KitchenStuff.Order;

/**
 * Generates orders based on different strategies
 */
class OrderGeneratorImpl implements OrderGenerator {

    private final Menu menu;
    private final Random random;

    private long lastOrderTime;
    private final int intervalMillis;

    public OrderGeneratorImpl(Menu menu) {
        this.menu = menu;
        this.random = new Random();
        this.lastOrderTime = System.currentTimeMillis();
        this.intervalMillis = 10000;
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

    // Generates an order after a fixed interval of time
    @Override
    public Order generateOrderAfterInterval() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastOrderTime >= intervalMillis) {
            lastOrderTime = currentTime;

            int itemCount = random.nextInt(3) + 1;
            List<Cookable> items = new ArrayList<>();
            for (int i = 0; i < itemCount; i++) {
                items.add(menu.getRandomPizza());
            }

            return new Order(items, System.currentTimeMillis());
        }

        throw new IllegalStateException("Interval has not passed yet. Wait for the next generation.");
    }
}
