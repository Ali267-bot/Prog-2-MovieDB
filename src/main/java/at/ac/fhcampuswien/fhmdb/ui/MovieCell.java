package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Genres;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.repositories.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.util.ClickEventHandler;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.sql.SQLException;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genres = new Label();
    private final Label releaseYearLabel = new Label();
    private final Label ratingLabel = new Label();
    private final ImageView posterImage = new ImageView();
    private final Button actionButton = new Button();

    private final VBox textLayout = new VBox(title, detail, genres, releaseYearLabel, ratingLabel, actionButton);
    private final HBox layout = new HBox(posterImage, textLayout);
    private final ClickEventHandler<Movie> onAction;
    private final WatchlistRepository watchlistRepository;

    public MovieCell(DoubleBinding widthBinding, WatchlistRepository watchlistRepository, ClickEventHandler<Movie> onAction) {
        this.onAction = onAction;
        this.watchlistRepository = watchlistRepository;
        layout.maxWidthProperty().bind(widthBinding);
        textLayout.setSpacing(5);
        layout.setSpacing(10);
        posterImage.setFitWidth(100);
        posterImage.setFitHeight(150);
        posterImage.setPreserveRatio(true);

        actionButton.getStyleClass().add("button-watchlist");

        title.setWrapText(true);
        detail.setWrapText(true);
        genres.setWrapText(true);
        releaseYearLabel.setWrapText(true);
        ratingLabel.setWrapText(true);

        layout.setBackground(new Background(new BackgroundFill(Color.web("#262626"), CornerRadii.EMPTY, Insets.EMPTY)));
        layout.setPadding(new Insets(10));
        layout.spacingProperty().set(10);
        layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
            getStyleClass().remove("movie-cell");
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(movie.getDescription() != null ? movie.getDescription() : "No description available");
            StringBuilder genresText = new StringBuilder();
            for (Genres genre : movie.getGenres()) {
                if (!genresText.isEmpty()) genresText.append(", ");
                genresText.append(genre.name());
            }
            genres.setText(genresText.toString());
            releaseYearLabel.setText("Year: " + movie.getReleaseYear());
            ratingLabel.setText("Rating: " + movie.getRating());
            posterImage.setImage(movie.getImgUrl() != null && !movie.getImgUrl().isEmpty() ? ImageCache.getImage(movie.getImgUrl()) : null);

            try {
                configureButton(movie);
            } catch (SQLException e) {
                e.printStackTrace();
                actionButton.setText("Error");
                actionButton.setDisable(true);
            }

            title.getStyleClass().add("text-white");
            detail.getStyleClass().add("text-white");
            genres.getStyleClass().add("text-white");
            releaseYearLabel.getStyleClass().add("text-white");
            ratingLabel.getStyleClass().add("text-white");

            setGraphic(layout);
        }
    }

    private void configureButton(Movie movie) throws SQLException {
        if (watchlistRepository.isMovieInWatchlist(movie.getId())) {
            actionButton.setText("Remove from Watchlist");
            actionButton.setOnAction(e -> {
                removeFromWatchlist(movie);
                onAction.onClick(movie);
            });
        } else {
            actionButton.setText("Add to Watchlist");
            actionButton.setOnAction(e -> {
                addToWatchlist(movie);
                onAction.onClick(movie);
            });
        }
    }

    private void addToWatchlist(Movie movie) {
        try {
            watchlistRepository.addToWatchlist(new WatchlistMovieEntity(movie.getId()));
            configureButton(movie); // Refresh button state
        } catch (SQLException e) {
            System.err.println("Error adding to watchlist: " + e.getMessage());
        }
    }

    private void removeFromWatchlist(Movie movie) {
        try {
            watchlistRepository.removeFromWatchlist(movie.getId());
            configureButton(movie); // Refresh button state
        } catch (SQLException e) {
            System.err.println("Error removing from watchlist: " + e.getMessage());
        }
    }
}

