package com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ilhamrofiqi.submissionfinal.Movies.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.POSTER;
import static com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.RELEASE;
import static com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.TITLE;
import static com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.VOTE;
import static com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.Database.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    public MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)));
                movie.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(VOTE)));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(RELEASE, movie.getRelease_date());
        args.put(VOTE, movie.getVote_average());
        args.put(POSTER, movie.getPoster_path());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMovie(int id) {
        return database.delete(TABLE_MOVIE, _ID + " = '" + id + "'", null);
    }
}
