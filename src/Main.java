import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws WrongTimeException {
        Cinema cinema = new Cinema(new Time(9, 0), new Time(23, 0));

        cinema.addMovie(new Movie("One another day", new Time(1, 55)));
        cinema.addMovie(new Movie("Two another day", new Time(2, 3)));
        cinema.addMovie(new Movie("Three another day", new Time(1, 34)));
        cinema.addMovie(new Movie("Four another day", new Time(1, 41)));
        cinema.addMovie(new Movie("Five another day", new Time(2, 0)));
        cinema.addMovie(new Movie("Six another day", new Time(1, 48)));
        cinema.addMovie(new Movie("Seven another day", new Time(2, 31)));

        cinema.addSeance(new Seance(cinema.getMoviesLibrary().get(3), new Time(11, 30)), "Monday");
        cinema.addSeance(new Seance(cinema.getMoviesLibrary().get(2), new Time(21, 22)), "Monday");
        cinema.addSeance(new Seance(cinema.getMoviesLibrary().get(1), new Time(9, 0)), "Monday");
        cinema.addSeance(new Seance(cinema.getMoviesLibrary().get(1), new Time(9, 0)), "Tuesday");
        cinema.addSeance(new Seance(cinema.getMoviesLibrary().get(2), new Time(10, 50)), "Tuesday");
        cinema.addSeance(new Seance(cinema.getMoviesLibrary().get(3), new Time(9, 0)), "Wednesday");
        cinema.addSeance(new Seance(cinema.getMoviesLibrary().get(4), new Time(11, 30)), "Wednesday");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            menu();
            String input = scanner.next();
            switch (input) {
                case "0" -> {
                    scanner.close();
                    System.exit(0);
                }
                case "1" -> {
                    System.out.println("Список всих фільмів:");
                    System.out.printf("%-21s %s \n", "Назва фільму", "Тривалість");
                    System.out.printf("%-21s %s \n", "--------------------", "----------");
                    cinema.getMoviesLibrary().forEach(m -> System.out.printf("%-20s  %d:%d \n", m.getTitle(), m.getDuration().getHour(), m.getDuration().getMin()));
                }
                case "2" -> {
                    System.out.println("Введіть назву фільму, який потрібно додати:");
                    String title = scanner.next();
                    title += scanner.nextLine();
                    if (isMoviePresent(title, cinema.getMoviesLibrary())) {
                        System.out.println("Такий фільм вже існує!");
                        break;
                    }
                    System.out.println("Введіть довжину фільму у форматі: ГГ:ХХ");
                    String[] splitStringTime = scanner.next().split(":");
                    try {
                        Time time = new Time(Integer.parseInt(splitStringTime[0]), Integer.parseInt(splitStringTime[1]));
                        cinema.addMovie(new Movie(title, time));
                        System.out.println("Фільм " + title + " додано!");
                    } catch (WrongTimeException e) {
                        System.err.println(e.getMessage());
                    }
                }
                case "3" -> {
                    System.out.println("Введіть назву фільму, який потрібно видалити:");
                    String title = scanner.next();
                    title += scanner.nextLine();
                    List<Movie> list = cinema.getMoviesLibrary();
                    if (isMoviePresent(title, list)) {
                        Movie m = stringToMovie(title, list);
                        cinema.removeMovie(m);
                        assert m != null;
                        System.out.println("Фільм " + m.getTitle() + " видалено!");
                    } else System.out.println("Такого фільму немає в бібліотеці");
                }
                case "4" -> {
                    System.out.println("Введіть день тижня:");
                    String dayOfWeek = scanner.next();
                    if (!Days.isDayPresent(dayOfWeek)) {
                        System.out.println("Такого дня не існує!");
                        break;
                    }
                    System.out.println("Список запланованих сеансів на цей день:");
                    cinema.showSeancesFromDay(dayOfWeek);
                    Schedule schedule = cinema.getSchedules().get(Days.convertStringtoDay(dayOfWeek));
                    if (!schedule.getSeances().isEmpty()) {
                        Seance max = schedule.getSeances().stream()
                                .max(Comparator.comparingInt(s -> Time.toInt(s.getEndTime())))
                                .get();
                        int lastMovieEndTime = Time.toInt(max.getEndTime());
                        if (Time.toInt(cinema.getCloseTime()) - lastMovieEndTime <= 15) {
                            System.out.println("Цього дня сеансів краще не планувати, "
                                    + "оскільки до закриття кінотеатру лишилося " + (Time.toInt(cinema.getCloseTime()) - lastMovieEndTime) + "хв");
//                            break;
                        } else {
                            lastMovieEndTime += 15;
                            while (lastMovieEndTime % 5 != 0) lastMovieEndTime++;
                            System.out.print("Рекомендований час початку наступного сеансу: ");
                            System.out.println(Time.toTime(lastMovieEndTime));
                        }
                    }
                    System.out.println("Введіть назву фільму:");
                    String title = scanner.next();
                    title += scanner.nextLine();
                    List<Movie> list = cinema.getMoviesLibrary();
                    if (!isMoviePresent(title, list)) {
                        System.out.println("Такого фільму немає в бібліотеці!");
                        break;
                    }
                    System.out.println("Введіть час початку сеансу в форматі ГГ:ХХ");
                    String[] split = scanner.next().split(":");

                    try {
                        Time time = new Time(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                        Movie m = stringToMovie(title, list);
                        assert m != null;
                        if (Time.toInt(cinema.getCloseTime()) < Time.toInt(Time.timeSumm(time, m.getDuration()))) {
                            System.out.println("Сеанс не буде заплановано, оскільки час закриття кінотеатру раніше, ніж час"
                                    + " закінчення сеансу.");
                            break;
                        }
                        cinema.addSeance(new Seance(m, time), dayOfWeek);
                        System.out.println("Успішно!");
                    } catch (WrongTimeException e) {
                        System.err.println(e.getMessage());
                    }
                }
                case "5" -> {
                    System.out.println("Введіть день тижня:");
                    String dayOfWeek = scanner.next();
                    if (!Days.isDayPresent(dayOfWeek)) {
                        System.out.println("Такого дня не існує!");
                        break;
                    }
                    System.out.println("Список запланованих сеансів на цей день:");
                    cinema.showSeancesFromDay(dayOfWeek);
                }
                case "6" -> {
                    System.out.println("Введіть день тижня:");
                    String dayOfWeek = scanner.next();
                    if (!Days.isDayPresent(dayOfWeek)) {
                        System.out.println("Такого дня не існує!");
                        break;
                    }
                    System.out.println("Список запланованих сеансів на цей день:");
                    cinema.showSeancesFromDay(dayOfWeek);
                    System.out.println("Введіть назву фільму, який потрібно видалити в цей день:");
                    String title = scanner.next();
                    title += scanner.nextLine();
                    if (isMoviePresent(title, cinema.getMoviesLibrary())) {
                        String finalTitle = title;
                        cinema.getSchedules()
                                .get(Days.convertStringtoDay(dayOfWeek))
                                .getSeances()
                                .removeIf(seance -> seance.getMovie().getTitle().equalsIgnoreCase(finalTitle));
                        System.out.println("Сеанс видалено!");
                    } else System.out.println("Такого фільму не заплановано в цей день.");
                }
            }
        }
    }

    private static void menu() {
        System.out.println();
        System.out.println("Введіть 0, щоб вийти з програми");
        System.out.println("Введіть 1, щоб переглянути список всих фільмів у бібліотеці");
        System.out.println("Введіть 2, щоб додати фільм до бібліотеки");
        System.out.println("Введіть 3, щоб видалити фільм з бібліотеки");
        System.out.println("Введіть 4, щоб створити новий сеанс");
        System.out.println("Введіть 5, щоб вивести всі сеанси на конкретний день");
        System.out.println("Введіть 6, щоб видалити сеанс в конкретний день");
    }

    private static boolean isMoviePresent(String title, List<Movie> library) {
        return library.stream().anyMatch(m -> m.getTitle().equalsIgnoreCase(title));
    }

    private static Movie stringToMovie(String title, List<Movie> library) {
        for (Movie m : library) {
            if (m.getTitle().equalsIgnoreCase(title)) {
                return m;
            }
        }
        return null;
    }
}