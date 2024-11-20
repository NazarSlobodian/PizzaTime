package Model.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Logger {
    private final Clock clock;
    private final List<String> logEntries = new ArrayList<>();

    public Logger(Clock clock) {
        this.clock = clock;
    }
    // Метод для логування початку приготування
    public void logStartCooking(String pizzaName) {
        String log = "[" + clock.toString() + "] Cooking started for: " + pizzaName;
        logEntries.add(log);
        System.out.println(log);
    }


    public void logFinishCooking(String pizzaName) {
        String log = "[" + clock.toString() + "] Cooking finished for: " + pizzaName;
        logEntries.add(log);
        System.out.println(log);
    }

    // Метод для отримання списку логів
    public List<String> getLogEntries() {
        return Collections.unmodifiableList(logEntries); // Повертаємо незмінний список
    }


    public void clearLogs() {
        logEntries.clear();
    }

}
