package at.ac.fhcampuswien.fhmdb.repositories;

import at.ac.fhcampuswien.fhmdb.models.Genres;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieRepository {
    private final Dao<MovieEntity, String> movieDao;

    public MovieRepository(ConnectionSource connectionSource) throws SQLException {
        movieDao = DaoManager.createDao(connectionSource, MovieEntity.class);
    }

    public List<MovieEntity> getAllMovies() throws SQLException {
        return movieDao.queryForAll();
    }

    public void addMovie(MovieEntity movie) throws SQLException {
        MovieEntity existingMovie = movieDao.queryBuilder()
                .where()
                .eq("apiId", movie.getApiId())
                .queryForFirst();
        if (existingMovie == null) {
            movieDao.create(movie);
        }
    }


    public void removeMovie(String apiId) throws SQLException {
        MovieEntity movie = movieDao.queryBuilder()
                .where()
                .eq("apiId", apiId)
                .queryForFirst();

        if (movie != null) {
            movieDao.delete(movie);
        }
    }

    /**
     * Finds a movie by its API ID.
     * @param apiId The API ID of the movie to find.
     * @return The found MovieEntity or null if no movie matches the given API ID.
     * @throws SQLException If there is a problem executing the query.
     */
    public Movie findMovieByApiId(String apiId) throws SQLException {
        MovieEntity movieEntity = movieDao.queryBuilder()
                .where()
                .eq("apiId", apiId)
                .queryForFirst();
        return convertToMovie(movieEntity);
    }

    private Movie convertToMovie(MovieEntity movieEntity) {
        if (movieEntity == null) return null;

        // Assuming a proper constructor or a builder in Movie class
        return new Movie(
                movieEntity.getApiId(),
                movieEntity.getTitle(),
                movieEntity.getDescription(),
                movieEntity.getGenres(),
                movieEntity.getImgUrl(),
                movieEntity.getReleaseYear(),
                movieEntity.getRating(),
                null, // mainCast, fill with null or appropriate default
                null  // directors, fill with null or appropriate default
        );
    }
}

