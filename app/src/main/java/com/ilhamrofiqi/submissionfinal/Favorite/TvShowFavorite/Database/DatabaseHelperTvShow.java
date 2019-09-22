package com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperTvShow  extends SQLiteOpenHelper {

//    membuat database
    public static String DATABASE_NAME = "dbtvshowapp";

    private static final int DATABASE_VERSION = 1;

//    membuat table
    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s"
            + " (%s INTEGER PRIMARY KEY," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            DatabaseContractTvShow.TABLE_TVSHOW,
            DatabaseContractTvShow.TvShowColumns._ID,
            DatabaseContractTvShow.TvShowColumns.TITLE_TVSHOW,
            DatabaseContractTvShow.TvShowColumns.OVERVIEW_TVSHOW,
            DatabaseContractTvShow.TvShowColumns.RELEASE_TVSHOW,
            DatabaseContractTvShow.TvShowColumns.VOTE_TVSHOW,
            DatabaseContractTvShow.TvShowColumns.POSTER_TVSHOW
    );

    public DatabaseHelperTvShow(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContractTvShow.TABLE_TVSHOW);
        onCreate(db);
    }
}
