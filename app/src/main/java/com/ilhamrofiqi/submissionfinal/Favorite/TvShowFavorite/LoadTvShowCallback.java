package com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite;

import com.ilhamrofiqi.submissionfinal.TvShow.TvShow;

import java.util.ArrayList;

public interface LoadTvShowCallback {
    void preExecute();
    void postExecute(ArrayList<TvShow> tvshows);
}
