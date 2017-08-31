package com.arfajarsetiaji.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Ar Fajar Setiaji on 27-Aug-17.
 */

/**
 * Struktur POJO Movie == JsonObject dari Popular Movies & Top Rated Movies.
 * Implements parcelable agar object movie dapat dikirim dengan bundle menggunakan intent.
 */

public class Movie implements Parcelable, Comparable<Movie>{

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

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(String genreIds) {
        this.genreIds = genreIds;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPosterPath() {
        if (posterPath.contains(movieImageUrlPrefix)) {
            return posterPath;
        }
        else return  movieImageUrlPrefix + posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        if (backdropPath.contains(movieImageUrlPrefix)) {
            return backdropPath;
        }
        else return movieImageUrlPrefix + backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setMovieImageUrlPrefix(String movieImageUrlPrefix) {
        this.movieImageUrlPrefix = movieImageUrlPrefix;
    }

    @Override
    public int describeContents() {
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
    }

    @Override
    public int compareTo(@NonNull Movie m) {
        if (Integer.parseInt(movieId) > Integer.parseInt(m.movieId)) {
            return 1;
        } else if (Integer.parseInt(movieId) < Integer.parseInt(m.movieId)) {
            return -1;
        } else {
            return 0;
        }
    }
}
