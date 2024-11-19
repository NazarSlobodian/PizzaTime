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

    public void acceptOrder(Order order) {
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
        //?? Нащо розподіл якщо в cook є метод перевірки
        // Змінюємо параметри кухаря в списку notActiveCooks, якщо він там є
        notActiveCooks.stream()
                .filter(cook -> cook.equals(cooker))
                .findFirst()
                .ifPresent(cook -> cook.setCookPresent(cookPresent));
    }

    /**
     * Головний метод оновлення для обробки замовлень і приготування страв.
     */
    public void update(long elapsedMs) {
        checkActiveCook();
        for (int i = 0; i < cookables.size(); i++) {
            processOrderItem(cookables.get(i), elapsedMs);
        }
        //видалення приготованих страв
        for (int i = 0; i < cookables.size(); i++) {
            if (cookables.get(i).isCooked()) {
                Logger.logFinishCooking(cookables.get(i).getName());
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
            assignCook(cookable);
            return;
        }
        // Виконуємо один етап приготування з призначеним кухарем
        boolean isCookingStepDone = assignedCook.cook(cookable, elapsedMs);

        if (isCookingStepDone && cookable.getStateName().equals("Dough preparation")
                && cookable.getReadiness() < 0.5) {
            Logger.logStartCooking(cookable.getName());
        }

        if (!isCookingStepDone) {
            // Якщо кухар більше не може виконати етап, видаляємо його призначення для цього етапу
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
        for (Cooker cook : activeCooks) {
            // Перевіряємо, чи кухар уже зайнятий іншою стравою
            if (cookAssignments.containsValue(cook)) {
                continue; // Пропускаємо кухаря, якщо він уже зайнятий
            }
            // Якщо кухар не зайнятий, призначаємо його для приготування страви
            boolean canCook = cook.canCook(cookable.getStateName());
            if (canCook) {
                cookAssignments.put(cookable, cook);
                return;
            }
        }
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
