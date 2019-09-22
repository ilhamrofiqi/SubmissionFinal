package com.ilhamrofiqi.submissionfinal.TvShowWidget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Database.TvShowHelper;
import com.ilhamrofiqi.submissionfinal.R;
import com.ilhamrofiqi.submissionfinal.TvShow.TvShow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

class TvShowStackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<TvShow> tvShow = new ArrayList<>();
    private final Context TvShowcontext;
    private TvShowHelper tvShowHelper;
    private static final String POSTER_URL = "https://image.tmdb.org/t/p/w500";

    TvShowStackRemoteViewsFactory(Context context) {
        TvShowcontext = context;
    }

    @Override
    public void onCreate() {
        tvShowHelper = TvShowHelper.getInstance(TvShowcontext);
        tvShowHelper.open();
        tvShow.addAll(tvShowHelper.getAllTvShows());
    }

    @Override
    public void onDataSetChanged() {
        tvShowHelper = TvShowHelper.getInstance(TvShowcontext);
        tvShowHelper.open();
        tvShow.addAll(tvShowHelper.getAllTvShows());

    }

    @Override
    public void onDestroy() {
        tvShowHelper.close();
    }

    @Override
    public int getCount() {
        return tvShow.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        TvShow tvShowItem = tvShow.get(position);
        RemoteViews rvTvShow = new RemoteViews(TvShowcontext.getPackageName(), R.layout.widget_item_tv_show);
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(TvShowcontext)
                    .asBitmap()
                    .load(POSTER_URL + tvShowItem.getBackdrop_path())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        rvTvShow.setImageViewBitmap(R.id.imageView_tv_show, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(TvShowFavoriteWidget.EXTRA_ITEM_TV_SHOW, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rvTvShow.setOnClickFillInIntent(R.id.imageView_tv_show, fillInIntent);
        return rvTvShow;
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
