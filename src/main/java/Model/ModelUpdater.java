package Model;

import java.util.concurrent.TimeUnit;

public interface ModelUpdater {
    void update(long elapsedMs);
}
