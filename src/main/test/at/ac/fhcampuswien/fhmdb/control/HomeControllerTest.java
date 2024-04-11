package at.ac.fhcampuswien.fhmdb.control;

import at.ac.fhcampuswien.fhmdb.control.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Genres;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class HomeControllerTest {

    private static HomeController homeController;

    private ObservableList<Movie> observableMovies;

    @BeforeEach
    void setUp() {
        homeController = new HomeController();
        observableMovies = FXCollections.observableArrayList(
                new Movie(
                        "ID",
                        "Interstellar",
                        "Set in a dystopian future where humanity is embroiled in a catastrophic blight " +
                                "and famine, the film follows a group of astronauts who travel through a wormhole " +
                                "near Saturn in search of a new home for humankind.",
                        Arrays.asList(Genres.SCIENCE_FICTION, Genres.ACTION, Genres.ADVENTURE),
                        "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                        2014,
                        8.6,
                        Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"), // mainCast
                        new HashSet<>(Arrays.asList("Christopher Nolan"))),

                new Movie(
                        "ID",
                        "SpongeBob SquarePants",
                        "After King Neptune's crown is stolen, SpongeBob and Patrick go on a quest in 6 " +
                                "days to retrieve his crown. On the way SpongeBob and Patrick defeat many evildoers " +
                                "using their brains and bronzes.",
                        Arrays.asList(Genres.COMEDY, Genres.ACTION, Genres.ADVENTURE),
                        "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                        2014,
                        8.6,
                        Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"), // mainCast
                        new HashSet<>(Arrays.asList("Christopher Nolan"))),

                new Movie(
                        "ID",
                        "The Usual Suspects",
                        "A sole survivor tells of the twisty events leading up to a horrific gun battle on" +
                                " a boat, which begin when five criminals meet at a seemingly random police lineup.",
                        Arrays.asList(Genres.CRIME, Genres.DRAMA, Genres.MYSTERY),
                        "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                        2014,
                        8.6,
                        Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"), // mainCast
                        new HashSet<>(Arrays.asList("Christopher Nolan"))),

                new Movie(
                        "ID",
                        "The Wolf of Wall Street",
                        "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker" +
                                " living the high life to his fall involving crime, corruption and the federal government.",
                        Arrays.asList(Genres.DRAMA, Genres.ROMANCE, Genres.BIOGRAPHY),
                        "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                        2014,
                        8.6,
                        Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"), // mainCast
                        new HashSet<>(Arrays.asList("Christopher Nolan"))),

                new Movie(
                        "ID",
                        "Avatar",
                        "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn " +
                                "between following his orders and protecting the world he feels is his home. äää",
                        Arrays.asList(Genres.ANIMATION, Genres.DRAMA, Genres.ACTION),
                        "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                        2014,
                        8.6,
                        Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"), // mainCast
                        new HashSet<>(Arrays.asList("Christopher Nolan"))),

                new Movie(
                        "ID",
                        "One Piece film red",
                        "The Straw Hat Pirates leave for the island of Elegia to attend a concert by Uta," +
                                " a world-famous singer. After Uta performs her first song (\"New Genesis\"), Luffy" +
                                " goes on stage to reunite with her, revealing that the two of them know each" +
                                " other because Uta is the adopted daughter of \"Red-Haired\" Shanks.",
                        Arrays.asList(Genres.ADVENTURE, Genres.COMEDY, Genres.ACTION),
                        "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                        2014,
                        8.6,
                        Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"), // mainCast
                        new HashSet<>(Arrays.asList("Christopher Nolan"))
                ));

    }


    // ---------------- SORTING ---------------- \\

    @Test
    void test_Sorting_Ascending() {
        ObservableList<Movie> observableMovies = homeController.getObservableMovies();

        // Sorting the movies in ascending order by title
        Comparator<Movie> ascendingComparator = Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER);
        homeController.sortMovies(ascendingComparator);

        // Check if movies are sorted in ascending order
        for (int i = 0; i < observableMovies.size() - 1; i++) {
            Movie currentMovie = observableMovies.get(i);
            Movie nextMovie = observableMovies.get(i + 1);
            assertTrue(ascendingComparator.compare(currentMovie, nextMovie) <= 0, "Movies are not sorted in ascending order");
        }
    }

    @Test
    void test_Sorting_Ascending_EmptyList() {
        // Clear the observable movies list
        observableMovies.clear();

        // Sort the empty list in ascending order
        Comparator<Movie> ascendingComparator = Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER);
        homeController.sortMovies(ascendingComparator);

        // Ensure that the empty list remains empty after sorting
        assertTrue(observableMovies.isEmpty(), "Empty list should remain empty after sorting");
    }

    @Test
    void test_Sorting_Descending() {
        ObservableList<Movie> observableMovies = homeController.getObservableMovies();

        // Sorting the movies in descending order by title
        Comparator<Movie> descendingComparator = Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER).reversed();
        homeController.sortMovies(descendingComparator);

        // Check if movies are sorted in descending order
        for (int i = 0; i < observableMovies.size() - 1; i++) {
            Movie currentMovie = observableMovies.get(i);
            Movie nextMovie = observableMovies.get(i + 1);
            assertTrue(descendingComparator.compare(currentMovie, nextMovie) >= 0, "Movies are not sorted in descending order");
        }
    }

    @Test
    void test_Sorting_Descending_EmptyList() {
        // Clear the observable movies list
        observableMovies.clear();

        // Sort the empty list in descending order
        Comparator<Movie> descendingComparator = Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER).reversed();
        homeController.sortMovies(descendingComparator);

        // Ensure that the empty list remains empty after sorting
        assertTrue(observableMovies.isEmpty(), "Empty list should remain empty after sorting in descending order");
    }


    // ---------------- FILTERING ---------------- \\

    // ----- SEARCH ----- \\
    @Test
    void testFilterBySearchText_ExactMatch() {
        Predicate<Movie> predicate = homeController.filterBySearchText("Interstellar");
        assertTrue(predicate.test(new Movie("1", "Interstellar", "Description", Arrays.asList(),
                "",
                1,
                0.0,
                new ArrayList<>(), new TreeSet<>())));
    }

    @Test
    void testFilterBySearchText_CaseInsensitiveMatch() {
        Predicate<Movie> predicate = homeController.filterBySearchText("interstellar");
        assertTrue(predicate.test(new Movie("1", "Interstellar", "Description", Arrays.asList(),
                "",
                1,
                0.0,
                new ArrayList<>(), new TreeSet<>())));
    }

    @Test
    void testFilterBySearchText_PartialMatch() {
        Predicate<Movie> predicate = homeController.filterBySearchText("Inter");
        assertTrue(predicate.test(new Movie("1", "Interstellar", "Description", Arrays.asList(),
                "",
                1,
                0.0,
                new ArrayList<>(), new TreeSet<>())));
    }

    @Test
    void testFilterBySearchText_NoMatch() {
        Predicate<Movie> predicate = homeController.filterBySearchText("Random");
        assertFalse(predicate.test(new Movie("1", "Interstellar", "Description", Arrays.asList(),
                "",
                1,
                0.0,
                new ArrayList<>(), new TreeSet<>())));
    }

    @Test
    void testFilterBySearchText_SpecialCharacters() {
        Predicate<Movie> predicate = homeController.filterBySearchText("Intérstéllar");
        assertTrue(predicate.test(new Movie("1", "Interstellar", "Description", Arrays.asList(),
                "",
                1,
                0.0,
                new ArrayList<>(), new TreeSet<>())));
    }

    @Test
    void testFilterBySearchText_EmptyString() {
        Predicate<Movie> predicate = homeController.filterBySearchText("");
        assertTrue(predicate.test(new Movie("1", "AnyMovie", "Description", Arrays.asList(),
                "",
                1,
                0.0,
                new ArrayList<>(), new TreeSet<>())));
    }

    @Test
    void testFilterBySearchText_MultipleWordsMatch() {
        Predicate<Movie> predicate = homeController.filterBySearchText("Interstellar future");
        assertTrue(predicate.test(new Movie("1", "Interstellar", "Set in a dystopian future", Arrays.asList(),
                "",
                1,
                0.0,
                new ArrayList<>(), new TreeSet<>())), "Should match movies containing all search terms across title and description");
    }

    @Test
    void testFilterBySearchText_TrimSpaces() {
        Predicate<Movie> predicate = homeController.filterBySearchText("  Interstellar   ");
        assertTrue(predicate.test(new Movie("1", "Interstellar", "Description", Arrays.asList(),
                "",
                1,
                0.0,
                new ArrayList<>(), new TreeSet<>())), "Search should ignore leading and trailing spaces");
    }

    @Test
    void testFilterBySearchText_SpecialCharactersInTitle() {
        Predicate<Movie> predicate = homeController.filterBySearchText("Intérstéllar");
        assertTrue(predicate.test(new Movie("1", "Intérstéllar", "A futuristic space adventure", Arrays.asList(),
                "",
                1,
                0.0,
                new ArrayList<>(), new TreeSet<>())), "Should match titles with special characters");
    }

    @Test
    void testFilterBySearchText_NonAlphanumericCharacters() {
        Predicate<Movie> predicate = homeController.filterBySearchText("!@#$%");
        assertFalse(predicate.test(new Movie("1", "Interstellar", "Description", Arrays.asList(),
                "",
                1,
                0.0,
                new ArrayList<>(), new TreeSet<>())), "Non-alphanumeric search query should ideally not match standard movie titles");
    }

    @Test
    void testFilterBySearchText_IgnoreAccentsOption() {
        Predicate<Movie> predicate = homeController.filterBySearchText("E");
        assertTrue(predicate.test(new Movie("1", "Épic", "An epic adventure", Arrays.asList(),
                "",
                1,
                0.0,
                new ArrayList<>(), new TreeSet<>())), " 'E' should match 'É' in titles");
    }


    // ----- BY GENRE ----- \\
    @Test
    void filterByGenre_COMEDY_shouldFilter_SpongeBobSquarePants_and_OnePiecefilmred() {
        //Given
        homeController = new HomeController();
        Predicate<Movie> movieTest = homeController.filterByGenre("COMEDY");
        List<Movie> actual = new ArrayList<>();
        List<Movie> expected = Arrays.asList(
                new Movie("2", "SpongeBob SquarePants", "", Arrays.asList(Genres.ACTION),
                        "",
                        1,
                        0.0,
                        new ArrayList<>(), new TreeSet<>()),
                new Movie("6", "One Piece film red", "", Arrays.asList(Genres.ACTION),
                        "",
                        1,
                        0.0,
                        new ArrayList<>(), new TreeSet<>()));

        //When
        for (Movie m : homeController.allMovies) {
            if (movieTest.test(m)) actual.add(m);
        }

        //Then
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i).getTitle(), actual.get(i).getTitle());
        }
    }

    @Test
    void filterByGenre_ACTION_shouldFilter_Interstellar_and_SpongeBobSquarePants_and_Avatar_and_OnePiecefilmred() {
        //Given
        homeController = new HomeController();
        Predicate<Movie> movieTest = homeController.filterByGenre("ACTION");
        List<Movie> actual = new ArrayList<>();
        List<Movie> expected = Arrays.asList(
                new Movie("1", "Interstellar", "", Arrays.asList(Genres.ACTION),
                        "",
                        1,
                        0.0,
                        new ArrayList<>(), new TreeSet<>()),
                new Movie("2", "SpongeBob SquarePants", "", Arrays.asList(Genres.ACTION),
                        "",
                        1,
                        0.0,
                        new ArrayList<>(), new TreeSet<>()),
                new Movie("5", "Avatar", "", Arrays.asList(Genres.ACTION),
                        "",
                        1,
                        0.0,
                        new ArrayList<>(), new TreeSet<>()),
                new Movie("6", "One Piece film red", "", Arrays.asList(Genres.ACTION),
                        "",
                        1,
                        0.0,
                        new ArrayList<>(), new TreeSet<>()));

        //When
        for (Movie m : homeController.allMovies) {
            if (movieTest.test(m)) actual.add(m);
        }

        //Then
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i).getTitle(), actual.get(i).getTitle());
        }
    }

    @Test
    void filterByGenre_BIOGRAPHY_shouldFilter_TheWolfofWallStreet() {
        //Given
        homeController = new HomeController();
        Predicate<Movie> movieTest = homeController.filterByGenre("BIOGRAPHY");
        List<Movie> actual = new ArrayList<>();
        List<Movie> expected = Arrays.asList(
                new Movie("4", "The Wolf of Wall Street", "", Arrays.asList(Genres.ACTION),
                        "",
                        1,
                        0.0,
                        new ArrayList<>(), new TreeSet<>()));

        //When
        for (Movie m : homeController.allMovies) {
            if (movieTest.test(m)) actual.add(m);
        }

        //Then
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i).getTitle(), actual.get(i).getTitle());
        }
    }

    @Test
    void filterByGenre_MYSTERY_should_filter_TheUsualSuspects() {
        //Given
        homeController = new HomeController();
        Predicate<Movie> movieTest = homeController.filterByGenre("MYSTERY");
        List<Movie> actual = new ArrayList<>();
        List<Movie> expected = Arrays.asList(new Movie("3", "The Usual Suspects", "", Arrays.asList(Genres.ACTION),
                "",
                1,
                0.0,
                new ArrayList<>(), new TreeSet<>()));


        //When
        for (Movie m : homeController.allMovies) {
            if (movieTest.test(m)) actual.add(m);
        }

        //Then
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i).getTitle(), actual.get(i).getTitle());
        }
    }

    @Test
    void testx() {
        homeController = new HomeController();


        List<Movie> movies = new ArrayList<>();

        movies.add(new Movie(
                        "ID",
                        "Interstellar",
                        "Set in a dystopian future where humanity is embroiled in a catastrophic blight " +
                                "and famine, the film follows a group of astronauts who travel through a wormhole " +
                                "near Saturn in search of a new home for humankind.",
                        Arrays.asList(Genres.SCIENCE_FICTION, Genres.ACTION, Genres.ADVENTURE),
                        "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                        2014,
                        8.6,
                        Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"), // mainCast
                        new HashSet<>(Arrays.asList("Christopher Nolan"))
                )
        );
        movies.add(new Movie(
                "ID",
                "SpongeBob SquarePants",
                "After King Neptune's crown is stolen, SpongeBob and Patrick go on a quest in 6 " +
                        "days to retrieve his crown. On the way SpongeBob and Patrick defeat many evildoers " +
                        "using their brains and bronzes.",
                Arrays.asList(Genres.COMEDY, Genres.ACTION, Genres.ADVENTURE),
                "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                2014,
                8.6,
                Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"), // mainCast
                new HashSet<>(Arrays.asList("Christopher Nolan"))
        ));
        movies.add(new Movie(
                "ID",
                "The Usual Suspects",
                "A sole survivor tells of the twisty events leading up to a horrific gun battle on" +
                        " a boat, which begin when five criminals meet at a seemingly random police lineup.",
                Arrays.asList(Genres.CRIME, Genres.DRAMA, Genres.MYSTERY),
                "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                2014,
                8.6,
                Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"), // mainCast
                new HashSet<>(Arrays.asList("Christopher Nolan"))));
        movies.add(new Movie(
                "ID",
                "The Wolf of Wall Street",
                "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker" +
                        " living the high life to his fall involving crime, corruption and the federal government.",
                Arrays.asList(Genres.DRAMA, Genres.ROMANCE, Genres.BIOGRAPHY),
                "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                2014,
                8.6,
                Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"), // mainCast
                new HashSet<>(Arrays.asList("Christopher Nolan"))));
        movies.add(new Movie(
                "ID",
                "Avatar",
                "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn " +
                        "between following his orders and protecting the world he feels is his home. äää",
                Arrays.asList(Genres.ANIMATION, Genres.DRAMA, Genres.ACTION),
                "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                2014,
                8.6,
                Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"), // mainCast
                new HashSet<>(Arrays.asList("Christopher Nolan"))));
        movies.add(new Movie(
                "ID",
                "One Piece film red",
                "The Straw Hat Pirates leave for the island of Elegia to attend a concert by Uta," +
                        " a world-famous singer. After Uta performs her first song (\"New Genesis\"), Luffy" +
                        " goes on stage to reunite with her, revealing that the two of them know each" +
                        " other because Uta is the adopted daughter of \"Red-Haired\" Shanks.",
                Arrays.asList(Genres.ADVENTURE, Genres.COMEDY, Genres.ACTION),
                "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
                2014,
                8.6,
                Arrays.asList("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"), // mainCast
                new HashSet<>(Arrays.asList("Christopher Nolan"))
        ));


        assertEquals("Jessica Chastain", homeController.getMostPopularActor(movies));
    }
}