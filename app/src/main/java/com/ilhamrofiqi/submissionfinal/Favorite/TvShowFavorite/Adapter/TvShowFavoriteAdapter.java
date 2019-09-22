package com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.Adapter;

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
import com.ilhamrofiqi.submissionfinal.Favorite.TvShowFavorite.DetailTvShowFavoriteActivity;
import com.ilhamrofiqi.submissionfinal.R;
import com.ilhamrofiqi.submissionfinal.TvShow.TvShow;

import java.util.ArrayList;


public class TvShowFavoriteAdapter extends RecyclerView.Adapter<TvShowFavoriteAdapter.TvShowFavoriteViewHolder> {

    private ArrayList<TvShow> listTvShows = new ArrayList<>();
    private Activity activity;

    public TvShowFavoriteAdapter(Activity  activity) {
        this.activity = activity;
    }

    public ArrayList<TvShow> getListTvShows() {
        return listTvShows;
    }

    public void setListTvShows(ArrayList<TvShow> listTvShows) {

        if (listTvShows.size() > 0) {
            this.listTvShows.clear();
        }
        this.listTvShows.addAll(listTvShows);

        notifyDataSetChanged();
    }

//    menambahkan item RV
    public void addItem(TvShow tvShow) {
        this.listTvShows.add(tvShow);
        notifyItemInserted(listTvShows.size() - 1);
    }

//    memperbarui item RV
    public void updateItem(int position, TvShow tvShow) {
        this.listTvShows.set(position, tvShow);
        notifyItemChanged(position, tvShow);
    }

//    menghapus item RV
    public void removeItem(int position) {
        this.listTvShows.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listTvShows.size());
    }

    @NonNull
    @Override
    public TvShowFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_favorite_tv_show, viewGroup, false);
        return new TvShowFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowFavoriteViewHolder tvShowFavoriteViewHolder, int i) {
        tvShowFavoriteViewHolder.tvTitle.setText(listTvShows.get(i).getName());
        tvShowFavoriteViewHolder.tvRelease.setText(listTvShows.get(i).getFirst_air_date());
        tvShowFavoriteViewHolder.tvVote.setText(listTvShows.get(i).getVote_average());

        String url = "https://image.tmdb.org/t/p/w500" + listTvShows.get(i).getBackdrop_path();
        Glide.with(tvShowFavoriteViewHolder.itemView.getContext())
                .load(url)
                .into(tvShowFavoriteViewHolder.imgPosterTvShow);

        tvShowFavoriteViewHolder.itemView.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailTvShowFavoriteActivity.class);
                intent.putExtra(DetailTvShowFavoriteActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailTvShowFavoriteActivity.EXTRA_TVSHOW, listTvShows.get(position));
                activity.startActivityForResult(intent, DetailTvShowFavoriteActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listTvShows.size();
    }

    class TvShowFavoriteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvRelease, tvVote;
        final ImageView imgPosterTvShow;

        TvShowFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_favorite_tv_show_name);
            tvRelease = itemView.findViewById(R.id.tv_favorite_tv_show_first_air_date);
            tvVote = itemView.findViewById(R.id.tv_favorite_tv_show_vote);
            imgPosterTvShow = itemView.findViewById(R.id.img_favorite_tv_show_backdrop_path);
        }
    }
}
