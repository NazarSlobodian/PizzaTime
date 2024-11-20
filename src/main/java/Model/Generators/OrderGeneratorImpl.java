package Model.Generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.Menu;
import Model.KitchenStuff.Order;
import Model.Utils.Clock;

/**
 * Generates orders based on different strategies
 */
public class OrderGeneratorImpl implements OrderGenerator {

    private final Menu menu;
    private final Random random;

    private long lastOrderTime;
    private final long intervalMillis;
    Clock clock;

    public OrderGeneratorImpl(Menu menu, Clock clock) {
        this.menu = menu;
        this.random = new Random();
        this.lastOrderTime = clock.getCurrentTime();
        this.intervalMillis = 10000;
        this.clock = clock;
    }

    // Generates an order with a random number of pizzas (1-5)
    @Override
    public Order generateRandomOrder() {
        int itemCount = random.nextInt(1) + 2;
        List<Cookable> items = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            items.add(menu.getRandomCookable());
        }
        return new Order(items, clock.getCurrentTime());
    }

    // Generates an order after a fixed interval of time
    @Override
    public Order generateOrderAfterInterval() {
        long currentTime = clock.getCurrentTime();
        if (currentTime - lastOrderTime >= intervalMillis) {
            lastOrderTime = currentTime;

            int itemCount = random.nextInt(3) + 1;
            List<Cookable> items = new ArrayList<>();
            for (int i = 0; i < itemCount; i++) {
                items.add(menu.getRandomCookable());
            }

            return new Order(items, clock.getCurrentTime());
        }
        return null;
    }
}
