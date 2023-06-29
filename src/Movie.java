import java.util.Objects;

public class Movie {


    private String title;
    private Time duration;

    public Movie(String title, Time duration) {
        this.title = title;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie movie)) return false;
        return Objects.equals(title, movie.title) && Objects.equals(duration, movie.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, duration);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Назва фільму: " + title + "(" + duration.getHour() + ":" + duration.getMin() + ")";
    }
}
