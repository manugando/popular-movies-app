package it.tangodev.popularmoviesapp.asynctasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.net.URL;

import it.tangodev.popularmoviesapp.BuildConfig;
import it.tangodev.popularmoviesapp.models.MovieDetails;
import it.tangodev.popularmoviesapp.utils.Constants;
import it.tangodev.popularmoviesapp.utils.HttpUtils;
import it.tangodev.popularmoviesapp.utils.JsonConvertUtils;

public class MovieDetailAsyncTask extends AsyncTask<Integer, Void, MovieDetails> {
    public static final String LOG_TAG = MovieListAsyncTask.class.getSimpleName();

    @Override
    protected MovieDetails doInBackground(Integer... params) {
        int movieId = params[0];

        Uri builtUri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendPath(movieId + "")
                .appendQueryParameter(Constants.PARAM_API_KEY, BuildConfig.MOVIE_DB_ORG_KEY)
                .appendQueryParameter(Constants.PARAM_APPEND_TO_RESPONSE, "videos,reviews")
                .build();

        try {
            String result = HttpUtils.executeHttpRequest(new URL(builtUri.toString()));
            return JsonConvertUtils.getMovieDetailsFromJsonString(result);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error: ", e);
        }

        return null;
    }
}
