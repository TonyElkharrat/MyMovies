package com.tonyelkharrat.database;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tonyelkharrat.model.Movie;
import com.tonyelkharrat.network.DataBaseCalls;

import java.util.List;

@Database(entities = {Movie.class}, version = 1)

public abstract class MovieDataBase extends RoomDatabase {

    private static MovieDataBase instance;
    private static final String DATABASE_NAME = "movie_database";
   static Context  context;

    public abstract MovieDao movieDao();

    //Get single Instance of the database
    public static synchronized MovieDataBase getInstance(Context i_context){

        if(instance == null){

            instance = Room.databaseBuilder(i_context.getApplicationContext(),
                    MovieDataBase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        context = i_context;

        return instance;
    }

    //if the database is null initialize it
    public  void populateDatabase(){

        DataBaseCalls.fetchMovies(new DataBaseCalls.Callbacks() {
            @Override
            public void onResponse(@Nullable List<Movie> movies) {
                new InitializeMoviesAsyncTask(instance.movieDao()).execute(movies);
            }

            @Override
            public void onFailure() {
               Toast.makeText(context,"Check your internet connection ",Toast.LENGTH_LONG).show();
            }
        },"2019");
    }

    //Asynctask request for databaste query
    private static class InitializeMoviesAsyncTask extends AsyncTask<List<Movie>,Void,Void> {

        private MovieDao movieDao;

        private InitializeMoviesAsyncTask(MovieDao movieDao){
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(List<Movie>... movies) {
            movieDao.insert(movies[0]);
            return null;
        }
    }
}
