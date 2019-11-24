package com.tonyelkharrat.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.tonyelkharrat.viewmodel.MovieViewModel;
import com.tonyelkharrat.model.Movie;
import com.tonyelkharrat.R;
import com.tonyelkharrat.ui.adapters.MovieAdapter;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieLikedListener, MovieAdapter.MovieClickListener, View.OnClickListener {

    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView moviesRV = findViewById(R.id.movies_RV);
        ImageButton moviesLikedButton = findViewById(R.id.movies_liked_IB);

        moviesLikedButton.setOnClickListener(this);
        MovieAdapter.setMovieClickedListener(this);
        MovieAdapter.setMovieLikedListener(this);

        moviesRV.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        movieAdapter = new MovieAdapter();
        moviesRV.setAdapter(movieAdapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(List<Movie> movies) {
                       movieAdapter.setMovies(movies);
                       if(movies.isEmpty()){
                           movieViewModel.populateDatabase();
                       }
                    }
                });
    }

    @Override
    public void onMovieLiked(Movie movie) {
        //Update database with the new state of movie ( liked or not)
        movieViewModel.update(movie);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent detailsMovieIntent = new Intent(MainActivity.this, MovieActivity.class);
        //Passing the clicked movie to MovieActivity
        detailsMovieIntent.putExtra("movie_clicked",movie);

        if(Build.VERSION.SDK_INT >= 21) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
            startActivity(detailsMovieIntent,options.toBundle());
        }

        else{
            startActivity(detailsMovieIntent);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.movies_liked_IB){

            Intent moviesLikedIntent = new Intent(MainActivity.this, MoviesLikedListActivity.class);
            startActivity(moviesLikedIntent);
        }
    }


}
