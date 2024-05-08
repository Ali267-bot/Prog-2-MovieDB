package at.ac.fhcampuswien.fhmdb.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DatabaseTable(tableName = "movies")
public class MovieEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String apiId;

    @DatabaseField
    private String title;

    @DatabaseField
    private String description;

    @DatabaseField
    private String genres;

    @DatabaseField
    private int releaseYear;

    @DatabaseField
    private String imgUrl;

    @DatabaseField
    private int lengthInMinutes;

    @DatabaseField
    private double rating;

    public MovieEntity() {
    }

    public MovieEntity(String apiId, String title, String description, List<String> genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating) {
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = String.join(",", genres);
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Genres> getGenres() {
        return Arrays.stream(genres.split(","))
                .map(String::trim)
                .map(genre -> {
                    try {
                        return Genres.valueOf(genre.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Genre conversion error: " + genre);
                        return null;
                    }
                })
                .filter(genre -> genre != null)
                .collect(Collectors.toList());
    }

    public void setGenres(List<Genres> genres) {
        this.genres = convertGenresToString(genres);
    }

    private String convertGenresToString(List<Genres> genres) {
        return genres.stream()
                .map(Genres::name)
                .collect(Collectors.joining(","));
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Movie> toMovies(List<MovieEntity> list) {
        List<Movie> erg = new ArrayList<>();
        for (MovieEntity m : list) {
            erg.add(new Movie(
                    m.getApiId(),
                    m.getTitle(),
                    m.getDescription(),
                    m.getGenres(),
                    m.getImgUrl(),
                    m.getReleaseYear(),
                    m.getRating(),
                    null, // mainCast, fill with null or appropriate default
                    null  // directors, fill with null or appropriate default
            ));
        }
        return erg;
    }

    public List<MovieEntity> fromMovies(List<Movie> list) {
        List<MovieEntity> erg = new ArrayList<>();
        for (Movie m : list) {
            erg.add(new MovieEntity(
                    m.getId(),
                    m.getTitle(),
                    m.getDescription(),
                    new ArrayList<>(),
                    m.getReleaseYear(),
                    m.getImgUrl(),
                    0,
                    m.getRating()
            ));
        }
        return erg;
    }
}

