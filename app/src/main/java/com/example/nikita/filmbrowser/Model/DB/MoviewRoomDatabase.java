package com.example.nikita.filmbrowser.Model.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Movie.class, MovieDetails.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MoviewRoomDatabase extends RoomDatabase {
    public abstract MovieDao filmDao();
    public abstract MovieDetailsDao detailsDao();

    private static MoviewRoomDatabase INSTANCE;

    public static MoviewRoomDatabase getInstance(final Context context){
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