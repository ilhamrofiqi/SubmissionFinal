package com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Adapter.TvShowFavoriteAdapter;
import com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Database.TvShowHelper;
import com.ilhamrofiqi.submissionfinal.R;
import com.ilhamrofiqi.submissionfinal.TvShow.DetailTvShowActivity;
import com.ilhamrofiqi.submissionfinal.TvShow.TvShow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment implements View.OnClickListener, LoadTvShowCallback{

    private RecyclerView rvTvShow;
    private ProgressBar progressBar;
    public static final String EXTRA_STATE = "EXTRA_STATE";
    private TvShowFavoriteAdapter adapter;
    private TvShowHelper tvShowHelper;
    ArrayList<TvShow> tvShow;

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvShow = new ArrayList<>();

        rvTvShow = view.findViewById(R.id.rv_tv_show_favorite);
        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTvShow.setHasFixedSize(true);

        tvShowHelper = new TvShowHelper(getActivity().getApplicationContext());
        tvShowHelper.open();

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.bringToFront();

        adapter = new TvShowFavoriteAdapter(getActivity());
        adapter.notifyDataSetChanged();
        rvTvShow.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadTvShowAsync(tvShowHelper, this).execute();
        } else {
            ArrayList<TvShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListTvShows(list);
            }
        }
    }

        @Override
        public void onSaveInstanceState(Bundle outState){
            super.onSaveInstanceState(outState);
            outState.putParcelableArrayList(EXTRA_STATE, adapter.getListTvShows());
        }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void preExecute() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<TvShow> tvshows) {
        if (tvshows != null) {
            progressBar.setVisibility(View.GONE);
            adapter.setListTvShows(tvshows);
        }
//        progressBar.setVisibility(View.INVISIBLE);
//        adapter.setListTvShows(tvshows);
    }

    private static class LoadTvShowAsync extends AsyncTask<Void, Void, ArrayList<TvShow>> {

        private final WeakReference<TvShowHelper> weakTvShowHelper;
        private final WeakReference<LoadTvShowCallback> weakCallback;

        private LoadTvShowAsync(TvShowHelper tvShowHelper, LoadTvShowCallback callback) {
            weakTvShowHelper = new WeakReference<>(tvShowHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<TvShow> doInBackground(Void... voids) {
            return weakTvShowHelper.get().getAllTvShows();
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> tvShows) {
            super.onPostExecute(tvShows);
            weakCallback.get().postExecute(tvShows);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == DetailTvShowActivity.REQUEST_ADD) {
                if (resultCode == DetailTvShowActivity.RESULT_ADD) {
                    TvShow tvShow = data.getParcelableExtra(DetailTvShowActivity.EXTRA_TVSHOW);
                    adapter.addItem(tvShow);
                    rvTvShow.smoothScrollToPosition(adapter.getItemCount() - 1);
                }
            } else if (requestCode == DetailTvShowFavoriteActivity.REQUEST_UPDATE) {
                if (resultCode == DetailTvShowFavoriteActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(DetailTvShowFavoriteActivity.EXTRA_POSITION, 0);
                    adapter.removeItem(position);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvShowHelper.close();
    }
}
