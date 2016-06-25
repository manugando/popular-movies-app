package it.tangodev.popularmoviesapp.asynctasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;
import java.util.List;

import it.tangodev.popularmoviesapp.BuildConfig;
import it.tangodev.popularmoviesapp.models.Movie;
import it.tangodev.popularmoviesapp.utils.Constants;
import it.tangodev.popularmoviesapp.utils.HttpUtils;
import it.tangodev.popularmoviesapp.utils.JsonConvertUtils;

public class MovieListAsyncTask extends AsyncTask<Void, Void, List<Movie>> {
    public static final String LOG_TAG = MovieListAsyncTask.class.getSimpleName();

    @Override
    protected List<Movie> doInBackground(Void... params) {
        Uri builtUri = Uri.parse(Constants.MOVIE_LIST_URL).buildUpon()
                .appendQueryParameter(Constants.PARAM_API_KEY, BuildConfig.MOVIE_DB_ORG_KEY)
                .build();

        try {
            String result = HttpUtils.executeHttpRequest(new URL(builtUri.toString()));
            return JsonConvertUtils.getMovieListFromJsonString(result);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error: ", e);
        }

        return null;
    }
}
