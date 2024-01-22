package com.example.labweek2_task2.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

//Makes sure its not a regular interface, but a DATA ACCESS OBJECT
@Dao
public interface movieDao {
    @Query("SELECT * from MovieTable")
    LiveData<List<Movie>> getAllMovieInputs();
    // ^ Returns list of movies

    @Insert
    void addThings(Movie MovieTable);

    @Query("DELETE from MovieTable")
    void removeAll();

}
