package com.mohit.example.indrabookstore.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mohit Goel on 20-Nov-18.
 */

public class BookContract {

    public static final String CONTENT_AUTHORITY = "com.example.mohgoel.indrabookstore.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BOOK = BookEntry.TABLE_NAME;
    public static final String PATH_BOOK_ID = BookEntry.TABLE_NAME + "/#";
//    public static final String PATH_IMAGE = ImageEntry.TABLE_NAME;

    /* Inner class that defines the table contents of the books table */
    public static final class BookEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BOOK).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOK;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOK;

        public static final String TABLE_NAME = "book";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";

        // Uri constructor to build for a specific book entry
        public static Uri buildBookUri(long id) {
            Uri reminderUri = ContentUris.withAppendedId(CONTENT_URI, id);
            return reminderUri;
        }

        // Extract row id of book if Uri is given
        public static long getBookIdFromUri(Uri uri) {
            return Long.parseLong(uri.getLastPathSegment());
        }
    }


}
