package it.tangodev.popularmoviesapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import it.tangodev.popularmoviesapp.adapters.MovieListAdapter;
import it.tangodev.popularmoviesapp.fragments.MovieDetailFragment;
import it.tangodev.popularmoviesapp.models.Movie;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieListAdapterListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra(MovieDetailFragment.MOVIE_OBJECT, movie);
        startActivity(intent);
    }
}
