package at.ac.fhcampuswien.fhmdb.db;

import at.ac.fhcampuswien.fhmdb.models.MovieEntity;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:h2:mem:fhmdb";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    private ConnectionSource connectionSource;

    public DatabaseManager() {
        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTableIfNotExists() {
        try {
            TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }
}

