package it.tangodev.popularmoviesapp.asynctasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.List;

import it.tangodev.popularmoviesapp.database.MovieContract;
import it.tangodev.popularmoviesapp.models.Movie;
import it.tangodev.popularmoviesapp.utils.DbConvertUtils;

public class FavouriteMovieListAsyncTask extends AsyncTask<Void, Void, List<Movie>> {
    private Context context;

    public FavouriteMovieListAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Movie> doInBackground(Void... params) {
        Cursor cursor = context.getContentResolver().query(
                MovieContract.FavouriteEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        try {
            return DbConvertUtils.getMovieListFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
}
