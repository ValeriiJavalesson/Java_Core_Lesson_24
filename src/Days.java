import java.util.Arrays;

public enum Days {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    public static Days convertStringtoDay(String day) {
        Days d = Days.valueOf(day.toUpperCase());
        return Days.valueOf(day.toUpperCase());
    }

    public static boolean isDayPresent(String day) {
        return Arrays.stream(Days.values()).anyMatch(d -> d.name().equalsIgnoreCase(day));
    }
}
