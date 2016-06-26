package it.tangodev.popularmoviesapp.asynctasks;

import android.content.Intent;
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

public class MovieListAsyncTask extends AsyncTask<Integer, Void, List<Movie>> {
    public static final String LOG_TAG = MovieListAsyncTask.class.getSimpleName();
    public static final int MODE_POPULAR = 1;
    public static final int MODE_TOP_RATED = 2;

    @Override
    protected List<Movie> doInBackground(Integer... params) {
        int mode = params[0];
        int page = params[1];
        String baseUrl = mode == MODE_POPULAR ? Constants.POPULAR_MOVIE_LIST_URL : Constants.TOP_RATED_MOVIE_LIST_URL;
        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(Constants.PARAM_API_KEY, BuildConfig.MOVIE_DB_ORG_KEY)
                .appendQueryParameter(Constants.PARAM_PAGE, page + "")
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
