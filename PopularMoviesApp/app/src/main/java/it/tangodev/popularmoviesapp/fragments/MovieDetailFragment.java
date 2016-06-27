package it.tangodev.popularmoviesapp.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import it.tangodev.popularmoviesapp.R;
import it.tangodev.popularmoviesapp.adapters.MovieReviewListAdapter;
import it.tangodev.popularmoviesapp.adapters.MovieVideoListAdapter;
import it.tangodev.popularmoviesapp.asynctasks.MovieDetailAsyncTask;
import it.tangodev.popularmoviesapp.models.Movie;
import it.tangodev.popularmoviesapp.models.MovieDetails;
import it.tangodev.popularmoviesapp.models.MovieVideo;

public class MovieDetailFragment extends Fragment {
    public static final String MOVIE_OBJECT = "MOVIE_OBJECT";
    private TextView movieDetailFragmentTitle, movieDetailFragmentOverview, movieDetailFragmentYear, movieDetailFragmentVote, movieDetailFragmentDuration;
    private ImageView movieDetailFragmentPoster;
    private RecyclerView movieDetailFragmentVideos, movieDetailFragmentReviews;
    private MovieVideoListAdapter movieVideoListAdapter;
    private MovieReviewListAdapter movieReviewListAdapter;
    private Movie movie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        movieDetailFragmentOverview = (TextView) rootView.findViewById(R.id.movie_detail_fragment_overview);
        movieDetailFragmentTitle = (TextView) rootView.findViewById(R.id.movie_detail_fragment_title);
        movieDetailFragmentYear = (TextView) rootView.findViewById(R.id.movie_detail_fragment_year);
        movieDetailFragmentVote = (TextView) rootView.findViewById(R.id.movie_detail_fragment_vote);
        movieDetailFragmentDuration = (TextView) rootView.findViewById(R.id.movie_detail_fragment_duration);
        movieDetailFragmentPoster = (ImageView) rootView.findViewById(R.id.movie_detail_fragment_poster);

        movieDetailFragmentVideos = (RecyclerView) rootView.findViewById(R.id.movie_detail_fragment_videos);
        movieVideoListAdapter = new MovieVideoListAdapter() {

            @Override
            public void onItemClick(MovieVideo movieVideo) {
                launchYouTubeIntent(movieVideo.getKey());
            }
        };
        movieDetailFragmentVideos.setAdapter(movieVideoListAdapter);
        movieDetailFragmentVideos.setLayoutManager(new LinearLayoutManager(getContext()));

        movieDetailFragmentReviews = (RecyclerView) rootView.findViewById(R.id.movie_detail_fragment_reviews);
        movieReviewListAdapter = new MovieReviewListAdapter();
        movieDetailFragmentReviews.setAdapter(movieReviewListAdapter);
        movieDetailFragmentReviews.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            movie = (Movie) arguments.getSerializable(MovieDetailFragment.MOVIE_OBJECT);
        }

        popolaUiPrincipale();
        loadMovieDetails();
    }

    private void popolaUiPrincipale() {
        movieDetailFragmentTitle.setText(movie.getTitle());
        movieDetailFragmentOverview.setText(movie.getOverview());
        movieDetailFragmentVote.setText(movie.getVoteAvg() + "/10" );
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        movieDetailFragmentYear.setText(sdf.format(movie.getReleaseDate()));
        Picasso.with(getContext()).load(movie.getPosterUrl()).fit().centerInside().into(movieDetailFragmentPoster);
    }

    private void loadMovieDetails() {
        MovieDetailAsyncTask movieDetailAsyncTask = new MovieDetailAsyncTask() {
            @Override
            protected void onPostExecute(MovieDetails movieDetails) {
                super.onPostExecute(movieDetails);
                movieVideoListAdapter.getDataset().clear();
                if(movieDetails.getVideos() != null) {
                    movieVideoListAdapter.getDataset().addAll(movieDetails.getVideos());
                }
                movieVideoListAdapter.notifyDataSetChanged();

                movieReviewListAdapter.getDataset().clear();
                if(movieDetails.getReviews() != null) {
                    movieReviewListAdapter.getDataset().addAll(movieDetails.getReviews());
                }
                movieReviewListAdapter.notifyDataSetChanged();
            }
        };
        movieDetailAsyncTask.execute(movie.getId());
    }

    private void launchYouTubeIntent(String videoId) {
        Uri uri = Uri.parse("vnd.youtube:" + videoId);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoId)));
        }
    }
}
