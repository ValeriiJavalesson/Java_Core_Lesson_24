import java.util.Comparator;
import java.util.Objects;

public class Seance implements Comparable<Seance> {
    Movie movie;
    private Time startTime;
    private Time endTime;

    Seance(Movie movie, Time startTime) throws WrongTimeException {
        this.movie = movie;
        this.startTime = startTime;
        this.endTime = Time.toTime(Time.toInt(startTime) + Time.toInt(movie.getDuration()));
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seance seance)) return false;
        return Objects.equals(movie, seance.movie) && Objects.equals(startTime, seance.startTime) && Objects.equals(endTime, seance.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, startTime, endTime);
    }

    @Override
    public int compareTo(Seance s) {
            int thisInt = Time.toInt(this.getStartTime());
            int sInt = Time.toInt(s.getStartTime());
            if (thisInt - sInt == 0)return this.getMovie().getTitle().compareTo(s.getMovie().getTitle());
            return thisInt - sInt;
    }

    @Override
    public String toString() {
        return String.format("%-20s (початок: %5s, завершення: %5s)", movie.getTitle(), startTime, endTime);
    }
}
