package com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.Adaper;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ilhamrofiqi.submissionfinal.CustomOnItemClickListener;
import com.ilhamrofiqi.submissionfinal.Favorite.MovieFavorite.DetailMovieFavoriteActivity;
import com.ilhamrofiqi.submissionfinal.Movies.Movie;
import com.ilhamrofiqi.submissionfinal.R;

import java.util.ArrayList;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavoriteViewHolder> {
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private Activity activity;

    public MovieFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Movie> getListMovies() {
        return listMovies;
    }

    public void setListMovies(ArrayList<Movie> listMovies) {
        if (listMovies.size() > 0) {
            this.listMovies.clear();
        }
        this.listMovies.addAll(listMovies);
        notifyDataSetChanged();
    }

    public void addItem(Movie movie) {
        this.listMovies.add(movie);
        notifyItemInserted(listMovies.size() - 1);
    }

    public void removeItem(int position) {
        this.listMovies.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listMovies.size());
    }

    @NonNull
    @Override
    public MovieFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_favorite_movie, viewGroup, false);
        return new MovieFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieFavoriteViewHolder movieFavoriteViewHolder, int i) {
        movieFavoriteViewHolder.tvTitle.setText(listMovies.get(i).getTitle());
        movieFavoriteViewHolder.tvRelease.setText(listMovies.get(i).getRelease_date());
        movieFavoriteViewHolder.tvVote.setText(listMovies.get(i).getVote_average());

        String url = "https://image.tmdb.org/t/p/w500" + listMovies.get(i).getPoster_path();
        Glide.with(movieFavoriteViewHolder.itemView.getContext())
                .load(url)
                .into(movieFavoriteViewHolder.imgPoster);

        movieFavoriteViewHolder.itemView.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailMovieFavoriteActivity.class);
                intent.putExtra(DetailMovieFavoriteActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailMovieFavoriteActivity.EXTRA_MOVIE, listMovies.get(position));
                activity.startActivityForResult(intent, DetailMovieFavoriteActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class MovieFavoriteViewHolder extends RecyclerView.ViewHolder {
       final TextView tvTitle, tvRelease, tvVote;
       final ImageView imgPoster;

        MovieFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_favorite_movies_title);
            tvRelease = itemView.findViewById(R.id.tv_favorite_movies_release);
            tvVote = itemView.findViewById(R.id.tv_favorite_movies_vote);
            imgPoster = itemView.findViewById(R.id.img_favorite_movies_poster);
        }
    }
}
