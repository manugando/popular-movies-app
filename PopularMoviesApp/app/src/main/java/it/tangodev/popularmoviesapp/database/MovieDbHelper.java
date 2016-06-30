package it.tangodev.popularmoviesapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVOURITE_TABLE = "CREATE TABLE " + MovieContract.FavouriteEntry.TABLE_NAME + " (" +
                MovieContract.FavouriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieContract.FavouriteEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieContract.FavouriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieContract.FavouriteEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL, " +
                MovieContract.FavouriteEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.FavouriteEntry.COLUMN_VOTE_AVG + " REAL NOT NULL, " +
                MovieContract.FavouriteEntry.COLUMN_POSTER_URL + " TEXT NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_FAVOURITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
