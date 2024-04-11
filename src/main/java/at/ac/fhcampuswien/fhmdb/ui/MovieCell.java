package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Genres;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();

    private final Label genres = new Label();
    private final Label releaseYearLabel = new Label();
    private final Label ratingLabel = new Label();
    private final ImageView posterImage = new ImageView();

    private final VBox textLayout = new VBox(title, detail, genres, releaseYearLabel, ratingLabel);
    private final HBox layout = new HBox(posterImage, textLayout);

    public MovieCell() {
        textLayout.setSpacing(5);
        layout.setSpacing(10);
        posterImage.setFitWidth(100);
        posterImage.setFitHeight(150);
        posterImage.setPreserveRatio(true);
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
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );

            StringBuilder genresText = new StringBuilder();
            for (Genres genre : movie.getGenres()) {
                if (!genresText.isEmpty()) genresText.append(", ");
                genresText.append(genre.name());
            }

            genres.setText(genresText.toString());



            // color scheme
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            genres.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            releaseYearLabel.setText("Year: " + movie.getReleaseYear());
            ratingLabel.setText("Rating: " + movie.getRating());

            if (movie.getImgUrl() != null && !movie.getImgUrl().isEmpty()) {
                posterImage.setImage(ImageCache.getImage(movie.getImgUrl()));
            } else {
                posterImage.setImage(null);
            }


            // Style and layout adjustments for new labels
            releaseYearLabel.getStyleClass().add("text-white");
            ratingLabel.getStyleClass().add("text-white");

            // layout
            title.fontProperty().set(title.getFont().font(20));
            detail.maxWidthProperty().bind(layout.widthProperty().subtract(posterImage.fitWidthProperty()).subtract(30));
            detail.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            setGraphic(layout);
        }
    }
}

