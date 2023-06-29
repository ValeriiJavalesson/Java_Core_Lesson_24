import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Schedule {
    private Set<Seance> seances = new TreeSet<>();

    public void addSeance(Seance seance) {
        seances.add(seance);
    }

    public void removeSeance(Seance seance) {
        for (Seance s : seances) {
            if (s.equals(seance)) seances.remove(s);
        }
    }

    public Set<Seance> getSeances() {
        return seances;
    }
}
