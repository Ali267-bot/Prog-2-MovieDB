package at.ac.fhcampuswien.fhmdb.db;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.MovieEntity;
import at.ac.fhcampuswien.fhmdb.models.WatchlistMovieEntity;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:h2:mem:fhmdb";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    private ConnectionSource connectionSource;



    public void createTableIfNotExists() throws DatabaseException.OperationException {
        try {
            TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
            System.out.println("Movie table created successfully.");

            TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
            System.out.println("Watchlist table created successfully.");
        } catch (SQLException e) {
            throw new DatabaseException.OperationException("Error creating tables: " + e.getMessage(), e);
        }
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }
}

