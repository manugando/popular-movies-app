package it.tangodev.popularmoviesapp.asynctasks;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import it.tangodev.popularmoviesapp.database.MovieContract;
import it.tangodev.popularmoviesapp.models.Movie;
import it.tangodev.popularmoviesapp.utils.DbConvertUtils;

public class AddFavouriteMovieAsyncTask extends AsyncTask<Movie, Void, Boolean> {
    private Context context;

    public AddFavouriteMovieAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Movie... params) {
        Movie movie = params[0];
        try {
            ContentValues contentValues = DbConvertUtils.getContentValuesFromMovie(movie);
            context.getContentResolver().insert(MovieContract.FavouriteEntry.CONTENT_URI, contentValues);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
