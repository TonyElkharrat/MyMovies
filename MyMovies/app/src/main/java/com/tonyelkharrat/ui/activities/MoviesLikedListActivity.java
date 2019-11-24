package com.tonyelkharrat.ui.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tonyelkharrat.R;
import com.tonyelkharrat.model.Movie;
import com.tonyelkharrat.ui.adapters.MovieAdapter;
import com.tonyelkharrat.viewmodel.MovieViewModel;

import java.util.List;

public class MoviesLikedListActivity extends AppCompatActivity {
    MovieViewModel movieViewModel ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielikedlist);

        RecyclerView allMoviesLikedRV = findViewById(R.id.movielikedList_RV);
        allMoviesLikedRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        MovieAdapter movieAdapter = new MovieAdapter();
        allMoviesLikedRV.setAdapter(movieAdapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getAllMoviesLiked().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
               movieAdapter.setMovies(movies);
            }
        });
    }
}
