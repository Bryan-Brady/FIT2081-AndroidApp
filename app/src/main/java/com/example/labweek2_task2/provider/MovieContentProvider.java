package com.example.labweek2_task2.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MovieContentProvider extends ContentProvider {

    //set content authority
    public static final String CONTENT_AUTHORITY = "fit2081.app.Bryan";
    public static final Uri CONTENT_URI= Uri.parse("content://"+CONTENT_AUTHORITY);

    MovieDatabase db;

    //public static final int MOVIE = 1;
    //public static final int BIGBUDGET= 40;
    //content://fit2081.app.Bryan/BigBudget
    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /*static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, "movies_table", MOVIE);
        sUriMatcher.addURI(CONTENT_AUTHORITY, "movies_table/*", BIGBUDGET);

    }*/

    public MovieContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int deletionCount;

    /*
    switch(selection){
        case "cost < 15":
            deletionCount = db.getOpenHelper().getWritableDatabase().delete(Movie.TABLE_NAME, selection, selectionArgs);
            break;
        case "date < 2010":
            deletionCount = db.getOpenHelper().getWritableDatabase().delete(Movie.TABLE_NAME, selection, selectionArgs);
            break;
        case "name = nammmmm":
            deletionCount = db.getOpenHelper().getWritableDatabase().delete(Movie.TABLE_NAME, selection, selectionArgs);
            break;
    }
    */
        deletionCount = db.getOpenHelper().getWritableDatabase().delete(Movie.TABLE_NAME, selection, selectionArgs);

        return deletionCount;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        /*
        int match = sUriMatcher.match(uri);

        switch (match){
            case MOVIE:
                break;
            case BIGBUDGET:
                break;
        }
        */

        long newId = db.getOpenHelper().getWritableDatabase().insert(Movie.TABLE_NAME, 0, values);

        return ContentUris.withAppendedId(CONTENT_URI, newId);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        // 1st
        db = MovieDatabase.getMovieDatabase(getContext());
        //set to true
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        // read from database and return back Cursor (special data structure that holds 0, 1 or more rows)


        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(Movie.TABLE_NAME);
        String query = builder.buildQuery(projection, selection, null, null, null, null);


        Cursor cursor = db.getOpenHelper().getReadableDatabase().query(query);
        return (cursor);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}