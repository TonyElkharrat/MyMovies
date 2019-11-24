package com.tonyelkharrat.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.tonyelkharrat.model.Movie;
import com.tonyelkharrat.database.MovieDao;
import com.tonyelkharrat.database.MovieDataBase;

import java.util.List;

public class MovieRepository{

    private MovieDao movieDao;
    private LiveData<List<Movie>> allMovies;
    private LiveData<List<Movie>> allMoviesLiked;
    MovieDataBase movieDataBase;

    public MovieRepository(Application application){
        movieDataBase = MovieDataBase.getInstance(application);
        movieDao = movieDataBase.movieDao();
        allMovies = movieDao.getAllmovies();
        allMoviesLiked = movieDao.getLikedMovies();
    }

    public void update(Movie movie){
        new UpdateMovieAsyncTask(movieDao).execute(movie);
    }

    public LiveData<List<Movie>> getAllMovies(){
        return allMovies;
    }

    public LiveData<List<Movie>> getAllMoviesLiked(){
         return allMoviesLiked;
    }

    public void populateDatabase(){
        movieDataBase.populateDatabase();
    }
    //Asynctask request for updating Database
    private static class UpdateMovieAsyncTask extends AsyncTask<Movie,Void,Void>{

        private MovieDao movieDao;
        private UpdateMovieAsyncTask(MovieDao movieDao){
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.update(movies[0]);
            return null;
        }
    }
}
