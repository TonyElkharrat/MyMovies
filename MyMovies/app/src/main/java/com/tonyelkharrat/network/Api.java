package com.tonyelkharrat.network;

import com.tonyelkharrat.model.MovieResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("/3/{method}/movie")
    Call<MovieResults> getListOfMovies(
            @Path("method") String method,
            @Query("api_key") String apiKey,
            @Query("primary_release_year") String year
    );
}
