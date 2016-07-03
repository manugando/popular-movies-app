package it.tangodev.popularmoviesapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import it.tangodev.popularmoviesapp.adapters.MovieListAdapter;
import it.tangodev.popularmoviesapp.fragments.MovieDetailFragment;
import it.tangodev.popularmoviesapp.fragments.MovieListFragment;
import it.tangodev.popularmoviesapp.models.Movie;
import it.tangodev.popularmoviesapp.utils.Preferences;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieListAdapterListener {
    private String currentMovieListMode;
    private boolean isModalitaMasterDetail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentMovieListMode = Preferences.getPreferredMovieListMode(this);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.movie_detail_container) != null) {
            isModalitaMasterDetail = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new MovieDetailFragment(), MovieDetailFragment.FRAGMENT_TAG)
                        .commit();
            }

            MovieListFragment movieListFragment =  ((MovieListFragment)getSupportFragmentManager().findFragmentById(R.id.movie_list_fragment));
            movieListFragment.setNumberOfColumns(3);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Movie movie) {
        // TODO USARE PARCELABLE
        if(isModalitaMasterDetail) {
            switchDetailFragment(movie);
        } else {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(MovieDetailFragment.MOVIE_OBJECT, movie);
            startActivity(intent);
        }
    }

    private void switchDetailFragment(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();

        if(movie != null) {
            Bundle arguments = new Bundle();
            arguments.putSerializable(MovieDetailFragment.MOVIE_OBJECT, movie);
            fragment.setArguments(arguments);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_container, fragment, MovieDetailFragment.FRAGMENT_TAG)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String movieListMode = Preferences.getPreferredMovieListMode(this);
        if (movieListMode != null && !movieListMode.equals(currentMovieListMode)) {
            MovieListFragment movieListFragment = (MovieListFragment)getSupportFragmentManager().findFragmentById(R.id.movie_list_fragment);
            if (null != movieListFragment) {
                movieListFragment.onMovieListModeChange();
            }
            if(isModalitaMasterDetail) {
                switchDetailFragment(null);
            }
            currentMovieListMode = movieListMode;
        }
    }
}
