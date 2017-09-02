package com.arfajarsetiaji.android.popularmovies.network;

import android.net.Uri;
import android.util.Log;

public class NetworkHelper {
    private static final String TAG = "NetworkHelper";

    private static final String mUriScheme = "https";
    private static final String mApiAuthority = "api.themoviedb.org";
    private static final String mImageAuthority = "image.tmdb.org";
    private static final String mAuthVersion = "3";

    private static final String mPathMovie = "movie";
    private static final String mPathGenre = "genre";
    private static final String mPathVideos = "videos";
    private static final String mPathReviews = "reviews";
    private static final String mPathMostPopular = "popular";
    private static final String mPathTopRated = "top_rated";
    private static final String mPathList = "list";
    private static final String mPathImage0 = "t";
    private static final String mPathImage1 = "p";
    private static final String mPathImageSize = "w500";

    private static final String mParamApikeyKey = "api_key";
    private static final String mParamApikeyValue = "INSERT_YOUR_OWN_API_KEY_HERE";
    private static final String mParamLanguageKey = "language";
    private static final String mParamLanguageDefaultValue = "en-US";
    private static final String mParamPageKey = "page";
    private static final String mParamPageDefaultValue = "1";

    public static String getImageUrlPrefix() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mUriScheme)
                .authority(mImageAuthority)
                .appendPath(mPathImage0)
                .appendPath(mPathImage1)
                .appendPath(mPathImageSize);
        String imageUrlPrefix = builder.build().toString();
        Log.d(TAG, "getImageUrlPrefix: " + imageUrlPrefix);
        return imageUrlPrefix;
    }

    public static String getGenreIdJsonObjectUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mUriScheme)
                .authority(mApiAuthority)
                .appendPath(mAuthVersion)
                .appendPath(mPathGenre)
                .appendPath(mPathList)
                .appendQueryParameter(mParamApikeyKey, mParamApikeyValue)
                .appendQueryParameter(mParamLanguageKey, mParamLanguageDefaultValue)
                .appendQueryParameter(mParamPageKey, mParamPageDefaultValue);
        String genreIdJsonObjectUrl = builder.build().toString();
        Log.d(TAG, "getGenreIdJsonObjectUrl: " + genreIdJsonObjectUrl);
        return genreIdJsonObjectUrl;
    }

    public static String getMostPopularMovieJsonObjectUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mUriScheme)
                .authority(mApiAuthority)
                .appendPath(mAuthVersion)
                .appendPath(mPathMovie)
                .appendPath(mPathMostPopular)
                .appendQueryParameter(mParamApikeyKey, mParamApikeyValue)
                .appendQueryParameter(mParamLanguageKey, mParamLanguageDefaultValue)
                .appendQueryParameter(mParamPageKey, mParamPageDefaultValue);
        String mostPopularMovieJsonObjectUrl = builder.build().toString();
        Log.d(TAG, "getMostPopularMovieJsonObjectUrl: " + mostPopularMovieJsonObjectUrl);
        return mostPopularMovieJsonObjectUrl;
    }

    public static String getTopRatedMovieJsonObjectUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mUriScheme)
                .authority(mApiAuthority)
                .appendPath(mAuthVersion)
                .appendPath(mPathMovie)
                .appendPath(mPathTopRated)
                .appendQueryParameter(mParamApikeyKey, mParamApikeyValue)
                .appendQueryParameter(mParamLanguageKey, mParamLanguageDefaultValue)
                .appendQueryParameter(mParamPageKey, mParamPageDefaultValue);
        String topRatedMovieJsonObjectUrl = builder.build().toString();
        Log.d(TAG, "getTopRatedMovieJsonObjectUrl: " +topRatedMovieJsonObjectUrl);
        return topRatedMovieJsonObjectUrl;
    }

    public static String getMovieVideosJsonObjectUrl(String movieId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mUriScheme)
                .authority(mApiAuthority)
                .appendPath(mAuthVersion)
                .appendPath(mPathMovie)
                .appendPath(movieId)
                .appendPath(mPathVideos)
                .appendQueryParameter(mParamApikeyKey, mParamApikeyValue);
        String movieVideosJsonObjectUrl = builder.build().toString();
        Log.d(TAG, "getMovieVideosJsonObjectUrl: " + movieVideosJsonObjectUrl);
        return movieVideosJsonObjectUrl;
    }

    public static String getMovieReviewsJsonObjectUrl(String movieId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mUriScheme)
                .authority(mApiAuthority)
                .appendPath(mAuthVersion)
                .appendPath(mPathMovie)
                .appendPath(movieId)
                .appendPath(mPathReviews)
                .appendQueryParameter(mParamApikeyKey, mParamApikeyValue);
        String movieReviewsJsonObjectUrl = builder.build().toString();
        Log.d(TAG, "getMovieReviewsJsonObjectUrl: " + movieReviewsJsonObjectUrl);
        return movieReviewsJsonObjectUrl;
    }
}
