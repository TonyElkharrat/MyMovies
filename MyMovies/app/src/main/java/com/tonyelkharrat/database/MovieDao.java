package com.tonyelkharrat.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.tonyelkharrat.model.Movie;

import java.util.Collection;
import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Collection<Movie> movie);

    @Update
    void update(Movie movie);

    @Query("SELECT * FROM movie_table")
    LiveData<List<Movie>> getAllmovies();

    @Query("SELECT * FROM movie_table WHERE isLiked = 1 ")
    LiveData<List<Movie>> getLikedMovies();

}
