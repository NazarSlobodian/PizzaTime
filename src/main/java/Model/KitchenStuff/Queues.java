package Model.KitchenStuff;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;

import Model.Generators.OrderStrategyManager;
import Model.Utils.Clock;
import Model.Utils.ObservableModel;
import Model.Utils.Schedule;

/**
 * Manages the queues for order processing and integrates with OrderStrategyManager, FlowGenerator, and KitchenManager.
 */
public class Queues extends ObservableModel implements Lobby {
    private final List<Queue<Order>> orderQueues;
    private final Queue<Order> rejectedQueue;
    private final List<Order> allOrders;
    private final KitchenManager kitchenManager;
    private final Schedule schedule;

    private final OrderStrategyManager orderStrategyManager;
    private final Clock clock;

    private int currentQueueIndex;
    private Lock lock;

    public Queues(Schedule schedule, OrderStrategyManager orderStrategyManager, KitchenManager kitchenManager, Clock clock, Lock lock) {
        this.lock = lock;
        this.orderQueues = new ArrayList<>();
        this.rejectedQueue = new LinkedList<>();
        this.allOrders = new ArrayList<>();
        this.schedule = schedule;
        this.orderStrategyManager = orderStrategyManager;
        this.kitchenManager = kitchenManager;
        this.clock = clock;
        this.currentQueueIndex = 0;

        for (int i = 0; i < 4; i++) {
            orderQueues.add(new LinkedList<>());
        }
    }

    /**
     * Main logic to generate orders and manage the flow between queues and the kitchen.
     */
    @Override
    public void manageOrderFlow() {
        if (orderStrategyManager.shouldGenerate()) {
            // Generate a new order using the active strategy
            Order newOrder = orderStrategyManager.generateOrder();
            addOrderToQueue(newOrder);
        }

        // Try to process orders from all queues into the kitchen
        processOrdersToKitchen();
    }

    /**
     * Adds an order to one of the queues in a round-robin manner.
     *
     * @param order The order to be added.
     */
    private void addOrderToQueue(Order order) {
        allOrders.add(order); // Save to the history of all orders

        if (canOrderBeCompleted(order)) {
            Queue<Order> queue = orderQueues.get(currentQueueIndex);
            order.setQueue(currentQueueIndex);
            queue.add(order);
            eventContext.forceFirePropertyChange("orderAdded", null, order);
            //System.out.println("Order added to queue [" + (currentQueueIndex + 1) + "]: " + order);

            currentQueueIndex = (currentQueueIndex + 1) % orderQueues.size();
        } else {
            rejectOrder(order);
        }
    }

    /**
     * Processes orders from all queues and sends them to the kitchen if possible.
     */
    private void processOrdersToKitchen() {
        for (int i = 0; i < orderQueues.size(); i++) {
            Queue<Order> queue = orderQueues.get(i);
            while (!queue.isEmpty()) {
                Order order = queue.peek();
                if (kitchenManager.canAcceptOrder(order)) {
                    queue.poll(); // Remove the order from the queue
                    kitchenManager.acceptOrder(order);
                    //System.out.println("Order sent to kitchen from queue [" + (i + 1) + "]: " + orderCopy);
                } else {
                    System.out.println("Kitchen cannot accept order yet from queue [" + (i + 1) + "]: " + order);
                    break; // Stop processing this queue if the kitchen can't accept the current order
                }
            }
        }
    }

    /**
     * Checks if the order can be completed before the end of the day.
     *
     * @param order The order to check.
     * @return True if the order can be completed, false otherwise.
     */
    private boolean canOrderBeCompleted(Order order) {
        long estimatedCompletionTime = order.getOrderTime() + 20 * 60 * 1000; // 20 minutes
        return estimatedCompletionTime <= schedule.getCloseTimeMs(clock.getLocalDate());
    }

    /**
     * Rejects the order if it can't be completed on time.
     *
     * @param order The order to be rejected.
     */
    private void rejectOrder(Order order) {
        rejectedQueue.add(order);
        System.out.println("Order rejected: " + order);
    }

    /**
     * Retrieves the queue of rejected orders.
     *
     * @return The rejected orders queue.
     */
    @Override
    public Queue<Order> getRejectedOrders() {
        return rejectedQueue;
    }

    /**
     * Retrieves the list of all orders processed throughout the day.
     *
     * @return The list of all orders.
     */
    @Override
    public List<Order> getAllOrders() {
        return allOrders;
    }

    @Override
    public void deleteQueue() {
        lock.lock();
        try {
            if (orderQueues.size() < 2) {
                return;
            }
            Queue<Order> ordersToRedistribute = orderQueues.get(orderQueues.size() - 1);
            Queue<Order> reversed = new LinkedList<>();
            while (ordersToRedistribute.size() > 0) {
                reversed.add(ordersToRedistribute.remove());
            }
            orderQueues.remove(orderQueues.size() - 1);
            int amount = (int) Math.ceil(reversed.size() / (double) orderQueues.size());
            for (int i = 0; i < orderQueues.size(); i++) {
                for (int j = 0; j < amount; j++) {
                    Order order = reversed.poll();
                    order.setQueue(i);
                    orderQueues.get(i).add(order);
                }
            }
            currentQueueIndex = 0; // Magic fix
            eventContext.firePropertyChange("queuesCountChanged", null, getQueuesCount());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void addQueue() {
        lock.lock();
        orderQueues.add(new LinkedList<>());
        eventContext.firePropertyChange("queuesCountChanged", null, getQueuesCount());
        lock.unlock();
    }

    @Override
    public int getQueuesCount() {
        return orderQueues.size();
    }
}