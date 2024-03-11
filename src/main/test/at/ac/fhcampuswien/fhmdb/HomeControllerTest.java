package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.control.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Genres;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;

class HomeControllerTest {

    private static HomeController homeController;

    private ObservableList<Movie> observableMovies;
    @BeforeEach
    public void setUp() {
        homeController = new HomeController();
        observableMovies = FXCollections.observableArrayList(
        new Movie(
                "Interstellar",
                " Set in a dystopian future where humanity is embroiled in a catastrophic blight " +
                        "and famine, the film follows a group of astronauts who travel through a wormhole " +
                        "near Saturn in search of a new home for humankind.",
                        Arrays.asList(Genres.SCIENCE_FICTION, Genres.ACTION, Genres.ADVENTURE)),
        new Movie(
                "SpongeBob SquarePants",
                "After King Neptune's crown is stolen, SpongeBob and Patrick go on a quest in 6 " +
                        "days to retrieve his crown. On the way SpongeBob and Patrick defeat many evildoers " +
                        "using their brains and bronzes.",
                Arrays.asList(Genres.COMEDY, Genres.ACTION, Genres.ADVENTURE)),
        new Movie(
                "The Usual Suspects",
                "A sole survivor tells of the twisty events leading up to a horrific gun battle on" +
                        " a boat, which begin when five criminals meet at a seemingly random police lineup.",
                Arrays.asList(Genres.CRIME, Genres.DRAMA, Genres.MYSTERY)),
        new Movie(
                "The Wolf of Wall Street",
                "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker" +
                        " living the high life to his fall involving crime, corruption and the federal government.",
                Arrays.asList(Genres.DRAMA, Genres.ROMANCE, Genres.BIOGRAPHY)),
        new Movie(
                "Avatar",
                "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn " +
                        "between following his orders and protecting the world he feels is his home. äää",
                Arrays.asList(Genres.ANIMATION, Genres.DRAMA, Genres.ACTION)),
        new Movie(
                "One Piece film red",
                "The Straw Hat Pirates leave for the island of Elegia to attend a concert by Uta," +
                        " a world-famous singer. After Uta performs her first song (\"New Genesis\"), Luffy" +
                        " goes on stage to reunite with her, revealing that the two of them know each" +
                        " other because Uta is the adopted daughter of \"Red-Haired\" Shanks.",
                Arrays.asList(Genres.ADVENTURE, Genres.COMEDY, Genres.ACTION))
        );

    }

    @Test
    public void test_Sorting_Ascending(){
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


}