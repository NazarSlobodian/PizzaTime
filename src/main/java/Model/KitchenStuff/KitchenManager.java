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
    private List<Cooker> activeCooks; // Активні кухарі
    private List<Cooker> notActiveCooks; // Неактивні кухарі
    private Map<Cookable, Cooker> cookAssignments; // Мапа для відстеження, хто готує кожну страву
    private final Clock clock;

    public KitchenManager(Clock clock) {
        this.cookables = new ArrayList<Cookable>(); // Генеруємо тестові замовлення
        this.activeCooks = generateTestCooks(); // Генеруємо тестових кухарів
        this.notActiveCooks = new ArrayList<>();
        this.cookAssignments = new HashMap<>();
        this.clock = clock;
    }

    // Example method: Check if the kitchen can accept the order
    public boolean canAcceptOrder(Order order) {
        // Logic to determine if the kitchen can accept the order
        // Example: Check if there are enough available slots or resources
        return true; // Placeholder
    }

    // Example method: Accept the order for processing
    public void acceptOrder(Order order) {
        // Logic to process the order in the kitchen
        for (Cookable cookable : order.getItems()) {
            addCookable(cookable);
        }
        System.out.println("Kitchen processing order: " + order);
    }

    public void setCookPresent(Cooker cooker, boolean cookPresent) {
        // Змінюємо параметри кухаря в списку activeCooks, якщо він там є
        activeCooks.stream()
                .filter(cook -> cook.equals(cooker))
                .findFirst()
                .ifPresent(cook -> cook.setCookPresent(cookPresent));

        // Змінюємо параметри кухаря в списку notActiveCooks, якщо він там є
        notActiveCooks.stream()
                .filter(cook -> cook.equals(cooker))
                .findFirst()
                .ifPresent(cook -> cook.setCookPresent(cookPresent));
    }

    public List<Cookable> getAllCookable() {
        // Витягуємо всі страви зі списку замовлень
        return orders.stream()
                .flatMap(order -> order.getItems().stream()) // Отримуємо Stream всіх Cookable зі списку замовлень
                .toList(); // Перетворюємо в список
    }


    /**
     * Головний метод оновлення для обробки замовлень і приготування страв.
     */
    public void update(long elapsedMs) {
        checkActiveCook();
        for (int i = 0; i < cookables.size(); i++) {
            System.out.println("STARTED");
            processOrderItem(cookables.get(i), elapsedMs);
            System.out.println("FINISHED");
        }
        //System.out.println("Cook " + assignedCook + " completed an stage of: " + cookable.getStateName() + " Readiness: " + cookable.getReadiness() + "%");

        // Перевірка, чи продукт повністю готовий
        for (int i = 0; i < cookables.size(); i++) {
            if (cookables.get(i).isCooked()) {
                //System.out.println("Product " + cookable.getName() + " is fully cooked and ready at time: " + clock.getLocalDateTime().toLocalTime());
                cookAssignments.remove(cookables.get(i)); // Видаляємо з мапи призначень, страва готова
                cookables.remove(cookables.get(i)); // Видаляємо готовий елемент з замовлення
                i--;
            }
        }
    }

  private void processOrderItem(Cookable cookable, long elapsedMs) {
    Cooker assignedCook = cookAssignments.get(cookable);

    // Якщо кухар не призначений або зайнятий іншою стравою, шукаємо доступного кухаря
    if (assignedCook == null) {
        assignedCook = assignCook(cookable, elapsedMs);
        if (assignedCook == null) {
            //System.out.println("No available cook for " + cookable.getStateName());
            return;
        }
    }
        // Виконуємо один етап приготування з призначеним кухарем
        boolean isCookingStepDone = assignedCook.cook(cookable, elapsedMs);

        if(isCookingStepDone && cookable.getStateName().equals("Dough preparation")
                && cookable.getReadiness() < 0.5)
        {
            Logger.logStartCooking(cookable.getName());
        }

        if (isCookingStepDone) {
            //System.out.println("Cook " + assignedCook + " completed an stage of: " + cookable.getStateName() + " Readiness: " + cookable.getReadiness() + "%");

            // Перевірка, чи продукт повністю готовий
            if (cookable.isCooked()) {
                //System.out.println("Product " + cookable.getName() + " is fully cooked and ready at time: " + clock.getLocalDateTime().toLocalTime());
                Logger.logFinishCooking(cookable.getName());
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
       
   
private void addCookable(Cookable cookable) {
    cookables.add(cookable);
    eventContext.forceFirePropertyChange("cookableAdded", null, cookable);
}
/**
 * Призначає доступного кухаря для приготування страви, якщо він не зайнятий.
 */
private Cooker assignCook(Cookable cookable, long elapsedMs) {
    for (Cooker cook : activeCooks) {
        // Перевіряємо, чи кухар уже зайнятий іншою стравою
        if (cookAssignments.containsValue(cook)) {
            continue; // Пропускаємо кухаря, якщо він уже зайнятий
        }

        // Якщо кухар не зайнятий, призначаємо його для приготування страви
        boolean isCookingStepDone = cook.cook(cookable, elapsedMs);
            if (isCookingStepDone) {
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
    for (Cooker cook : new ArrayList<>(activeCooks)) {
        if (!cook.isActive() || !cook.isCookPresent()) {
            notActiveCooks.add(cook);
            activeCooks.remove(cook);
        }
    }

    for (Cooker cook : new ArrayList<>(notActiveCooks)) {
        if (cook.isActive() && cook.isCookPresent()) {
            activeCooks.add(cook);
            notActiveCooks.remove(cook);
        }
    }
}

private static List<Cooker> generateTestCooks() {
    List<Cooker> cooks = new ArrayList<>();
    cooks.add(new Cook());
    return cooks;
}
}
