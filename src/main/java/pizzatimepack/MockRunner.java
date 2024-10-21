package pizzatimepack;

import Model.ModelUpdater;
import Model.SimulatorUpdater;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MockRunner {
    public static void main(String[] args) {
        ModelUpdater modelUpdater = new SimulatorUpdater();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            update(modelUpdater);
        }, 0, 1, TimeUnit.SECONDS);
    }
    public static void update(ModelUpdater modelUpdater) {
        modelUpdater.update(1000);
    }
}
