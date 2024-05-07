package at.ac.fhcampuswien.fhmdb.repositories;

import at.ac.fhcampuswien.fhmdb.models.MovieEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

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

}

