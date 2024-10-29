package Model.Utils;

import java.time.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Schedule for each LocalDate
 */
public class Schedule {

    private final Map<DayOfWeek, TimeInterval> defaultSchedule;
    private final Map<LocalDate, TimeInterval> specialSchedule;
    private final Set<LocalDate> holidays;

    //------------------------------------------------
    public Schedule(Map<DayOfWeek, TimeInterval> defaultSchedule, Map<LocalDate, TimeInterval> specialSchedule, Set<LocalDate> holidays) {
        this.defaultSchedule = defaultSchedule;
        this.specialSchedule = specialSchedule;
        this.holidays = holidays;
    }
    public Schedule() {
        TimeInterval typicalWorkday = new TimeInterval(LocalTime.of(9, 0), LocalTime.of(17, 0));
        this.defaultSchedule = new HashMap<>();
        defaultSchedule.put(DayOfWeek.MONDAY,typicalWorkday);
        defaultSchedule.put(DayOfWeek.TUESDAY,typicalWorkday);
        defaultSchedule.put(DayOfWeek.WEDNESDAY,typicalWorkday);
        defaultSchedule.put(DayOfWeek.THURSDAY,typicalWorkday);
        defaultSchedule.put(DayOfWeek.FRIDAY,typicalWorkday);

        specialSchedule = new HashMap<>();
        holidays = new HashSet<>();
    }
    //------------------------------------------------
    public boolean isOpen(LocalDateTime time) {
        LocalDate date = time.toLocalDate();
        LocalTime localTime = time.toLocalTime();
        DayOfWeek day = date.getDayOfWeek();
        if (holidays.contains(date)) {
            return false;
        }
        else if (specialSchedule.containsKey(date)) {
            return localTime.isAfter(specialSchedule.get(date).start())
                    && localTime.isBefore(specialSchedule.get(date).end());
        }
        else if (defaultSchedule.containsKey(day)) {
            return localTime.isAfter(defaultSchedule.get(day).start())
                    && localTime.isBefore(defaultSchedule.get(day).end());
        }
        else {
            return false;
        }
    }
    // - - - - - - - - - - - - - -
    public LocalTime getOpenTime(LocalDate localDate) {
        if (holidays.contains(localDate)) {
            throw new IllegalArgumentException("Not working");
        }
        if (specialSchedule.containsKey(localDate)) {
            return specialSchedule.get(localDate).start();
        }
        else {
            return defaultSchedule.get(localDate.getDayOfWeek()).start();
        }
    }
    // - - - - - - - - - - - - - -
    public LocalTime getCloseTime(LocalDate localDate) {
        if (holidays.contains(localDate)) {
            throw new IllegalArgumentException("Not working");
        }
        if (specialSchedule.containsKey(localDate)) {
            return specialSchedule.get(localDate).end();
        }
        else {
            return defaultSchedule.get(localDate.getDayOfWeek()).end();
        }
    }
    // - - - - - - - - - - - - - -
}
