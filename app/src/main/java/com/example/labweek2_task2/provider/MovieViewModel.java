package com.example.labweek2_task2.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository repo;
    private LiveData<List<Movie>> movies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repo = new MovieRepository(application);
        movies = repo.getAllMovie();
    }

    public LiveData<List<Movie>> getMovies(){
        return movies;
    }

    public void insertNewMovies(Movie movie){
        repo.insertMovie(movie);
    }

    public void removeAll(){
        repo.deleteAll();
    }

}
