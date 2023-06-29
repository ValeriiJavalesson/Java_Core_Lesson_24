import java.util.*;

public class Cinema {
    private TreeMap<Days, Schedule> schedules = new TreeMap<>();
    private List<Movie> moviesLibrary;
    private Time openTime;
    private Time closeTime;

    Cinema(Time openTime, Time closeTime) {
        this.openTime = openTime;
        this.closeTime = closeTime;
        moviesLibrary = new ArrayList<>();
        Days[] days = Days.values();
        for (Days d : days) {
            schedules.put(d, new Schedule());
        }
        System.out.println("Кінотеатр працює з " + openTime + " до "+ closeTime);
    }

    public Time getOpenTime() {
        return openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    void addMovie(Movie movie) {
        moviesLibrary.add(movie);
    }

    void removeMovie(Movie movie) {
        moviesLibrary.removeIf(m -> m.equals(movie));
        schedules.values().forEach(schedule -> schedule.getSeances().removeIf(seance -> seance.getMovie().equals(movie)));
    }

    public List<Movie> getMoviesLibrary() {
        return moviesLibrary;
    }

    public TreeMap<Days, Schedule> getSchedules() {
        return schedules;
    }

    void addSeance(Seance seance, String day) throws WrongTimeException {
        try {
            if (Time.toInt(seance.getStartTime()) < Time.toInt(openTime) || Time.toInt(seance.getEndTime()) > Time.toInt(closeTime))
                throw new WrongTimeException("Час сеансу не співпадає з часом роботи кінотеатру!");
        } catch (WrongTimeException e) {
            System.err.println(e.getMessage());
        }
        Days d = Days.convertStringtoDay(day);
        Schedule sch = schedules.get(d);
        sch.addSeance(seance);
    }

    void removeSeance(Seance seance, String day) {
        if (Days.isDayPresent(day)) {
            Days d = Days.convertStringtoDay(day);
            Schedule schedule = schedules.get(d);
            schedule.removeSeance(seance);
        }
    }

    public void showSeancesFromDay(String day) {
        if (Days.isDayPresent(day)) {
            Days d = Days.convertStringtoDay(day);
            Schedule schedule = schedules.get(d);
            Set<Seance> seances = schedule.getSeances();
            seances.forEach(System.out::println);
        }
    }

    @Override
    public String toString() {
        return "Кінотеатр";
    }


}
