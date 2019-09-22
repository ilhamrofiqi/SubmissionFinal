package com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ilhamrofiqi.submissionfinal.TvShow.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Database.DatabaseContractTvShow.TABLE_TVSHOW;
import static com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Database.DatabaseContractTvShow.TvShowColumns.OVERVIEW_TVSHOW;
import static com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Database.DatabaseContractTvShow.TvShowColumns.POSTER_TVSHOW;
import static com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Database.DatabaseContractTvShow.TvShowColumns.RELEASE_TVSHOW;
import static com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Database.DatabaseContractTvShow.TvShowColumns.TITLE_TVSHOW;
import static com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Database.DatabaseContractTvShow.TvShowColumns.VOTE_TVSHOW;

public class TvShowHelper {
    private static final String DATABASE_TABLE = TABLE_TVSHOW;
    private static DatabaseHelperTvShow databaseHelperTvShow;
    private static TvShowHelper INSTANCE;

    private static SQLiteDatabase database;

    public TvShowHelper(Context context) {
        databaseHelperTvShow = new DatabaseHelperTvShow(context);
    }

//    menginisiasi database
    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

//    membuka & menutup database
    public void open() throws SQLException {
        database = databaseHelperTvShow.getWritableDatabase();
    }

    public void close() {
        databaseHelperTvShow.close();
        if (database.isOpen())
            database.close();
    }

//    proses CRUD
    public ArrayList<TvShow> getAllTvShows() {
        ArrayList<TvShow> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShow.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_TVSHOW)));
                tvShow.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW_TVSHOW)));
                tvShow.setFirst_air_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_TVSHOW)));
                tvShow.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_TVSHOW)));
                tvShow.setBackdrop_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_TVSHOW)));

                arrayList.add(tvShow);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

//    Menyimpan Data
    public long insertTvShow(TvShow tvShow) {
        ContentValues args = new ContentValues();
        args.put(_ID, tvShow.getId());
        args.put(TITLE_TVSHOW, tvShow.getName());
        args.put(OVERVIEW_TVSHOW, tvShow.getOverview());
        args.put(RELEASE_TVSHOW, tvShow.getFirst_air_date());
        args.put(VOTE_TVSHOW, tvShow.getVote_average());
        args.put(POSTER_TVSHOW, tvShow.getBackdrop_path());
        return database.insert(DATABASE_TABLE, null, args);
    }

//    Menghapus Data
    public int deleteTvShow(int id) {
        return database.delete(TABLE_TVSHOW, _ID + " = '" + id + "'", null);
    }
}