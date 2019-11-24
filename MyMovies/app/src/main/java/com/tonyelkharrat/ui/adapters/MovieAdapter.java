package com.tonyelkharrat.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tonyelkharrat.model.Movie;
import com.tonyelkharrat.R;
import com.tonyelkharrat.ui.managers.AnimationManager;
import com.tonyelkharrat.ui.managers.UrlManager;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private static MovieClickListener callbackMovieCliked;
    private static MovieLikedListener callbackMovieLiked;

    //Call back when a movie was cliked
    public static void setMovieClickedListener(MovieClickListener movieClickListener){
        callbackMovieCliked = movieClickListener;
    }
    //Call back when a movie was Liked
    public static void setMovieLikedListener(MovieLikedListener movieLikedListener){
        callbackMovieLiked = movieLikedListener;
    }

    public interface MovieClickListener{
        void onMovieClicked(Movie movie);
    }

    public interface MovieLikedListener{
        void onMovieLiked(Movie movie);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleTV;
        private ImageView pictureIV;
        private SpinKitView loadingSV;
        private CardView movieCV;
        private ImageView like_IV;
        private ProgressBar progressBar;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            pictureIV = itemView.findViewById(R.id.movie_picture_IV);
            loadingSV = itemView.findViewById(R.id.loading_animation_SK);
            titleTV = itemView.findViewById(R.id.movie_title_ET);
            movieCV = itemView.findViewById(R.id.movie_cardview);
            like_IV = itemView.findViewById(R.id.liked_IV);
            progressBar = itemView.findViewById(R.id.progress_bar);
            movieCV.setOnClickListener(this);
            like_IV.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.movie_cardview) {
                callbackMovieCliked.onMovieClicked(movies.get(getAdapterPosition()));
            } else if (view.getId() == R.id.liked_IV) {
                Movie movie = movies.get(getAdapterPosition());
                //Update state of movie
                if(movie.isLiked()){
                    movie.setLiked(false);
                }
                else{
                    movie.setLiked(true);
                }

                callbackMovieLiked.onMovieLiked(movie);
            }
        }
    }

    public void setMovies(List<Movie> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_movie,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {

        //Binding data

        Movie movie = movies.get(position);
        holder.titleTV.setText(movie.getOriginalTitle());

        AnimationManager.manageLoadingAnimation(holder.loadingSV,holder.progressBar);
        Picasso.get().load(UrlManager.getUrlPictureFromPath(movie.getPosterPath())).into(holder.pictureIV, new Callback() {
            @Override
            public void onSuccess() {
             AnimationManager.manageLoadingAnimation(holder.loadingSV,holder.progressBar);
            }
            @Override
            public void onError(Exception e) {
               AnimationManager.showSnackBarMessage(holder.itemView.getContext(),"Error occured during pictures loading ,Check your internet connexion");
            }
        });

        if(movie.isLiked()){
            holder.like_IV.setImageResource(R.drawable.ic_like_selected);
        }
        else{
            holder.like_IV.setImageResource(R.drawable.ic_like_unselected);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
