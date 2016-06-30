package it.tangodev.popularmoviesapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import it.tangodev.popularmoviesapp.R;
import it.tangodev.popularmoviesapp.adapters.MovieListAdapter;
import it.tangodev.popularmoviesapp.asynctasks.FavouriteMovieListAsyncTask;
import it.tangodev.popularmoviesapp.asynctasks.MovieListAsyncTask;
import it.tangodev.popularmoviesapp.models.Movie;
import it.tangodev.popularmoviesapp.utils.Preferences;

public class MovieListFragment extends Fragment {
    private MovieListAdapter movieListAdapter;
    private RecyclerView movieListRecyclerView;
    private String currentMovieListMode;
    private int currentPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);

        movieListRecyclerView = (RecyclerView) rootView.findViewById(R.id.movie_list_recycler_view);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movieListAdapter = new MovieListAdapter(getContext(), (MovieListAdapter.MovieListAdapterListener) getActivity()) {

            @Override
            public void onLastItemBind() {
                if(!currentMovieListMode.equals(getString(R.string.pref_movie_list_mode_key_favourites))) {
                    currentPage ++;
                    loadMoviesFromServer();
                }
            }
        };
        movieListRecyclerView.setAdapter(movieListAdapter);
        movieListRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        onMovieListModeChange();
    }

    public void onMovieListModeChange() {
        movieListRecyclerView.scrollToPosition(0);
        movieListAdapter.getDataset().clear();
        movieListAdapter.notifyDataSetChanged();
        currentPage = 1;
        currentMovieListMode = Preferences.getPreferredMovieListMode(getContext());

        if(currentMovieListMode.equals(getString(R.string.pref_movie_list_mode_key_favourites))) {
            loadFavouriteMovies();
        } else {
            loadMoviesFromServer();
        }
    }

    private void loadMoviesFromServer() {
        MovieListAsyncTask movieListAsyncTask = new MovieListAsyncTask() {
            @Override
            protected void onPostExecute(List<Movie> movies) {
                super.onPostExecute(movies);
                movieListAdapter.getDataset().addAll(movies);
                movieListAdapter.notifyDataSetChanged();
            }
        };
        movieListAsyncTask.execute(currentMovieListMode.equals(getString(R.string.pref_movie_list_mode_key_top_rated)) ?
                MovieListAsyncTask.MODE_TOP_RATED :
                MovieListAsyncTask.MODE_POPULAR, currentPage);
    }

    private void loadFavouriteMovies() {
        FavouriteMovieListAsyncTask favouriteMovieListAsyncTask = new FavouriteMovieListAsyncTask(getContext()) {
            @Override
            protected void onPostExecute(List<Movie> movies) {
                super.onPostExecute(movies);
                movieListAdapter.getDataset().addAll(movies);
                movieListAdapter.notifyDataSetChanged();
            }
        };
        favouriteMovieListAsyncTask.execute();
    }
}
