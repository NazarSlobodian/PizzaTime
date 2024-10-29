package Model.Utils;

import java.time.LocalTime;

/**
 * Time interval (i.e. 9:00-18:00)
 * @param start
 * @param end
 */
public record TimeInterval(LocalTime start, LocalTime end) {
    public TimeInterval {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }
    }
}