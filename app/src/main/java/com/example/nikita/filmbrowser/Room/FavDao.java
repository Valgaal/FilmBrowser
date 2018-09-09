package com.example.nikita.filmbrowser.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import io.reactivex.Single;

@Dao
public interface FavDao {

    @Query("SELECT * FROM favorites")
    Single<FavModel> getFavMovies();
}
