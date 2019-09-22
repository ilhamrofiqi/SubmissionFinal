package com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite;

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
import com.ilhamrofiqi.submissionfinal.MainActivity;
import com.ilhamrofiqi.submissionfinal.R;
import com.ilhamrofiqi.submissionfinal.TvShow.TvShow;
import com.ilhamrofiqi.submissionfinal.TvShow.TvShowAdapter;

public class DetailTvShowFavoriteActivity extends AppCompatActivity {

    TextView tvTitle, tvOverview, tvRelease, tvVote;
    ImageView imgPoster;
    TvShowAdapter adapter;
    Button btnDelete;

    private ProgressBar progressBar;
    private TvShowHelper tvShowHelper;
    private TvShow tvShow;
    private int position;

    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 300;

    public static final String EXTRA_TVSHOW = "extra_tvShow";
    public static final String EXTRA_POSITION = "extra_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show_favorite);

        tvTitle = findViewById(R.id.tv_tv_show_name);
        tvOverview = findViewById(R.id.tv_tv_show_overview);
        tvRelease = findViewById(R.id.tv_tv_show_first_air_date);
        tvVote = findViewById(R.id.tv_tv_show_vote);
        imgPoster = findViewById(R.id.img_tv_show_backdrop_path);
        btnDelete = findViewById(R.id.btn_delete);

        tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
        tvShowHelper.open();

        adapter = new TvShowAdapter();
        adapter.notifyDataSetChanged();

        progressBar = findViewById(R.id.progressBar);
        progressBar.bringToFront();

        tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        tvTitle.setText(tvShow.getName());
        tvOverview.setText(tvShow.getOverview());
        tvRelease.setText(tvShow.getFirst_air_date());
        tvVote.setText(tvShow.getVote_average());

        String url = "https://image.tmdb.org/t/p/w500" + tvShow.getBackdrop_path();
        Glide.with(DetailTvShowFavoriteActivity.this)
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
                .into(imgPoster);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = tvShowHelper.deleteTvShow(tvShow.getId());
                if (result > 0) {
                    Intent intent = new Intent (DetailTvShowFavoriteActivity.this, MainActivity.class);
                    intent.putExtra(EXTRA_POSITION, position);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, REQUEST_UPDATE);
                    setResult(RESULT_DELETE);
                    Toast.makeText(DetailTvShowFavoriteActivity.this, "Berhasil menghapus data", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DetailTvShowFavoriteActivity.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
