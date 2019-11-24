package com.tonyelkharrat.network;

import android.os.Build;

import androidx.annotation.Nullable;

import com.tonyelkharrat.model.Movie;
import com.tonyelkharrat.model.MovieResults;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataBaseCalls {


    public interface Callbacks {
        void onResponse(@Nullable List<Movie> movies);
        void onFailure();
    }

    private static String API_KEY  = "c77cca1f004d52f7d1e1a905c783d112" ;

    public static void fetchMovies (final Callbacks callbacks, String year) {

        // Weak reference created to callback (avoid memory leaks)



        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        //Getting Rest Client Instance
        Api movieService = RetrofitInstance.getRetrofitInstance().create(Api.class);

        //Call created on TMDB API
        Call<MovieResults> call = movieService.getListOfMovies("discover",API_KEY,year);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {

                //Call callback
                if(callbacksWeakReference.get()!=null) {
                    //Get all the Result Object
                    MovieResults movieResults = response.body();

                    //Retreive all the movies
                    List<Movie> allMovies = movieResults.getResults();
                        callbacksWeakReference.get().onResponse(allMovies);
                }
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

                if(callbacksWeakReference.get()!=null)
                   callbacksWeakReference.get().onFailure();
            }
        });
    }
}
