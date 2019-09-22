package com.ilhamrofiqi.submissionfinal.TvShow;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvShow implements Parcelable {
    private int id;
    private String backdrop_path;
    private String name;
    private String overview;
    private String first_air_date;
    private String vote_average;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    TvShow(JSONObject object) {
        try {
            int id = object.getInt("id");
            String name = object.getString("name");
            String backdrop_path = object.getString("backdrop_path");
            String overview = object.getString("overview");
            String first_air_date = object.getString("first_air_date");
            String vote_average = object.getString("vote_average");

            this.id = id;
            this.name = name;
            this.backdrop_path = backdrop_path;
            this.overview = overview;
            this.first_air_date = first_air_date;
            this.vote_average = vote_average;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.name);
        dest.writeString(this.overview);
        dest.writeString(this.first_air_date);
        dest.writeString(this.vote_average);
    }

    protected TvShow(Parcel in) {
        this.id = in.readInt();
        this.backdrop_path = in.readString();
        this.name = in.readString();
        this.overview = in.readString();
        this.first_air_date = in.readString();
        this.vote_average = in.readString();
    }

    public TvShow() {

    }

    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}