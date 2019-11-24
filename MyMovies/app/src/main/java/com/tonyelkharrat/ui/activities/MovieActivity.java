package com.tonyelkharrat.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tonyelkharrat.model.Movie;
import com.tonyelkharrat.R;
import com.tonyelkharrat.ui.managers.AnimationManager;
import com.tonyelkharrat.ui.managers.UrlManager;

public class MovieActivity extends Activity implements View.OnClickListener {

    ImageView imageMovie ;
    TextView  ratingNumberMovie;
    TextView  yearOfMovie;
    TextView  descriptionMovie;
    TextView  movieTitle;
    ImageView backButton;
    SpinKitView loadingSV;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //Getting the parcelable object
        Bundle data = getIntent().getExtras();
        Movie movie = data.getParcelable("movie_clicked");

        imageMovie = findViewById(R.id.movie_image_IV);
        ratingNumberMovie = findViewById(R.id.rating_number_TV);
        yearOfMovie = findViewById(R.id.year_movie_TV);
        descriptionMovie = findViewById(R.id.movie_description_TV);
        movieTitle = findViewById(R.id.title_movie_TV);
        backButton = findViewById(R.id.back_button);
        progressBar = findViewById(R.id.progress_bar);
        loadingSV = findViewById(R.id.loading_animation_SK);


        backButton.setOnClickListener(this);

        bindingUI(movie);
    }

    private void bindingUI(Movie movie){

        AnimationManager.manageLoadingAnimation(loadingSV,progressBar);
        Picasso.get().load(UrlManager.getUrlPictureFromPath(movie.getBackdropPath())).into(imageMovie, new Callback() {
            @Override
            public void onSuccess() {
                AnimationManager.manageLoadingAnimation(loadingSV,progressBar);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        ratingNumberMovie.setText(movie.getVoteCount()+"");
        yearOfMovie.setText(movie.getReleaseDate());
        descriptionMovie.setText(movie.getOverview());
        movieTitle.setText(movie.getTitle());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.back_button){
            finish();
        }
    }
}
