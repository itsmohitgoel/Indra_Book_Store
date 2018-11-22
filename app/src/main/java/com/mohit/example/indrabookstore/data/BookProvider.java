package com.mohit.example.indrabookstore.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.mohit.example.indrabookstore.data.BookContract.BookEntry;

/**
 * Created by Mohit Goel on 21-Nov-18.
 */

public class BookProvider extends ContentProvider {
    private static final UriMatcher mUriMatcher = buildUriMatcher();
    private BookDbHelper mBookDbHelper;

    private static final int BOOK = 100;
    private static final int BOOK_WITH_ID = 101;

    private static final SQLiteQueryBuilder mBookQueryBuilder;

    static {
        mBookQueryBuilder = new SQLiteQueryBuilder();

        // This is a simple book query builder, without images informartion
        mBookQueryBuilder.setTables(BookEntry.TABLE_NAME);
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(BookContract.CONTENT_AUTHORITY, BookContract.PATH_BOOK, BOOK);
        matcher.addURI(BookContract.CONTENT_AUTHORITY, BookContract.PATH_BOOK_ID, BOOK_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mBookDbHelper = new BookDbHelper(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {
        final int match = mUriMatcher.match(uri);

        switch (match) {
            case BOOK:
                return BookEntry.CONTENT_TYPE;
            case BOOK_WITH_ID:
                return BookEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = mUriMatcher.match(uri);
        SQLiteDatabase db = mBookDbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (match) {
            case BOOK:
                cursor = mBookQueryBuilder.query(db,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        sortOrder
                );
                break;
            case BOOK_WITH_ID:
                String bookRowID = uri.getPathSegments().get(1);
                String bookSelection = BookEntry.TABLE_NAME + "." + BookEntry._ID + " = ?";
                cursor = mBookQueryBuilder.query(db,
                        projection,
                        bookSelection,
                        new String[]{bookRowID},
                        null, null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("unknown uri in query() method: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mBookDbHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);

        Uri returnUri = null;
        long _id = 0;
        switch (match) {
            case BOOK:
                _id = db.insert(BookEntry.TABLE_NAME, null, values);

                if (_id > 0) {
                    //Inserted correctly
                    returnUri = BookEntry.buildBookUri(_id);
                } else {
                    throw new SQLException("Failed to insert book row into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(returnUri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mBookDbHelper.getWritableDatabase();

        final int match = mUriMatcher.match(uri);
        int deleteCount = 0;

        if (selection == null) {
            selection = "1";
        }

        switch (match) {
            case BOOK:
                deleteCount = db.delete(BookEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("unknown uri: " + uri);
        }

        if (deleteCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mBookDbHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);

        int updateCount = 0;

        switch (match) {
            case BOOK:
                updateCount = db.update(BookEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }

        if (updateCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mBookDbHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        int returnCount = 0;

        switch (match) {
            case BOOK:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(BookEntry.TABLE_NAME, null, value);

                        if (_id > 0) {
                            //Inserted correctly
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                break;
            default:
                throw new UnsupportedOperationException("Unsupported uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
    }

}
