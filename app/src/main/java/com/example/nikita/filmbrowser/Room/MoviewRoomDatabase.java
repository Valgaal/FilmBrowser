package com.example.nikita.filmbrowser.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Movie.class}, version = 1)
public abstract class MoviewRoomDatabase extends RoomDatabase {
    public  abstract MovieDao filmDao();

    private static MoviewRoomDatabase INSTANCE;

    static MoviewRoomDatabase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (MoviewRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext() ,
                            MoviewRoomDatabase.class, "film_information").build();
                }
            }
        }
        return INSTANCE;
    }
}