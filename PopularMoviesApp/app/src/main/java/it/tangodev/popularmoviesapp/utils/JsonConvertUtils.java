package it.tangodev.popularmoviesapp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.tangodev.popularmoviesapp.models.Movie;
import it.tangodev.popularmoviesapp.models.MovieDetails;
import it.tangodev.popularmoviesapp.models.MovieReview;
import it.tangodev.popularmoviesapp.models.MovieVideo;

public class JsonConvertUtils {
    public static final String LOG_TAG = JsonConvertUtils.class.getSimpleName();

    public static List<Movie> getMovieListFromJsonString(String jsonString) {
        List<Movie> result = new ArrayList<>();

        try {
            JSONObject mainJsonObject = new JSONObject(jsonString);
            JSONArray movieListJson = mainJsonObject.getJSONArray("results");
            for(int i = 0 ; i < movieListJson.length() ; i++) {
                result.add(getMovieFromJsonObject(movieListJson.getJSONObject(i)));
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error:" , e);
        }

        return result;
    }

    public static Movie getMovieFromJsonObject(JSONObject jsonObject) {
        try {
            Movie movie = new Movie();
            movie.setId(jsonObject.getInt("id"));
            movie.setTitle(jsonObject.getString("title"));
            movie.setOverview(jsonObject.getString("overview"));
            movie.setPosterUrl(Constants.MOVIE_POSTER_BASE_URL + jsonObject.getString("poster_path"));

            return movie;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error:" , e);
        }
        return null;
    }

    public static MovieDetails getMovieDetailsFromJsonString(String jsonString) {
        try {
            MovieDetails movieDetails = new MovieDetails();
            JSONObject mainJsonObject = new JSONObject(jsonString);
            try {
                JSONArray videoJsonArray = mainJsonObject.getJSONObject("videos").getJSONArray("results");
                List<MovieVideo> videos = new ArrayList<>();
                for(int i = 0 ; i < videoJsonArray.length() ; i++) {
                    videos.add(getMovieVideoFromJsonObject(videoJsonArray.getJSONObject(i)));
                }
                movieDetails.setVideos(videos);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error:" , e);
            }

            try {
                JSONArray reviewsJsonArray = mainJsonObject.getJSONObject("reviews").getJSONArray("results");
                List<MovieReview> reviews = new ArrayList<>();
                for(int i = 0 ; i < reviewsJsonArray.length() ; i++) {
                    reviews.add(getMovieReviewFromJsonObject(reviewsJsonArray.getJSONObject(i)));
                }
                movieDetails.setReviews(reviews);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error:" , e);
            }

            return movieDetails;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error:" , e);
        }
        return null;
    }

    public static MovieVideo getMovieVideoFromJsonObject(JSONObject jsonObject) {
        try {
            MovieVideo movieVideo = new MovieVideo();
            movieVideo.setId(jsonObject.getString("id"));
            movieVideo.setKey(jsonObject.getString("key"));
            movieVideo.setName(jsonObject.getString("name"));
            movieVideo.setSite(jsonObject.getString("site"));

            return movieVideo;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error:" , e);
        }
        return null;
    }

    public static MovieReview getMovieReviewFromJsonObject(JSONObject jsonObject) {
        try {
            MovieReview movieReview = new MovieReview();
            movieReview.setId(jsonObject.getString("id"));
            movieReview.setAuthor(jsonObject.getString("author"));
            movieReview.setContent(jsonObject.getString("content"));
            movieReview.setUrl(jsonObject.getString("url"));

            return movieReview;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error:" , e);
        }
        return null;
    }
}
