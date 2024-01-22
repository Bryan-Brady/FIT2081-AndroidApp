package com.example.labweek2_task2.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Version = change it if there is an addition in Movie.class
@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public static final String DB_NAME = "MOVIE_DATABASE";

    public abstract movieDao movieDao();

    private static volatile MovieDatabase instance;

    static final ExecutorService dbWriter = Executors.newFixedThreadPool(4);

    public static MovieDatabase getMovieDatabase(Context context){
       if(instance==null){
           // Stops threads from running all at once
           synchronized (MovieDatabase.class) {
               if(instance==null) {
                   instance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DB_NAME)
                           .build();
               }
           }
       }
        return instance;
    }


}
