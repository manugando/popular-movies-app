package it.tangodev.popularmoviesapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import it.tangodev.popularmoviesapp.R;

public class Preferences {

    public static String getPreferredMovieListMode(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_movie_list_mode_key),
                context.getString(R.string.pref_movie_list_mode_key_top_rated));
    }
}
