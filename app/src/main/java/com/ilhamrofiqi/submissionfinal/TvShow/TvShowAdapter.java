package com.ilhamrofiqi.submissionfinal.TvShow;

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

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowHolder> {

    private ArrayList<TvShow> mData = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    void setData(ArrayList<TvShow> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TvShowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_tv_show, viewGroup, false);
        return new TvShowHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowHolder tvShowHolder, int i) {
        tvShowHolder.tvName.setText(mData.get(i).getName());
        tvShowHolder.tvFirst_air_date.setText(mData.get(i).getFirst_air_date());
        tvShowHolder.tvVote_average.setText(mData.get(i).getVote_average());

        String url = "https://image.tmdb.org/t/p/w500" + mData.get(i).getBackdrop_path();
        Glide.with(tvShowHolder.itemView.getContext())
                .load(url)
                .into(tvShowHolder.imgBackdrop_path);

        tvShowHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(mData.get(tvShowHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TvShowHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvFirst_air_date, tvVote_average;
        ImageView imgBackdrop_path;

        TvShowHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_tv_show_name);
            tvFirst_air_date = itemView.findViewById(R.id.tv_tv_show_first_air_date);
            tvVote_average = itemView.findViewById(R.id.tv_tv_show_vote);
            imgBackdrop_path = itemView.findViewById(R.id.img_tv_show_backdrop_path);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TvShow data);
    }

    public class TvShowViewHolder {
    }
}
