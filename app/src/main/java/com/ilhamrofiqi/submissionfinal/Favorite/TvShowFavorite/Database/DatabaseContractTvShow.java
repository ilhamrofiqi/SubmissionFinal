package com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Database;

import android.provider.BaseColumns;

public class DatabaseContractTvShow {

//    mempermudah akses nama table dan kolom dalam database

    static String TABLE_TVSHOW = "tvshow";

    static final class TvShowColumns implements BaseColumns {

        static String TITLE_TVSHOW = "title_tvshow";
        static String OVERVIEW_TVSHOW = "overview_tvshow";
        static String RELEASE_TVSHOW = "release_tvshow";
        static String VOTE_TVSHOW = "vote_tvshow";
        static String POSTER_TVSHOW = "postertv_show";
    }
}
