package com.example.nikita.filmbrowser.Model.DB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;

@Entity(tableName = "film_information")
public class Movie {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "PosterPath")
    private String posterPath;

    @ColumnInfo(name = "Description")
    private String description;

    @ColumnInfo(name = "Overview")
    private String overview;

    @ColumnInfo(name = "RatingAverage")
    private double ratingAvg;

    @ColumnInfo(name = "ReleaseDate")
    private String releaseDate;

    @ColumnInfo(name = "Trending")
    private boolean trending;

    @ColumnInfo(name = "Favorites")
    private boolean favorites;

    public boolean isFavorites() {
        return favorites;
    }

    public void setFavorites(boolean favorites) {
        this.favorites = favorites;
    }

    public boolean isTrending() {
        return trending;
    }

    public void setTrending(boolean trending) {
        this.trending = trending;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
