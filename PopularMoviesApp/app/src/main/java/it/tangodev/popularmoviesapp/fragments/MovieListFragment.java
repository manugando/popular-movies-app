package it.tangodev.popularmoviesapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.tangodev.popularmoviesapp.R;
import it.tangodev.popularmoviesapp.adapters.MovieListAdapter;
import it.tangodev.popularmoviesapp.asynctasks.MovieListAsyncTask;
import it.tangodev.popularmoviesapp.models.Movie;

public class MovieListFragment extends Fragment {
    private MovieListAdapter movieListAdapter;
    private MovieListAsyncTask movieListAsyncTask;
    private RecyclerView movieListRecyclerView;

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

        movieListAdapter = new MovieListAdapter(getContext(), (MovieListAdapter.MovieListAdapterListener) getActivity());
        movieListRecyclerView.setAdapter(movieListAdapter);
        movieListRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        movieListAsyncTask = new MovieListAsyncTask() {
            @Override
            protected void onPostExecute(List<Movie> movies) {
                super.onPostExecute(movies);
                movieListAdapter.getDataset().clear();
                movieListAdapter.getDataset().addAll(movies);
                movieListAdapter.notifyDataSetChanged();
            }
        };

        updateMovieList();
    }

    private void updateMovieList() {
        movieListAsyncTask.execute();
    }
}
