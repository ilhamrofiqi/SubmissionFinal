package com.ilhamrofiqi.submissionfinal.TvShow;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Database.TvShowHelper;
import com.ilhamrofiqi.submissionfinal.R;

public class DetailTvShowActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TVSHOW = "extra_tvshow";
    public static final String EXTRA_POSITION = "extra_position";

    private boolean isEdit = false;
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;

    private TvShow tvShows;
    private int position;

    TvShowAdapter adapter;

    private TvShowHelper tvShowHelper;

    TextView tvName, tvOverview, tvVote, tvDate;
    ImageView imgBackdrop_path;
    Button btnFavorite;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        tvName = findViewById(R.id.tv_tv_show_name);
        tvOverview = findViewById(R.id.tv_tv_show_overview);
        tvVote = findViewById(R.id.tv_tv_show_vote);
        tvDate = findViewById(R.id.tv_tv_show_first_air_date);
        imgBackdrop_path = findViewById(R.id.img_tv_show_backdrop_path);
        btnFavorite = findViewById(R.id.btn_favorite);

        progressBar = findViewById(R.id.progressBar);
        progressBar.bringToFront();

        tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
        tvShowHelper.open();

        adapter = new TvShowAdapter();
        adapter.notifyDataSetChanged();

        tvShows = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        if (position > 0) {
            isEdit = true;
        }

        tvName.setText(tvShows.getName());
        tvOverview.setText(tvShows.getOverview());
        tvVote.setText(tvShows.getVote_average());
        tvDate.setText(tvShows.getFirst_air_date());

        String url = "https://image.tmdb.org/t/p/w500" + tvShows.getBackdrop_path();
        Glide.with(DetailTvShowActivity.this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imgBackdrop_path);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tvName.getText().toString().trim();
                String vote = tvVote.getText().toString().trim();
                String overview = tvOverview.getText().toString().trim();
                String date = tvDate.getText().toString().trim();

                tvShows.setName(name);
                tvShows.setOverview(overview);
                tvShows.setVote_average(vote);
                tvShows.setFirst_air_date(date);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_TVSHOW, tvShows);
                intent.putExtra(EXTRA_POSITION, position);
                if (!isEdit) {
                    long result = tvShowHelper.insertTvShow(tvShows);
                    if (result > 0) {
                        tvShows.setId((int) result);
                        setResult(RESULT_ADD, intent);
                        Toast.makeText(DetailTvShowActivity.this, "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
