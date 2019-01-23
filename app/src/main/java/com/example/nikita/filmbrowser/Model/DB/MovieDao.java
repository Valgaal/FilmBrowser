package com.example.nikita.filmbrowser.Model.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Update
    void update(Movie movie);

    @Query("SELECT * FROM film_information WHERE trending = 1")
    Single<List<Movie>> getTrending();

    @Query("SELECT * FROM film_information WHERE id = :id")
    Single<Movie> getMovieById(int id);

    @Query("SELECT * FROM film_information WHERE Favorites = 1")
    Single<List<Movie>> getFavorites();

    @Query("UPDATE film_information SET trending = 0 WHERE trending = 1")
    void clearTrending();
}
