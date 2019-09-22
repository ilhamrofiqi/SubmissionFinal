package com.ilhamrofiqi.submissionfinal.Favorite;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilhamrofiqi.submissionfinal.Favorite.Adapter.ViewPagerAdapter;
import com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.MovieFavoriteFragment;
import com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.TvShowFavoriteFragment;
import com.ilhamrofiqi.submissionfinal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedinstaceState) {
        super.onViewCreated(view, savedinstaceState);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.AddFragment(new MovieFavoriteFragment(), getString(R.string.navigation_movies));
        adapter.AddFragment(new TvShowFavoriteFragment(), getString(R.string.navigation_tv_show));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
