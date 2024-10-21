package Model;

public class SimulatorUpdater implements ModelUpdater {

    @Override
    public void update(long elapsedMs) {
        System.out.println(elapsedMs);
        return;
    }
}
