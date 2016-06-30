package it.tangodev.popularmoviesapp.utils;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.tangodev.popularmoviesapp.database.MovieContract;
import it.tangodev.popularmoviesapp.models.Movie;

public class DbConvertUtils {

    public static ContentValues getContentValuesFromMovie(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.FavouriteEntry.COLUMN_MOVIE_ID, movie.getId());
        contentValues.put(MovieContract.FavouriteEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MovieContract.FavouriteEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate().getTime());
        contentValues.put(MovieContract.FavouriteEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(MovieContract.FavouriteEntry.COLUMN_VOTE_AVG, movie.getVoteAvg());
        contentValues.put(MovieContract.FavouriteEntry.COLUMN_POSTER_URL, movie.getPosterUrl());

        return contentValues;
    }

    public static List<Movie> getMovieListFromCursor(Cursor cursor) {
        List<Movie> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            result.add(getMovieFromCurrentCursorRow(cursor));
        }
        return result;
    }

    public static Movie getMovieFromCurrentCursorRow(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getInt(cursor.getColumnIndex(MovieContract.FavouriteEntry.COLUMN_MOVIE_ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.FavouriteEntry.COLUMN_TITLE)));
        movie.setReleaseDate(new Date(cursor.getLong(cursor.getColumnIndex(MovieContract.FavouriteEntry.COLUMN_RELEASE_DATE))));
        movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.FavouriteEntry.COLUMN_OVERVIEW)));
        movie.setVoteAvg(cursor.getFloat(cursor.getColumnIndex(MovieContract.FavouriteEntry.COLUMN_VOTE_AVG)));
        movie.setPosterUrl(cursor.getString(cursor.getColumnIndex(MovieContract.FavouriteEntry.COLUMN_POSTER_URL)));

        return movie;
    }
}
