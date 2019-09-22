package com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite;


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

import com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.Database.MovieHelper;
import com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.Adaper.MovieFavoriteAdapter;
import com.ilhamrofiqi.submissionfinal.Movies.DetailMovieActivity;
import com.ilhamrofiqi.submissionfinal.Movies.Movie;
import com.ilhamrofiqi.submissionfinal.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment implements View.OnClickListener, LoadMovieCallback{

    private RecyclerView rvMovie;
    private ProgressBar progressBar;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private MovieFavoriteAdapter adapter;
    private MovieHelper movieHelper;
    ArrayList<Movie> movie;


    public MovieFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movie = new ArrayList<>();

        rvMovie = view.findViewById(R.id.rv_movie_favorite);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);

        movieHelper = new MovieHelper(getActivity().getApplicationContext());
        movieHelper.open();

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.bringToFront();

        adapter = new MovieFavoriteAdapter(getActivity());
        adapter.notifyDataSetChanged();
        rvMovie.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadMovieAsync(movieHelper, this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListMovies(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovies());
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
    public void postExecute(ArrayList<Movie> movies) {
        if (movies != null) {
            progressBar.setVisibility(View.GONE);
            adapter.setListMovies(movies);
        }
    }

    private static class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final WeakReference<MovieHelper> weakMovieHelper;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadMovieAsync(MovieHelper movieHelper, LoadMovieCallback callback) {
            weakMovieHelper = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return weakMovieHelper.get().getAllMovies();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == DetailMovieActivity.REQUEST_ADD) {
                if (resultCode == DetailMovieActivity.RESULT_ADD) {
                    Movie movie = data.getParcelableExtra(DetailMovieActivity.EXTRA_MOVIE);
                    adapter.addItem(movie);
                    rvMovie.smoothScrollToPosition(adapter.getItemCount() - 1);
                }
            } else if (requestCode == DetailMovieFavoriteActivity.REQUEST_UPDATE) {
                if (resultCode == DetailMovieFavoriteActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(DetailMovieFavoriteActivity.EXTRA_POSITION, 0);
                    adapter.removeItem(position);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
