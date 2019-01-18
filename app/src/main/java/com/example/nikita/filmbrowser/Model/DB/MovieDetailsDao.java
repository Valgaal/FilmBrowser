package com.example.nikita.filmbrowser.Model.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import io.reactivex.Single;

@Dao
public interface MovieDetailsDao {

    @Query("SELECT * FROM film_details_information WHERE id = :id")
    Single<MovieDetails> getMovie(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieDetails movieDetails);
}
