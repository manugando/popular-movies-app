package it.tangodev.popularmoviesapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.tangodev.popularmoviesapp.R;
import it.tangodev.popularmoviesapp.asynctasks.MovieDetailAsyncTask;
import it.tangodev.popularmoviesapp.models.Movie;
import it.tangodev.popularmoviesapp.models.MovieDetails;

public class MovieDetailFragment extends Fragment {
    public static final String MOVIE_OBJECT = "MOVIE_OBJECT";
    private TextView movieDetailFragmentOverview;
    private Movie movie;
    private MovieDetailAsyncTask movieDetailAsyncTask;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        movieDetailFragmentOverview = (TextView) rootView.findViewById(R.id.movie_detail_fragment_overview);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            movie = (Movie) arguments.getSerializable(MovieDetailFragment.MOVIE_OBJECT);
        }
        // TODO popola UI usando i dati già presenti

        movieDetailAsyncTask = new MovieDetailAsyncTask() {
            @Override
            protected void onPostExecute(MovieDetails movieDetails) {
                super.onPostExecute(movieDetails);
                movieDetailFragmentOverview.setText(movieDetails.getReviews().size() + " - " + movieDetails.getVideos().size());
            }
        };

        loadMovieDetails();
    }

    private void loadMovieDetails() {
        movieDetailAsyncTask.execute(movie.getId());
    }
}
