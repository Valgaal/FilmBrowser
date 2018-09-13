package com.example.nikita.filmbrowser.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "film_details_information")
public class MovieDetails implements Serializable{

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "PosterPath")
    private String posterPath;

    @ColumnInfo(name = "Overview")
    private String overview;

    @ColumnInfo(name = "RatingAverage")
    private double ratingAvg;

    @ColumnInfo(name = "ReleaseDate")
    private String releaseDate;

    @ColumnInfo(name = "Revenue")
    private int revenue;

    @ColumnInfo(name = "Genres")
    private ArrayList<String> genres;

    @ColumnInfo(name = "Country")
    private ArrayList<String> countries;

    @ColumnInfo(name = "Runtime")
    private int runtime;

    @ColumnInfo(name = "Popularity")
    private double popularity;

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }


    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
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

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}
