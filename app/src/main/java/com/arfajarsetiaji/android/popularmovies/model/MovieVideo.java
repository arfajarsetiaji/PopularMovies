package com.arfajarsetiaji.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class MovieVideo implements Parcelable{
    private static final String TAG = "MovieVideo";
    public static final Creator<MovieVideo> CREATOR = new Creator<MovieVideo>() {
        @Override
        public MovieVideo createFromParcel(Parcel in) {
            Log.d(TAG, "createFromParcel: Called.");
            return new MovieVideo(in);
        }

        @Override
        public MovieVideo[] newArray(int size) {
            Log.d(TAG, "newArray: Called.");
            return new MovieVideo[size];
        }
    };
    private String id;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String url;
    private String size;
    private String type;

    public MovieVideo() {
        Log.d(TAG, "MovieVideo: Called.");
    }

    protected MovieVideo(Parcel in) {
        id = in.readString();
        iso_639_1 = in.readString();
        iso_3166_1 = in.readString();
        key = in.readString();
        name = in.readString();
        url = in.readString();
        size = in.readString();
        type = in.readString();
        Log.d(TAG, "MovieVideo: Called.");
    }

    public String getId() {
        Log.d(TAG, "getId: " + id);
        return id;
    }

    public void setId(String id) {
        this.id = id;
        Log.d(TAG, "setId: " + id);
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
        Log.d(TAG, "setIso_639_1: " + iso_639_1);
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
        Log.d(TAG, "setIso_3166_1: " + iso_3166_1);
    }

    public String getKey() {
        Log.d(TAG, "getKey: " + key);
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        Log.d(TAG, "setKey: " + key);
    }

    public String getName() {
        Log.d(TAG, "getName: " + name);
        return name;
    }

    public void setName(String name) {
        this.name = name;
        Log.d(TAG, "setName: " + name);
    }

    public String getUrl() {
        Log.d(TAG, "getUrl: " + url);
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        Log.d(TAG, "setUrl: " + url);
    }

    public void setSize(String size) {
        this.size = size;
        Log.d(TAG, "setSize: " + size);
    }

    public String getType() {
        Log.d(TAG, "getType: " + type);
        return type;
    }

    public void setType(String type) {
        this.type = type;
        Log.d(TAG, "setType: " + type);
    }

    @Override
    public int describeContents() {
        Log.d(TAG, "describeContents: Called.");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(iso_639_1);
        dest.writeString(iso_3166_1);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(size);
        dest.writeString(type);
        Log.d(TAG, "writeToParcel: Called.");
    }
}
