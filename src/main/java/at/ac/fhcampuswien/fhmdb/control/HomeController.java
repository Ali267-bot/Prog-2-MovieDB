package at.ac.fhcampuswien.fhmdb.control;

import at.ac.fhcampuswien.fhmdb.models.Genres;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import static at.ac.fhcampuswien.fhmdb.models.Movie.normalizeString;

/**
 * HomeController manages the UI logic for the Movie list and filter functionality.
 */
public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;
    @FXML
    public TextField searchField;

    @FXML
    public TextField releaseYearField;
    @FXML
    public TextField ratingField;

    @FXML
    public JFXListView<Movie> movieListView;
    @FXML
    public JFXComboBox<String> genreComboBox;
    @FXML
    public JFXButton sortBtn;
    @FXML
    public JFXButton clearBtn;
    @FXML
    public Label noMoviesLabel;


    private boolean isAscending = true;
    public List<Movie> allMovies = Movie.initializeMovies();
    private ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
    public FilteredList<Movie> filteredMovies;

    public ObservableList<Movie> getObservableMovies() {
        return observableMovies;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchMovies("", "", "", "");
        // observableMovies.addAll(allMovies); Nicht mehr nötig, weil API
        filteredMovies = new FilteredList<>(observableMovies, p -> true); // Initialize filtered list with a predicate that allows everything

        setupListView();
        setupGenreComboBox();
        setupActionHandlers();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateClearButtonVisibility());
        genreComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateClearButtonVisibility());
        releaseYearField.textProperty().addListener((observable, oldValue, newValue) -> updateClearButtonVisibility());
        ratingField.textProperty().addListener((observable, oldValue, newValue) -> updateClearButtonVisibility());

    }



    /**
     * Fetches movies from a remote source and updates the observable list with the fetched movies.
     * This method constructs a URI with optional query parameters for search query, genre, release year,
     * and minimum rating if they are provided (not null or empty), then makes an HTTP GET request to the specified URL.
     * The response is deserialized into a list of Movie objects, which is then used to update the observable list on the JavaFX Application Thread.
     * Also, logs the results and the URL used for the API call.
     *
     * @param query Optional. The search query input by the user. If null or empty, it is ignored.
     * @param genre Optional. The genre selected by the user. If null or empty, it is ignored.
     * @param releaseYear Optional. The release year specified by the user. If null or empty, it is ignored.
     * @param rating Optional. The minimum rating specified by the user. If null or empty, it is ignored.
     */
    private void fetchMovies(String query, String genre, String releaseYear, String rating) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            URIBuilder uriBuilder = new URIBuilder("https://prog2.fh-campuswien.ac.at/movies");

            Map<String, String> params = new HashMap<>();
            params.put("query", query);
            params.put("genre", genre);
            params.put("releaseYear", releaseYear);
            params.put("ratingFrom", rating);

            params.forEach((key, value) -> addParameterIfPresent(uriBuilder, key, value));

            URI uri = uriBuilder.build();
            HttpGet request = new HttpGet(uri);
            String jsonResponse = client.execute(request, httpResponse -> EntityUtils.toString(httpResponse.getEntity()));
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Movie> moviesData = mapper.readValue(jsonResponse, new TypeReference<>() {});

            Platform.runLater(() -> {
                observableMovies.setAll(moviesData);
                logResults(moviesData);
            });

            System.out.printf("API call performing.\nURL Used: %s\n", uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addParameterIfPresent(URIBuilder builder, String paramName, String paramValue) {
        if (paramValue != null && !paramValue.isEmpty()) {
            builder.addParameter(paramName, paramValue);
        }
    }

    private void logResults(List<Movie> movies) {
        String mostPopularActor = getMostPopularActor(movies);
        int longestMovieTitleLength = getLongestMovieTitle(movies);

        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_RESET = "\u001B[0m";

        System.out.println("Most Popular Actor: " + ANSI_CYAN + mostPopularActor + ANSI_RESET);
        System.out.println("Longest Movie Title Length: " + ANSI_CYAN + longestMovieTitleLength + ANSI_RESET);
    }





    /**
     * Configures the Movie ListView, including its cell factory.
     */
    private void setupListView() {
        movieListView.setItems(filteredMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());
    }

    /**
     * Populates the Genre ComboBox with unique genres extracted from all movies.
     */
    private void setupGenreComboBox() {
        genreComboBox.setPromptText("Filter by Genre");
        Set<Genres> uniqueGenres = allMovies.stream()
                .flatMap(movie -> movie.getGenres().stream())
                .collect(Collectors.toSet());
        genreComboBox.getItems().addAll(uniqueGenres.stream()
                .sorted()
                .map(Genres::name)
                .collect(Collectors.toList()));
    }

    /**
     * Sets up the action handlers for the interactive UI elements.
     *
     * OLD
     *
    private void setupActionHandlers() {
        searchBtn.setOnAction(actionEvent -> applyFilters());
        sortBtn.setOnAction(actionEvent -> toggleSortOrder());

        clearBtn.setOnAction(actionEvent -> {
            searchField.setText("");
            genreComboBox.getSelectionModel().clearSelection();
            applyFilters();
        });

    }
     */

    private void setupActionHandlers() {
        searchBtn.setOnAction(actionEvent -> {
            String userInput = searchField.getText().trim();
            String selectedGenre = genreComboBox.getSelectionModel().getSelectedItem();
            String releaseYearInput = releaseYearField.getText().trim();
            String ratingInput = ratingField.getText().trim();

            fetchMovies(userInput, selectedGenre, releaseYearInput, ratingInput);
        });

        sortBtn.setOnAction(actionEvent -> toggleSortOrder());
        clearBtn.setOnAction(actionEvent -> {
            searchField.setText("");
            genreComboBox.getSelectionModel().clearSelection();
            fetchMovies("", "","", "");
        });
    }


    /**
     * Filters the list of movies by genre.
     *
     * @param selectedGenre The genre to filter by.
     * @return A predicate that returns true for movies that match the selected genre.
     */
    protected Predicate<Movie> filterByGenre(String selectedGenre) {
        return movie -> selectedGenre == null || selectedGenre.isEmpty() || movie.getGenres().stream().anyMatch(g -> g.name().equals(selectedGenre));
    }

    /**
     * Creates a predicate for filtering movies based on a search text. The predicate checks if the given search text is
     * contained in either the movie's title or description, disregarding case sensitivity and diacritical marks.
     * Both the movie's title and description are pre-normalized to lowercase without diacritical marks for efficient searching.
     *
     * @param searchText The text to search for within the movie's title and description. The search ignores case sensitivity
     *                   and diacritical marks (e.g., accents).
     * @return A predicate that evaluates to {@code true} for movies where the normalized title or description contains
     *         the normalized search text. If the search text is empty, the predicate allows all movies to pass through.
     */
    protected Predicate<Movie> filterBySearchText(String searchText) {
        if (searchText.trim().isEmpty()) {
            return movie -> true;
        }

        String normalizedSearchText = normalizeString(searchText);
        List<String> searchWords = Arrays.asList(normalizedSearchText.split("\\s+"));

        return movie -> {
            String titleNormalized = movie.getTitleLowercaseNormalized();
            String descriptionNormalized = movie.getDescriptionLowercaseNormalized();

            return searchWords.stream()
                    .allMatch(word -> titleNormalized.contains(word) || descriptionNormalized.contains(word));
        };
    }



    /**
     * Toggles the sort order of the movie list between ascending and descending.
     */
    private void toggleSortOrder() {
        Comparator<Movie> comparator = Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER);
        comparator = isAscending ? comparator.reversed() : comparator;

        sortMovies(comparator);
        isAscending = !isAscending;

        sortBtn.setText(isAscending ? "Sort (asc)" : "Sort (desc)");
    }

    /**
     * Sorts the observable list of movies based on the provided comparator.
     *
     * @param comparator The comparator to use for sorting the movies.
     */
    public void sortMovies(Comparator<Movie> comparator) {
        FXCollections.sort(observableMovies, comparator);
    }

    /**
     * Applies the selected filters to the movie list.
     */
    public void applyFilters() {
        String selectedGenre = genreComboBox.getSelectionModel().getSelectedItem();
        String searchText = searchField.getText().trim();

        Predicate<Movie> combinedPredicate = filterByGenre(selectedGenre).and(filterBySearchText(searchText));

        filteredMovies.setPredicate(combinedPredicate);

        // Check if the filtered list is empty and update the UI accordingly
        updateUIBasedOnFilterResults();
    }



    /**
     * Updates the UI based on the results of movie filtering. Controls the visibility of the movie list view
     * and the "No movies found." label.
     * setUIVisibility
     */
    private void updateUIBasedOnFilterResults() {
        boolean listIsEmpty = filteredMovies.isEmpty();

        movieListView.setVisible(!listIsEmpty);
        noMoviesLabel.setVisible(listIsEmpty);
    }




    /**
     * Updates the visibility of the "Clear" button based on the content of the search field and the selection state of the genre combo box.
     *
     * The "Clear" button becomes visible if either the search field contains text or a genre is selected in the combo box. When both the search field is empty
     * and no genre is selected, the "Clear" button is hidden, indicating that there's nothing to reset.
     */
    private void updateClearButtonVisibility() {
        boolean isSearchFilled = !searchField.getText().trim().isEmpty();
        boolean isGenreSelected = genreComboBox.getSelectionModel().getSelectedItem() != null;
        boolean isReleaseYearFilled = !releaseYearField.getText().trim().isEmpty();
        boolean isRatingFilled = !ratingField.getText().trim().isEmpty();

        clearBtn.setVisible(isSearchFilled || isGenreSelected || isReleaseYearFilled || isRatingFilled);

    }



    public String getMostPopularActor_nodebug(List<Movie> movies) {
        return movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public String getMostPopularActor(List<Movie> movies) {
        System.out.println("--------- getMostPopularActor ---------");
        Map<String, Long> actorOccurrences = movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        actorOccurrences.forEach((actor, count) -> System.out.println(actor + " : " + count));
        System.out.println();

        return actorOccurrences.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }


    public int getLongestMovieTitle_nodebug(List<Movie> movies) {
        return movies.stream()
                .map(Movie::getTitle)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        System.out.println("--------- getLongestMovieTitle ---------");
        List<String> titles = movies.stream()
                .map(Movie::getTitle)
                .peek(title -> System.out.println(title + " : " + title.length()))
                .collect(Collectors.toList());

        System.out.println();
        return titles.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }


    public long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(movie -> movie.getDirectors().contains(director))
                .count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }

}
