package com.example.labweek2_task2;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieEditText implements Parcelable {
    String title;
    String year;
    String country;
    String genre;
    String cost;
    String keywords;
    String comments;

    public MovieEditText(String title, String year, String country, String genre, String cost, String keywords, String comments) {
        this.title = title;
        this.year = year;
        this.country = country;
        this.genre = genre;
        this.cost = cost;
        this.keywords = keywords;
        this.comments = comments;
    }

    protected MovieEditText(Parcel in) {
        title = in.readString();
        year = in.readString();
        country = in.readString();
        genre = in.readString();
        cost = in.readString();
        keywords = in.readString();
        comments = in.readString();
    }

    public static final Creator<MovieEditText> CREATOR = new Creator<MovieEditText>() {
        @Override
        public MovieEditText createFromParcel(Parcel in) {
            return new MovieEditText(in);
        }

        @Override
        public MovieEditText[] newArray(int size) {
            return new MovieEditText[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public String getGenre() {
        return genre;
    }

    public String getCost() {
        return cost;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getComments() {
        return comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(year);
        parcel.writeString(country);
        parcel.writeString(genre);
        parcel.writeString(cost);
        parcel.writeString(keywords);
        parcel.writeString(comments);
    }
}
