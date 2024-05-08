package at.ac.fhcampuswien.fhmdb.repositories;

import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    private final Dao<WatchlistMovieEntity, String> watchlistDao;

    public WatchlistRepository(ConnectionSource connectionSource) throws SQLException {
        watchlistDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
    }

    public void addToWatchlist(WatchlistMovieEntity movie) throws SQLException {
        WatchlistMovieEntity existingMovie = watchlistDao.queryBuilder()
                .where()
                .eq("apiId", movie.getApiId())
                .queryForFirst();
        if (existingMovie == null) {
            watchlistDao.create(movie);
        }
    }

    public void removeFromWatchlist(String apiId) throws SQLException {
        WatchlistMovieEntity movie = watchlistDao.queryBuilder()
                .where()
                .eq("apiId", apiId)
                .queryForFirst();
        if (movie != null) {
            watchlistDao.delete(movie);
        }
    }


    public boolean isMovieInWatchlist(String apiId) throws SQLException {
        WatchlistMovieEntity movie = watchlistDao.queryBuilder()
                .where()
                .eq("apiId", apiId)
                .queryForFirst();
        return movie != null;
    }

    public List<WatchlistMovieEntity> getWatchlist() throws SQLException {
        return watchlistDao.queryForAll();
    }
}
