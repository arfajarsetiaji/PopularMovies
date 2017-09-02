package com.arfajarsetiaji.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

public class Movie implements Parcelable, Comparable<Movie>{
    private static final String TAG = "Movie";
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            Log.d(TAG, "createFromParcel: Called.");
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            Log.d(TAG, "newArray: Called.");
            return new Movie[size];
        }
    };
    private String movieId;
    private String originalLanguage;
    private String originalTitle;
    private String title;
    private String genreIds = null;
    private String releaseDate;
    private String voteCount;
    private String voteAverage;
    private String popularity;
    private String overview;
    private String adult;
    private String video;
    private String posterPath;
    private String backdropPath;
    private String movieImageUrlPrefix;

    public Movie() {
        Log.d(TAG, "Movie: Called.");
    }

    protected Movie(Parcel in) {
        movieId = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        title = in.readString();
        genreIds = in.readString();
        releaseDate = in.readString();
        voteCount = in.readString();
        voteAverage = in.readString();
        popularity = in.readString();
        overview = in.readString();
        adult = in.readString();
        video = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        movieImageUrlPrefix = in.readString();
        Log.d(TAG, "Movie: Called.");
    }

    public String getMovieId() {
        Log.d(TAG, "getMovieId: " +movieId);
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
        Log.d(TAG, "setMovieId: " + movieId);
    }

    public String getOriginalLanguage() {
        Log.d(TAG, "getOriginalLanguage: " + originalLanguage);
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
        Log.d(TAG, "setOriginalLanguage: " + originalLanguage);
    }

    public String getOriginalTitle() {
        Log.d(TAG, "getOriginalTitle: " + originalTitle);
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        Log.d(TAG, "setOriginalTitle: " + originalTitle);
    }

    public String getTitle() {
        Log.d(TAG, "getTitle: " + title);
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        Log.d(TAG, "setTitle: " + title);
    }

    public String getGenreIds() {
        Log.d(TAG, "getGenreIds: " + genreIds);
        return genreIds;
    }

    public void setGenreIds(String genreIds) {
        this.genreIds = genreIds;
        Log.d(TAG, "setGenreIds: " + genreIds);
    }

    public String getReleaseDate() {
        Log.d(TAG, "getReleaseDate: " + releaseDate);
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        Log.d(TAG, "setReleaseDate: " + releaseDate);
    }

    public String getVoteCount() {
        Log.d(TAG, "getVoteCount: " + voteCount);
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
        Log.d(TAG, "setVoteCount: " + voteCount);
    }

    public String getVoteAverage() {
        Log.d(TAG, "getVoteAverage: " + voteAverage);
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
        Log.d(TAG, "setVoteAverage: " + voteAverage);
    }

    public String getPopularity() {
        Log.d(TAG, "getPopularity: " + popularity);
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
        Log.d(TAG, "setPopularity: " + popularity);
    }

    public String getOverview() {
        Log.d(TAG, "getOverview: " + overview);
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
        Log.d(TAG, "setOverview: " + overview);
    }

    public String getAdult() {
        Log.d(TAG, "getAdult: " + adult);
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
        Log.d(TAG, "setAdult: " + adult);
    }

    public String getVideo() {
        Log.d(TAG, "getVideo: " + video);
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
        Log.d(TAG, "setVideo: " + video);
    }

    public String getPosterPath() {
        if (posterPath.contains(movieImageUrlPrefix)) {
            Log.d(TAG, "getPosterPath: " + posterPath);
            return posterPath;
        }
        else
            Log.d(TAG, "getPosterPath: " + movieImageUrlPrefix + posterPath);
            return  movieImageUrlPrefix + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
        Log.d(TAG, "setPosterPath: " + posterPath);
    }

    public String getBackdropPath() {
        if (backdropPath.contains(movieImageUrlPrefix)) {
            Log.d(TAG, "getBackdropPath: " + backdropPath);
            return backdropPath;
        }
        else
            Log.d(TAG, "getBackdropPath: " + movieImageUrlPrefix + backdropPath);
            return movieImageUrlPrefix + backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        Log.d(TAG, "setBackdropPath: " + backdropPath);
    }

    public void setMovieImageUrlPrefix(String movieImageUrlPrefix) {
        this.movieImageUrlPrefix = movieImageUrlPrefix;
        Log.d(TAG, "setMovieImageUrlPrefix: " + movieImageUrlPrefix);
    }

    @Override
    public int describeContents() {
        Log.d(TAG, "describeContents: Called.");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieId);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(title);
        dest.writeString(genreIds);
        dest.writeString(releaseDate);
        dest.writeString(voteCount);
        dest.writeString(voteAverage);
        dest.writeString(popularity);
        dest.writeString(overview);
        dest.writeString(adult);
        dest.writeString(video);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(movieImageUrlPrefix);
        Log.d(TAG, "writeToParcel: Called.");
    }

    @Override
    public int compareTo(@NonNull Movie m) {
        if (Integer.parseInt(movieId) > Integer.parseInt(m.movieId)) {
            Log.d(TAG, "compareTo: " + 1);
            return 1;
        } else if (Integer.parseInt(movieId) < Integer.parseInt(m.movieId)) {
            Log.d(TAG, "compareTo: " + -1);
            return -1;
        } else {
            Log.d(TAG, "compareTo: " + 0);
            return 0;
        }
    }
}
