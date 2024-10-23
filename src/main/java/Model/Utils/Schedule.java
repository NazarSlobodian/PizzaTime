package Model.Utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class Schedule {
    // may separate this into separate schedule for each weekday
    // for ~flexibility~
    private Set<DayOfWeek> workDays;
    private LocalTime openTime;
    private LocalTime closeTime;
    public Schedule(Set<DayOfWeek> workDays, LocalTime openTime, LocalTime closeTime) {
        this.workDays = workDays;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
    public boolean isOpen(LocalDateTime time) {
        return workDays.contains(time.getDayOfWeek()) &&
                !time.toLocalTime().isBefore(openTime) &&
                !time.toLocalTime().isAfter(closeTime);
    }
    public LocalTime getOpenTime() {
        return openTime;
    }
    public LocalTime getCloseTime() {
        return closeTime;
    }
}
