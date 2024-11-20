package Model.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Logger {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final List<String> logEntries = new ArrayList<>();

    // Метод для логування початку приготування
    public static void logStartCooking(String pizzaName) {
        String timestamp = LocalDateTime.now().format(formatter);
        String log = "[" + timestamp + "] Cooking started for: " + pizzaName;
        logEntries.add(log);
        System.out.println(log);
    }


    public static void logFinishCooking(String pizzaName) {
        String timestamp = LocalDateTime.now().format(formatter);
        String log = "[" + timestamp + "] Cooking finished for: " + pizzaName;
        logEntries.add(log);
        System.out.println(log);
    }

    // Метод для отримання списку логів
    public static List<String> getLogEntries() {
        return Collections.unmodifiableList(logEntries); // Повертаємо незмінний список
    }


    public static void clearLogs() {
        logEntries.clear();
    }

}
