package pizzatimepack;

import Model.Pizzeria;
import ViewModels.MainViewModel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulatorLauncher {

    Pizzeria pizzeria;
    ScheduledExecutorService scheduler;
    public SimulatorLauncher(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }
    public void run() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::update, 0, 1, TimeUnit.SECONDS);
    }
    public void stop() {
        scheduler.shutdownNow();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private void update() {
        pizzeria.update(1000);
    }
}
