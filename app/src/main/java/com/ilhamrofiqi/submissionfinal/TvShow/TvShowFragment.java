package com.ilhamrofiqi.submissionfinal.TvShow;


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
public class TvShowFragment extends Fragment implements View.OnClickListener {
    private TvShowAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView rvTvShow;

    private MainViewModelTvShow mainViewModelTvShow;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstaceState) {
        super.onViewCreated(view, savedInstaceState);

        adapter = new TvShowAdapter();
        adapter.notifyDataSetChanged();

        rvTvShow = view.findViewById(R.id.rv_category_tv_show);
        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTvShow.setAdapter(adapter);
        rvTvShow.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.bringToFront();

        mainViewModelTvShow = ViewModelProviders.of(getActivity()).get(MainViewModelTvShow.class);
        mainViewModelTvShow.getTvShow().observe(getActivity(), getTvShow);

        adapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShow data) {
                showSlectedTvShow(data);
            }
        });
    }

    private void showSlectedTvShow(TvShow tvShow) {
        Intent intent = new Intent(getActivity().getApplication(), DetailTvShowActivity.class);
        intent.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, tvShow);
        startActivity(intent);
    }

    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                adapter.setData(tvShows);
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstaceState) {
        super.onActivityCreated(savedInstaceState);

        mainViewModelTvShow.setTvShow();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mainViewModelTvShow.getTvShow().removeObserver(getTvShow);
    }

    @Override
    public void onClick(View v) {

    }
}
