package com.rara.moviecatalog.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String judul, sinopsis;
    private int poster;

    protected Movie(Parcel in) {
        this.judul = in.readString();
        this.sinopsis = in.readString();
        this.poster = in.readInt();
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.judul);
        dest.writeString(this.sinopsis);
        dest.writeInt(this.poster);
    }

    public Movie() {
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}