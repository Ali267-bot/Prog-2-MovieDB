package at.ac.fhcampuswien.fhmdb.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "watchlist")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(index = true) // Optionally make it an index for faster query performance
    private String apiId;

    // ORMLite requires a no-arg constructor
    public WatchlistMovieEntity() {
    }

    // Constructor for creating a new watchlist entry with an apiId
    public WatchlistMovieEntity(String apiId) {
        this.apiId = apiId;
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
}
