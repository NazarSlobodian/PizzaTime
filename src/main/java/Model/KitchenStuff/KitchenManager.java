package Model.KitchenStuff;

import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.DishReadiness;
import Model.FoodAndStuff.Pizza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitchenManager {

    private List<Order> orders; // Список замовлень
    private List<Cook> activeCooks; // Активні кухарі
    private List<Cook> notActiveCooks; // Неактивні кухарі
    private Map<Cookable, Cook> cookAssignments; // Мапа для відстеження, хто готує кожну страву

    public KitchenManager() {
        this.orders = generateTestOrders(); // Генеруємо тестові замовлення
        this.activeCooks = generateTestCooks(); // Генеруємо тестових кухарів
        this.notActiveCooks = new ArrayList<>();
        this.cookAssignments = new HashMap<>();
    }

    public KitchenManager(List<Order> orders, List<Cook> cooks) {
        this.orders = orders;
        this.activeCooks = cooks;
        this.notActiveCooks = new ArrayList<>();
        this.cookAssignments = new HashMap<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Головний метод оновлення для обробки замовлень і приготування страв.
     */
    public void update(long elapsedMs) {
        checkActiveCook();

        // Копіюємо список замовлень, щоб уникнути ConcurrentModificationException
        for (Order order : new ArrayList<>(orders)) {
            for (Cookable cookable : new ArrayList<>(order.getItems())) {
                processOrderItem(cookable, elapsedMs, order);
            }

            // Видаляємо замовлення, якщо всі його елементи готові
            if (order.getItems().isEmpty()) {
                orders.remove(order);
            }
        }
    }

    /**
     * Метод для обробки окремого елемента замовлення.
     */
    private void processOrderItem(Cookable cookable, long elapsedMs, Order order) {
        Cook assignedCook = cookAssignments.get(cookable);

        // Якщо кухар не призначений, шукаємо першого доступного
        if (assignedCook == null) {
            assignedCook = assignCook(cookable, elapsedMs);
            if (assignedCook == null) {
                System.out.println("No available cook for " + cookable.getStateName());
                return;
            }
        }

        // Виконуємо один етап приготування з призначеним кухарем
        DishReadiness readiness = assignedCook.cook(cookable, elapsedMs);

        if (readiness.isReady()) {
            System.out.println("Cook " + assignedCook + " completed an stage of: " + cookable.getStateName() + " Readines: " +cookable.getReadiness() + "%");

            // Перевірка, чи продукт повністю готовий
            if (cookable.isCooked()) {
                System.out.println("Product " + cookable.getName() + " is fully cooked and ready at time: " + System.currentTimeMillis());
                cookAssignments.remove(cookable); // Видаляємо з мапи призначень, страва готова
                order.getItems().remove(cookable); // Видаляємо готовий елемент з замовлення
            }
        } else {
            // Якщо кухар більше не може виконати етап, видаляємо його призначення для цього етапу
            cookAssignments.remove(cookable);
            assignedCook = assignCook(cookable, elapsedMs);
            if (assignedCook == null) {
                System.out.println("No available cook to continue " + cookable.getStateName());
            }
        }
    }

    /**
     * Призначає доступного кухаря для приготування страви.
     */
    private Cook assignCook(Cookable cookable, long elapsedMs) {
        for (Cook cook : activeCooks) {
            DishReadiness readiness = cook.cook(cookable, elapsedMs);
            if (readiness.isReady()) {
                cookAssignments.put(cookable, cook);
                return cook;
            }
        }
        return null; // Якщо немає доступного кухаря
    }

    /**
     * Перевіряє активність кухарів і оновлює списки активних та неактивних.
     */
    private void checkActiveCook() {
        for (Cook cook : new ArrayList<>(activeCooks)) {
            if (!cook.isActive() || !cook.isCookPresent()) {
                notActiveCooks.add(cook);
                activeCooks.remove(cook);
            }
        }

        for (Cook cook : new ArrayList<>(notActiveCooks)) {
            if (cook.isActive() && cook.isCookPresent()) {
                activeCooks.add(cook);
                notActiveCooks.remove(cook);
            }
        }
    }

    // Методи для генерації тестових замовлень і кухарів
    private static List<Order> generateTestOrders() {
        List<Order> orders = new ArrayList<>();
        List<Cookable> itemsOrder1 = new ArrayList<>();
        itemsOrder1.add(new Pizza("Margherita", 20 * 60 * 1000));
        itemsOrder1.add(new Pizza("Pepperoni", 25 * 60 * 1000));

        List<Cookable> itemsOrder2 = new ArrayList<>();
        itemsOrder2.add(new Pizza("Vegetarian", 15 * 60 * 1000));
        itemsOrder2.add(new Pizza("Hawaiian", 14 * 60 * 1000));

        orders.add(new Order(itemsOrder1, System.currentTimeMillis()));
        orders.add(new Order(itemsOrder2, System.currentTimeMillis()));

        return orders;
    }

    private static List<Cook> generateTestCooks() {
        List<Cook> cooks = new ArrayList<>();
        cooks.add(new Cook());
        cooks.add(new Cook());
        cooks.add(new Cook());
        return cooks;
    }
}
