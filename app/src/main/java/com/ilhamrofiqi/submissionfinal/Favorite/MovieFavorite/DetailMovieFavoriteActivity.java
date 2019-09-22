package com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite;

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
import com.ilhamrofiqi.submissionfinal.MainActivity;
import com.ilhamrofiqi.submissionfinal.Movies.Movie;
import com.ilhamrofiqi.submissionfinal.Movies.MovieAdapter;
import com.ilhamrofiqi.submissionfinal.R;

public class DetailMovieFavoriteActivity extends AppCompatActivity {

    TextView tvTitle, tvOverview, tvRelease, tvVote;
    ImageView imgPoster;
    MovieAdapter adapter;
    Button btnDelete;

    private ProgressBar progressBar;
    private MovieHelper movieHelper;
    private Movie movie;
    private int position;

    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 300;

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_POSITION = "extra_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie_favorite);

        tvTitle = findViewById(R.id.tv_movies_title);
        tvOverview = findViewById(R.id.tv_movies_overview);
        tvRelease = findViewById(R.id.tv_movies_release);
        tvVote = findViewById(R.id.tv_movies_vote);
        imgPoster = findViewById(R.id.img_movies_poster);
        btnDelete = findViewById(R.id.btn_delete);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();

        progressBar = findViewById(R.id.progressBar);
        progressBar.bringToFront();

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvRelease.setText(movie.getRelease_date());
        tvVote.setText(movie.getVote_average());

        String url = "https://image.tmdb.org/t/p/w500" + movie.getPoster_path();
        Glide.with(DetailMovieFavoriteActivity.this)
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
                long result = movieHelper.deleteMovie(movie.getId());
                if (result > 0) {
                    Intent intent = new Intent(DetailMovieFavoriteActivity.this, MainActivity.class);
                    intent.putExtra(EXTRA_POSITION, position);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, REQUEST_UPDATE);
                    setResult(RESULT_DELETE);
                    Toast.makeText(DetailMovieFavoriteActivity.this, "Berhasil menghapus data", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DetailMovieFavoriteActivity.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
