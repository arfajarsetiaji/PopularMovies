package com.arfajarsetiaji.android.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.arfajarsetiaji.android.popularmovies.model.Movie;

import java.util.LinkedList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class MoviesDBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASEVERSION = 1;

    @SuppressWarnings({"unused", "MismatchedReadAndWriteOfArray"})
    private static final String[] COLUMNS = {
            MoviesContract.MoviesEntry.COLUMN_MOVIE_ID,
            MoviesContract.MoviesEntry.COLUMN_ORIGINAL_LANGUAGE,
            MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE,
            MoviesContract.MoviesEntry.COLUMN_TITLE,
            MoviesContract.MoviesEntry.COLUMN_GENRE_IDS,
            MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE,
            MoviesContract.MoviesEntry.COLUMN_VOTE_COUNT,
            MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE,
            MoviesContract.MoviesEntry.COLUMN_POPULARITY,
            MoviesContract.MoviesEntry.COLUMN_OVERVIEW,
            MoviesContract.MoviesEntry.COLUMN_ADULT,
            MoviesContract.MoviesEntry.COLUMN_VIDEO,
            MoviesContract.MoviesEntry.COLUMN_POSTER_PATH,
            MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH
    };

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + MoviesContract.MoviesEntry.TABLE_NAME + " (" +
            MoviesContract.MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MoviesContract.MoviesEntry.COLUMN_MOVIE_ID + " TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE + " TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_TITLE + " TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_GENRE_IDS + " TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE + " TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_VOTE_COUNT + " TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE + " TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_POPULARITY + " TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_OVERVIEW + " TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_ADULT + " TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_VIDEO + " TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_POSTER_PATH + " TEXT, " +
            MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH + " TEXT, " +
            "UNIQUE (" + MoviesContract.MoviesEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_NAME;

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASEVERSION);
        Log.d(TAG, "MoviesDBHelper: Called.");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        Log.d(TAG, "onCreate: Called.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
        Log.d(TAG, "onUpgrade: Called.");
    }

    @SuppressWarnings("unused")
    public void insertMovie(Movie movie){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID, movie.getMovieId());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_GENRE_IDS, movie.getGenreIds());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_COUNT, movie.getVoteCount());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_POPULARITY, movie.getPopularity());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_ADULT, movie.getAdult());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_VIDEO, movie.getVideo());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
        sqLiteDatabase.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        Log.d(TAG, "insertMovie: Called.");
    }

    @SuppressWarnings("unused")
    public Movie getMovie(String movieId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                MoviesContract.MoviesEntry.TABLE_NAME,
                COLUMNS,
                " movie_id = ?",
                new String[] { String.valueOf(movieId) },
                null,
                null,
                null,
                null);
        Movie movie = new Movie();
        if (cursor != null) {
            cursor.moveToFirst();
            movie.setMovieId(cursor.getString(0));
            movie.setOriginalLanguage(cursor.getString(1));
            movie.setOriginalTitle(cursor.getString(2));
            movie.setTitle(cursor.getString(3));
            movie.setGenreIds(cursor.getString(4));
            movie.setReleaseDate(cursor.getString(5));
            movie.setVoteCount(cursor.getString(6));
            movie.setVoteAverage(cursor.getString(7));
            movie.setPopularity(cursor.getString(8));
            movie.setOverview(cursor.getString(9));
            movie.setAdult(cursor.getString(10));
            movie.setVideo(cursor.getString(11));
            movie.setPosterPath(cursor.getString(12));
            movie.setBackdropPath(cursor.getString(13));
            cursor.close();
        }
        Log.d(TAG, "getMovie: Called.");
        return movie;
    }

    @SuppressWarnings("unused")
    public List<Movie> getAllMovies() {
        List<Movie> movies = new LinkedList<>();
        String query = "SELECT * FROM " + MoviesContract.MoviesEntry.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToFirst();
            Movie movie = new Movie();
            movie.setMovieId(cursor.getString(1));
            movie.setOriginalLanguage(cursor.getString(2));
            movie.setOriginalTitle(cursor.getString(3));
            movie.setTitle(cursor.getString(4));
            movie.setGenreIds(cursor.getString(5));
            movie.setReleaseDate(cursor.getString(6));
            movie.setVoteCount(cursor.getString(7));
            movie.setVoteAverage(cursor.getString(8));
            movie.setPopularity(cursor.getString(9));
            movie.setOverview(cursor.getString(10));
            movie.setAdult(cursor.getString(11));
            movie.setVideo(cursor.getString(12));
            movie.setPosterPath(cursor.getString(13));
            movie.setBackdropPath(cursor.getString(14));
            movies.add(movie);
            if (i < cursor.getCount() - 1) {
                cursor.moveToNext();
            }
        }
        cursor.close();
        Log.d(TAG, "getAllMovies: Called.");
        return movies;
    }
}