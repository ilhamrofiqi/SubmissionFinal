package com.ilhamrofiqi.submissionfinal.Movies;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ilhamrofiqi.submissionfinal.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements View.OnClickListener {
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView rvMovie;

    private MainViewModelMovie mainViewModelMovie;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedinstaceState) {
        super.onViewCreated(view, savedinstaceState);

        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();

        rvMovie = view.findViewById(R.id.rv_category);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setAdapter(adapter);
        rvMovie.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.bringToFront();

        mainViewModelMovie = ViewModelProviders.of(getActivity()).get(MainViewModelMovie.class);
        mainViewModelMovie.getMovie().observe(getActivity(), getMovie);

        adapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                showSlectedMovie(data);
            }
        });
    }

    private void showSlectedMovie(Movie movie) {
//        Toast.makeText(getActivity(), "Kamu memilih" + movie.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity().getApplication(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setData(movies);
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainViewModelMovie.setMovie();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mainViewModelMovie.getMovie().removeObserver(getMovie);
    }

    @Override
    public void onClick(View v) {

    }

}
