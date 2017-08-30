package com.arfajarsetiaji.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by Ar Fajar Setiaji on 27-Aug-17.
 */

/**
 * Contract class berisi deskripsi struktur data pada SQLite database.
 */

public class MoviesContract {
    static final String AUTHORITY = "com.arfajarsetiaji.android.popularmovies";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    static final String PATH_TASKS = "movies";

    public static class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_GENRE_IDS = "genre_ids";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
    }

}
