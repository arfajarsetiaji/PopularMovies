package com.arfajarsetiaji.android.popularmovies.network;

import android.net.Uri;

/**
 * Created by Ar Fajar Setiaji on 29-Aug-17.
 */

/**
 * Helper class untuk mempermudah saat berhubungan dengan network.
 */

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
    private static final String mParamApikeyValue = "bccd5b3bc9c3658bab941eeb0e1be99c";
    private static final String mParamLanguageKey = "language";
    private static final String mParamLanguageDefaultValue = "en-US";
    private static final String mParamPageKey = "page";
    private static final String mParamPageDefaultValue = "1";

    /**
     * Fungsi untuk membuat url JsonObject ImageUrlPrefix & Genre Id.
     * */

    public static String getImageUrlPrefix() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mUriScheme)
                .authority(mImageAuthority)
                .appendPath(mPathImage0)
                .appendPath(mPathImage1)
                .appendPath(mPathImageSize);
        return builder.build().toString();
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
        return builder.build().toString();
    }

    /**
     * Fungsi untuk membuat url JsonObject Most Popular Movie & Top Rated Movie.
     * Overload berdasarkan input paramLanguageValue & paramPageValue untuk kustomisasi
     * (Pengembangan lebih lanjut).
     * */

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
        return builder.build().toString();
    }

    public static String getMostPopularMovieJsonObjectUrl(String paramPageValue) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mUriScheme)
                .authority(mApiAuthority)
                .appendPath(mAuthVersion)
                .appendPath(mPathMovie)
                .appendPath(mPathMostPopular)
                .appendQueryParameter(mParamApikeyKey, mParamApikeyValue)
                .appendQueryParameter(mParamLanguageKey, mParamLanguageDefaultValue)
                .appendQueryParameter(mParamPageKey, paramPageValue);
        return builder.build().toString();
    }

    public static String getMostPopularMovieJsonObjectUrl(String paramLanguageValue, String paramPageValue) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mUriScheme)
                .authority(mApiAuthority)
                .appendPath(mAuthVersion)
                .appendPath(mPathMovie)
                .appendPath(mPathMostPopular)
                .appendQueryParameter(mParamApikeyKey, mParamApikeyValue)
                .appendQueryParameter(mParamLanguageKey, paramLanguageValue)
                .appendQueryParameter(mParamPageKey, paramPageValue);
        return builder.build().toString();
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
        return builder.build().toString();
    }

    public static String getTopRatedMovieJsonObjectUrl(String paramPageValue) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mUriScheme)
                .authority(mApiAuthority)
                .appendPath(mAuthVersion)
                .appendPath(mPathMovie)
                .appendPath(mPathTopRated)
                .appendQueryParameter(mParamApikeyKey, mParamApikeyValue)
                .appendQueryParameter(mParamLanguageKey, mParamLanguageDefaultValue)
                .appendQueryParameter(mParamPageKey, paramPageValue);
        return builder.build().toString();
    }

    public static String getTopRatedMovieJsonObjectUrl(String paramLanguageValue, String paramPageValue) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mUriScheme)
                .authority(mApiAuthority)
                .appendPath(mAuthVersion)
                .appendPath(mPathMovie)
                .appendPath(mPathTopRated)
                .appendQueryParameter(mParamApikeyKey, mParamApikeyValue)
                .appendQueryParameter(mParamLanguageKey, paramLanguageValue)
                .appendQueryParameter(mParamPageKey, paramPageValue);
        return builder.build().toString();
    }

    /**
     * Fungsi untuk membuat url JsonObject Movie Videos & Movie Reviews.
     * */

    public static String getMovieVideosJsonObjectUrl(String movieId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mUriScheme)
                .authority(mApiAuthority)
                .appendPath(mAuthVersion)
                .appendPath(mPathMovie)
                .appendPath(movieId)
                .appendPath(mPathVideos)
                .appendQueryParameter(mParamApikeyKey, mParamApikeyValue);
        return builder.build().toString();
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
        return builder.build().toString();
    }
}
