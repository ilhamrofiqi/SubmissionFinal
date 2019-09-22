package com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.Database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_MOVIE = "movie";

    static final class MovieColumns implements BaseColumns {

        static String TITLE = "title";
        static String OVERVIEW = "overview";
        static String RELEASE = "release";
        static String VOTE = "vote";
        static String POSTER = "poster";
    }
}
