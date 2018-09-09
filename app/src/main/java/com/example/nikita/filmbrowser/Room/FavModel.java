package com.example.nikita.filmbrowser.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.List;

@Entity(tableName = "favorites")
public class FavModel {

    @Relation(parentColumn = "id", entityColumn = "id" , entity = Movie.class)
    @PrimaryKey
    private List<Movie> movies;

    @ColumnInfo(name = "Favorites")
    private boolean favorites;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public boolean isFavorites() {
        return favorites;
    }

    public void setFavorites(boolean favorites) {
        this.favorites = favorites;
    }

}
