package com.example.labweek2_task2.provider;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MovieTable")

public class Movie implements Parcelable {
    public static final String TABLE_NAME = "MovieTable";


    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "year")
    private int year;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "genre")
    private String genre;
    @ColumnInfo(name = "cost")
    private int cost;
    @ColumnInfo(name = "keywords")
    private String keywords;
    @ColumnInfo(name = "comments")
    private String comments;

    public Movie(String name, int year, String country, String genre, int cost, String keywords, String comments) {
        this.name = name;
        this.year = year;
        this.country = country;
        this.genre = genre;
        this.cost = cost;
        this.keywords = keywords;
        this.comments = comments;
    }


    protected Movie(Parcel in) {
        id = in.readInt();
        name = in.readString();
        year = in.readInt();
        country = in.readString();
        genre = in.readString();
        cost = in.readInt();
        keywords = in.readString();
        comments = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    //Setter for ID
    public void setId(int id) {
        this.id = id;
    }


    //Getters for all

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public String getGenre() {
        return genre;
    }

    public int getCost() {
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
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(year);
        parcel.writeString(country);
        parcel.writeString(genre);
        parcel.writeInt(cost);
        parcel.writeString(keywords);
        parcel.writeString(comments);
    }
}
