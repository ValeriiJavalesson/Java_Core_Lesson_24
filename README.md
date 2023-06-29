# Java_Core_Lesson_24

* [<b>Завдання:</b>](https://github.com/ValeriiJavalesson/Java_Core_Lesson_24/tree/Lesson_24/src)

Проект кінотеатр:

Створити класи: Time, Movie, Seance, Schedule, Cinema. 

В enum Days:

- прописати дні тижня; 

В Time:

- поля int min, int hour;

- передбачити межі для їх значень (для min 0..60, для hour 0..24); 

В Movie:

-поля String title, Time duration; 

В Seance:

- поля Movie movie, Time startTime, Time endTime;

- в конструктор має надходити параметрами значення для перших двох полів, третє поле повинне обчислюватись (startTime + movie.duration);

В Schedule:

- поле Set<Seance> seances = new TreeSet<>();

- методи: addSeance (Seance), removeSeance (Seance); 


В Cinema:

- поля:

TreeMap<Days, Schedule> schedules,

ArrayList<Movie> moviesLibrary = new ArrayList<>(); 

Time open, Time close; - врахувати час відкриття і закриття при формуванні сеансів!

- методи:

addMovie (Movie, Time...time)  - додає фільм в фільмотеку moviesLibrary,

addSeance (Seance, String) - додає сеанс фільму в розклад (schedules), в конкретний день, який задається параметром String 

removeMovie(Movie) - повністю видаляє фільм з фільмотеки і розкладу, якщо був запланований

removeSeance (Seance, String)  - видаляє конкретний сеанс фільму в конкретний день, який задається параметром String 


В класі Main: - створити об'єкт Cinema; - реалізувати меню, в якому виконується весь функціонал Cinema. 

Для кожного класу зробити адекватний toString, щоб все було читабельно і доступно. Супроводжуючі повідомлення і тому подібне. Там де потрібно, зробити compareTo(). Маєте якісь власні ідеї для розробки - будь-ласка. Це моделювання роботи кінотеатру, тому все що наблизить програму до реальності вітається.
