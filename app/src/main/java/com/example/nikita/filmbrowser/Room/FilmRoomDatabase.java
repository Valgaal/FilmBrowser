package com.example.nikita.filmbrowser.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Film.class}, version = 1)
public abstract class FilmRoomDatabase extends RoomDatabase {
    public  abstract FilmDao filmDao();

    private static FilmRoomDatabase INSTANCE;

    static FilmRoomDatabase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (FilmRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext() ,
                            FilmRoomDatabase.class, "film_information").build();
                }
            }
        }
        return INSTANCE;
    }
}