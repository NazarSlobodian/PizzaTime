package pizzatimepack;

import Model.SimulatorUpdater;
import ViewModels.MainViewModel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MockRunner {

    public static void main(String[] args) {

        // call update each realtime second
        SimulatorUpdater modelUpdater = new SimulatorUpdater();
        MainViewModel viewModel = new MainViewModel(modelUpdater);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> update(modelUpdater), 0, 1, TimeUnit.SECONDS);
    }

    public static void update(SimulatorUpdater modelUpdater) {
        modelUpdater.update(1000); // assume it is indeed a second
    }
}
