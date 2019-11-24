package com.tonyelkharrat.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.tonyelkharrat.repository.MovieRepository;
import com.tonyelkharrat.model.Movie;
import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;
    private LiveData<List<Movie>> allMovies;
    private LiveData<List<Movie>> allMoviesLiked;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        allMovies = movieRepository.getAllMovies();
        allMoviesLiked = movieRepository.getAllMoviesLiked();
    }

    public void update(Movie movie){
        movieRepository.update(movie);
    }

    public LiveData<List<Movie>> getAllMovies(){
        return allMovies;
    }

    public LiveData<List<Movie>> getAllMoviesLiked(){
        return allMoviesLiked;
    }

    public void populateDatabase(){
        movieRepository.populateDatabase();
    }
}
