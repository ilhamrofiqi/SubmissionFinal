package com.ilhamrofiqi.submissionfinal.Movies;

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
import com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.Database.MovieHelper;
import com.ilhamrofiqi.submissionfinal.R;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_POSITION = "extra_position";

    private boolean isEdit = false;
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;

    private Movie movies;
    private int position;

    MovieAdapter adapter;

    private MovieHelper movieHelper;

    TextView tvTitle, tvOverview, tvRelease, tvVote;
    ImageView imgPoster;
    Button btnFavorite;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tvTitle = findViewById(R.id.tv_movies_title);
        tvOverview = findViewById(R.id.tv_movies_overview);
        tvRelease = findViewById(R.id.tv_movies_release);
        tvVote = findViewById(R.id.tv_movies_vote);
        imgPoster = findViewById(R.id.img_movies_poster);
        btnFavorite = findViewById(R.id.btn_favorite);

        progressBar = findViewById(R.id.progressBar);
        progressBar.bringToFront();

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();

        movies = getIntent().getParcelableExtra(EXTRA_MOVIE);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        if (position > 0) {
            isEdit = true;
        }

        tvTitle.setText(movies.getTitle());
        tvOverview.setText(movies.getOverview());
        tvRelease.setText(movies.getRelease_date());
        tvVote.setText(movies.getVote_average());

        String url = "https://image.tmdb.org/t/p/w500" + movies.getPoster_path();
        Glide.with(DetailMovieActivity.this)
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

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = tvTitle.getText().toString().trim();
                String vote = tvVote.getText().toString().trim();
                String overview = tvOverview.getText().toString().trim();
                String release = tvRelease.getText().toString().trim();

                movies.setTitle(title);
                movies.setOverview(overview);
                movies.setVote_average(vote);
                movies.setRelease_date(release);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_MOVIE, movies);
                intent.putExtra(EXTRA_POSITION, position);
                if (!isEdit) {
                    long result = movieHelper.insertMovie(movies);
                    if (result > 0) {
                        movies.setId((int) result);
                        setResult(RESULT_ADD, intent);
                        Toast.makeText(DetailMovieActivity.this, "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();
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
