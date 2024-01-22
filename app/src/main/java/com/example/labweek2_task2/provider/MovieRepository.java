package com.example.labweek2_task2.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private movieDao movieDao;
    private LiveData<List<Movie>> allMovie;
    private ArrayList<Movie> nonLiveMovie;
    MovieDatabase db;


    //access the db
    public MovieRepository(Application application) {
        db = MovieDatabase.getMovieDatabase(application);
        movieDao=db.movieDao();

        allMovie = db.movieDao().getAllMovieInputs();

    }

    //  Fetch from DB
    LiveData<List<Movie>> getAllMovie() {
        return allMovie;
    }

    void insertMovie(Movie movie){
        MovieDatabase.dbWriter.execute(()->{movieDao.addThings(movie);});
    }

    void deleteAll(){
        MovieDatabase.dbWriter.execute(()->{movieDao.removeAll();});
    }

}
