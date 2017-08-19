package com.arfaststudio.prototype.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDBHelper extends SQLiteOpenHelper{
    private static final String TAG = "MovieDBHelper";
    private static final String DATABASE_NAME = "movielist";
    private static final int DATABASEVERSION = 3;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIELIST_TABLE = "CREATE TABLE " + MovieContract.MovielistEntry.TABLE_NAME + " (" +
                MovieContract.MovielistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovielistEntry.COLUMN_JUDUL + " TEXT, " +
                MovieContract.MovielistEntry.COLUMN_POSTER + " TEXT, " +
                MovieContract.MovielistEntry.COLUMN_RATING + " TEXT, " +
                MovieContract.MovielistEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                MovieContract.MovielistEntry.COLUMN_OVERVIEW + " TEXT, " +
                "UNIQUE (" + MovieContract.MovielistEntry.COLUMN_JUDUL + ") ON CONFLICT REPLACE);";
        Log.d(TAG, "onCreate: "+SQL_CREATE_MOVIELIST_TABLE);
        db.execSQL(SQL_CREATE_MOVIELIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovielistEntry.TABLE_NAME);
        onCreate(db);
    }
}