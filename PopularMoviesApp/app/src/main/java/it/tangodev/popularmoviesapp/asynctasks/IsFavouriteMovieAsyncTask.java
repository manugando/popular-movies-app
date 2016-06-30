package it.tangodev.popularmoviesapp.asynctasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.renderscript.BaseObj;

import it.tangodev.popularmoviesapp.database.MovieContract;
import it.tangodev.popularmoviesapp.utils.DbConvertUtils;

public class IsFavouriteMovieAsyncTask extends AsyncTask<Integer, Void, Boolean> {
    private Context context;

    public IsFavouriteMovieAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Integer... params) {
        long idMovie = params[0];
        Cursor cursor = context.getContentResolver().query(
                MovieContract.FavouriteEntry.buildFavouriteUri(idMovie),
                null,
                null,
                null,
                null
        );

        try {
            return cursor.moveToFirst();
        } finally {
          cursor.close();
        }
    }
}
