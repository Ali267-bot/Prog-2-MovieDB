package at.ac.fhcampuswien.fhmdb;

import static org.junit.jupiter.api.Assertions.*;

import at.ac.fhcampuswien.fhmdb.models.Genres;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MovieTest {
    @Test
    void initializeMovie_is_not_null() {
        //Given
        List<Movie> actual;

        //When
        actual = Movie.initializeMovies();

        //Then
        assertNotNull(actual);

    }

    @Test
    void initializeMovie_all_Movies_stay_the_same_after_initialize() {
        //Given
        List<Movie> actual;
        List<Movie> expected = new ArrayList<>();

        expected.add(new Movie(
                "Interstellar",
                " Set in a dystopian future where humanity is embroiled in a catastrophic blight " +
                        "and famine, the film follows a group of astronauts who travel through a wormhole " +
                        "near Saturn in search of a new home for humankind.",
                Arrays.asList(Genres.SCIENCE_FICTION, Genres.ACTION, Genres.ADVENTURE)
        ));
        expected.add(new Movie(
                "SpongeBob SquarePants",
                "After King Neptune's crown is stolen, SpongeBob and Patrick go on a quest in 6 " +
                        "days to retrieve his crown. On the way SpongeBob and Patrick defeat many evildoers " +
                        "using their brains and bronzes.",
                Arrays.asList(Genres.COMEDY, Genres.ACTION, Genres.ADVENTURE)
        ));
        expected.add(new Movie(
                "The Usual Suspects",
                "A sole survivor tells of the twisty events leading up to a horrific gun battle on" +
                        " a boat, which begin when five criminals meet at a seemingly random police lineup.",
                Arrays.asList(Genres.CRIME, Genres.DRAMA, Genres.MYSTERY)));
        expected.add(new Movie(
                "The Wolf of Wall Street",
                "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker" +
                        " living the high life to his fall involving crime, corruption and the federal government.",
                Arrays.asList(Genres.DRAMA, Genres.ROMANCE, Genres.BIOGRAPHY)));
        expected.add(new Movie(
                "Avatar",
                "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn " +
                        "between following his orders and protecting the world he feels is his home. äää",
                Arrays.asList(Genres.ANIMATION, Genres.DRAMA, Genres.ACTION)));
        expected.add(new Movie(
                "One Piece film red",
                "The Straw Hat Pirates leave for the island of Elegia to attend a concert by Uta," +
                        " a world-famous singer. After Uta performs her first song (\"New Genesis\"), Luffy" +
                        " goes on stage to reunite with her, revealing that the two of them know each" +
                        " other because Uta is the adopted daughter of \"Red-Haired\" Shanks.",
                Arrays.asList(Genres.ADVENTURE, Genres.COMEDY, Genres.ACTION)
        ));

        //When
        actual = Movie.initializeMovies();

        //Then
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    void normalizeString_The_Wolf_of_Wall_Street_to_the_wolf_of_wall_street() {
        //Given
        String actual = "The Wolf of Wall Street";

        //When
        actual = Movie.normalizeString(actual);

        //Then
        String expected = "the wolf of wall street";
        assertEquals(expected, actual);
    }
}