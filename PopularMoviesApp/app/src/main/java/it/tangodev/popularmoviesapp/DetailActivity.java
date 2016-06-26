package it.tangodev.popularmoviesapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import it.tangodev.popularmoviesapp.asynctasks.MovieDetailAsyncTask;
import it.tangodev.popularmoviesapp.fragments.MovieDetailFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putSerializable(MovieDetailFragment.MOVIE_OBJECT, getIntent().getSerializableExtra(MovieDetailFragment.MOVIE_OBJECT));

            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_activity_container, fragment)
                    .commit();
        }
    }

}
