package com.ilhamrofiqi.submissionfinal.Widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.Database.MovieHelper;
import com.ilhamrofiqi.submissionfinal.Movies.Movie;
import com.ilhamrofiqi.submissionfinal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Movie> movie = new ArrayList<>();
    private final Context mContext;
    private MovieHelper movieHelper;
    private static final String POSTER_URL = "https://image.tmdb.org/t/p/w500";

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        movieHelper = MovieHelper.getInstance(mContext);
        movieHelper.open();
        movie.addAll(movieHelper.getAllMovies());

    }

    @Override
    public void onDataSetChanged() {
        movieHelper = MovieHelper.getInstance(mContext);
        movieHelper.open();
        movie.addAll(movieHelper.getAllMovies());
    }

    @Override
    public void onDestroy() {
        movieHelper.close();
    }

    @Override
    public int getCount() {
        return movie.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Movie item = movie.get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(POSTER_URL + item.getPoster_path())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle  extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
