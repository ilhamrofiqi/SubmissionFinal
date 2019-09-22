package com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite;

import com.ilhamrofiqi.submissionfinal.Movies.Movie;

import java.util.ArrayList;

public interface LoadMovieCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);
}
