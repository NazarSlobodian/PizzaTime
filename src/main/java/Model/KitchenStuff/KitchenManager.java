package Model.KitchenStuff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.FoodAndStuff.Cookable;
import Model.FoodAndStuff.Pizza;
import Model.Utils.Clock;
import Model.Utils.Logger;
import Model.Utils.ObservableModel;

public class KitchenManager extends ObservableModel {

    private List<Cookable> cookables;
    private List<Cooker> cooks; // Активні кухарі
    private Map<Cookable, Cooker> cookAssignments; // Мапа для відстеження, хто готує кожну страву
    private final Clock clock;
    private final Logger logger;

    public KitchenManager(Clock clock) {
        this.cookables = new ArrayList<Cookable>(); // Генеруємо тестові замовлення
        this.cooks = generateTestCooks(); // Генеруємо тестових кухарів
        this.cookAssignments = new HashMap<>();
        this.clock = clock;
        this.logger = new Logger(clock);
    }

    // Example method: Check if the kitchen can accept the order
    public boolean canAcceptOrder(Order order) {

        return true; // Placeholder
    }

    public void acceptOrder(Order order) {
        for (Cookable cookable : order.getItems()) {
            addCookable(cookable);
        }
        System.out.println("Kitchen processing order: " + order);
    }

    public void setCookPresent(Cooker cooker, boolean cookPresent) {
        cooks.stream()
                .filter(cook -> cook.equals(cooker))
                .findFirst()
                .ifPresent(cook -> cook.setCookPresent(cookPresent));
    }

    /**
     * Головний метод оновлення для обробки замовлень і приготування страв.
     */
    public void update(long elapsedMs) {
        for (int i = 0; i < cookables.size(); i++) {
            processOrderItem(cookables.get(i), elapsedMs);
        }
        //видалення приготованих страв
        for (int i = 0; i < cookables.size(); i++) {
            if (cookables.get(i).isCooked()) {
                Cookable c = cookables.get(i);
                logger.logFinishCooking(c.getName());
                cookAssignments.remove(c); // Видаляємо з мапи призначень, страва готова
                cookables.remove(c); // Видаляємо готовий елемент з замовлення
                eventContext.forceFirePropertyChange("cookableDeleted", null, c);
                i--;
            }
        }
    }

    private void processOrderItem(Cookable cookable, long elapsedMs) {
        Cooker assignedCook = cookAssignments.get(cookable);

        // Якщо кухар не призначений або зайнятий іншою стравою, шукаємо доступного кухаря
        if (assignedCook == null) {
            assignCook(cookable);
            return;
        }
        // Виконуємо один етап приготування з призначеним кухарем
        if (cookable.isInitial()) {
            logger.logStartCooking(cookable.getName());
        }
        if (assignedCook.canCook(cookable)) {
            assignedCook.cook(cookable, elapsedMs);
        } else {
            cookAssignments.remove(cookable);
        }


    }

    private void addCookable(Cookable cookable) {
        cookables.add(cookable);
        eventContext.forceFirePropertyChange("cookableAdded", null, cookable);
    }

    /**
     * Призначає доступного кухаря для приготування страви, якщо він не зайнятий.
     */
    private void assignCook(Cookable cookable) {
        for (Cooker cook : cooks) {
            if (!cook.isCookPresent()) {
                continue;
            }
            // Перевіряємо, чи кухар уже зайнятий іншою стравою
            if (cookAssignments.containsValue(cook)) {
                continue; // Пропускаємо кухаря, якщо він уже зайнятий
            }
            // Якщо кухар не зайнятий, призначаємо його для приготування страви
            boolean canCook = cook.canCook(cookable);
            if (canCook) {
                cookAssignments.put(cookable, cook);
                return;
            }
        }
    }

    private static List<Cooker> generateTestCooks() {
        List<Cooker> cooks = new ArrayList<>();
        cooks.add(new Cook());
        cooks.add(new Cook());
        cooks.add(new Cook());
        cooks.add(new Cook());
        cooks.add(new Cook());
        cooks.add(new Cook());
        return cooks;
    }

    @Override
    public void setNotifications(boolean setting) {
        eventContext.setEventFiring(setting);
        for (Cookable cookable : cookables) {
            cookable.setNotifications(setting);
        }
    }

    public Logger getLogger() {
        return logger;
    }
}
