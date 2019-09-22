package com.ilhamrofiqi.submissionfinal.Movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ilhamrofiqi.submissionfinal.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> mData = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    void setData(ArrayList<Movie> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movies, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.tvTitle.setText(mData.get(i).getTitle());
        movieViewHolder.tvRelease_date.setText(mData.get(i).getRelease_date());
        movieViewHolder.tvVote_average.setText(mData.get(i).getVote_average());

        String url = "https://image.tmdb.org/t/p/w500" + mData.get(i).getPoster_path();
        Glide.with(movieViewHolder.itemView.getContext())
                .load(url)
//                .apply(new RequestOptions().override(50,75))
                .into(movieViewHolder.imgPoster_path);

        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(mData.get(movieViewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvRelease_date, tvVote_average;
        ImageView imgPoster_path;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_movies_title);
            tvRelease_date = itemView.findViewById(R.id.tv_movies_release);
            tvVote_average = itemView.findViewById(R.id.tv_movies_vote);
            imgPoster_path = itemView.findViewById(R.id.img_movies_poster);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie data);
    }
}
