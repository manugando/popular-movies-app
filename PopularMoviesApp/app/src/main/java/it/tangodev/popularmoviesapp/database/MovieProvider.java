package it.tangodev.popularmoviesapp.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

public class MovieProvider extends ContentProvider {
    private MovieDbHelper movieDbHelper;
    private static final UriMatcher uriMatcher = buildUriMatcher();
    private static final SQLiteQueryBuilder queryBuilder = buildQueryBuilder();

    static final int FAVOURITES = 100;
    static final int FAVOURITE_MOVIE_ID = 101;

    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (uriMatcher.match(uri)) {
            case FAVOURITES:
            {
                retCursor = queryBuilder.query(movieDbHelper.getReadableDatabase(),
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                );
                break;
            }
            case FAVOURITE_MOVIE_ID: {
                retCursor = queryBuilder.query(movieDbHelper.getReadableDatabase(),
                    projection,
                    "movie_id=?",
                    new String[]{MovieContract.FavouriteEntry.getMovieIdSettingFromUri(uri)},
                    null,
                    null,
                    sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);

        switch (match) {
            case FAVOURITES:
                return MovieContract.FavouriteEntry.CONTENT_TYPE;
            case FAVOURITE_MOVIE_ID:
                return MovieContract.FavouriteEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case FAVOURITES: {
                long _id = db.insert(MovieContract.FavouriteEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.FavouriteEntry.buildFavouriteUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_FAVOURITE, FAVOURITES);
        matcher.addURI(authority, MovieContract.PATH_FAVOURITE + "/*", FAVOURITE_MOVIE_ID);

        return matcher;
    }

    static SQLiteQueryBuilder buildQueryBuilder() {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(MovieContract.FavouriteEntry.TABLE_NAME);
        return sqLiteQueryBuilder;
    }
}
