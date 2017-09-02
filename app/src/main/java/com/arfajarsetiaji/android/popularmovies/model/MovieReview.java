package com.arfajarsetiaji.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class MovieReview implements Parcelable{
    private static final String TAG = "MovieReview";
    public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel in) {
            Log.d(TAG, "createFromParcel: Called.");
            return new MovieReview(in);
        }

        @Override
        public MovieReview[] newArray(int size) {
            Log.d(TAG, "newArray: Called.");
            return new MovieReview[size];
        }
    };
    private String id;
    private String author;
    private String content;
    private String url;

    public MovieReview() {
        Log.d(TAG, "MovieReview: Called.");
    }

    protected MovieReview(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
        url = in.readString();
        Log.d(TAG, "MovieReview: Called.");
    }

    public String getId() {
        Log.d(TAG, "getId: " + id);
        return id;
    }

    public void setId(String id) {
        this.id = id;
        Log.d(TAG, "setId: " + id);
    }

    public void setAuthor(String author) {
        this.author = author;
        Log.d(TAG, "setAuthor: " + author);
    }

    public String getContent() {
        Log.d(TAG, "getContent: " + content);
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        Log.d(TAG, "setContent: " + content);
    }

    public void setUrl(String url) {
        this.url = url;
        Log.d(TAG, "setUrl: " + url);
    }

    @Override
    public int describeContents() {
        Log.d(TAG, "describeContents: Called.");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(url);
        Log.d(TAG, "writeToParcel: Called.");
    }
}
