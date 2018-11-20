package com.mohit.example.indrabookstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mohit.example.indrabookstore.data.BookContract.BookEntry;

/**
 * Created by Mohit Goel on 20-Nov-18.
 */

public class BookDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "indrabook.db";

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_BOOK_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME + " (" +
                BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BookEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                BookEntry.COLUMN_PRICE + " REAL , " +
                BookEntry.COLUMN_QUANTITY + " INTEGER DEFAULT 0, " +
                BookEntry.COLUMN_SUPPLIER_NAME + " TEXT,  " +
                BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER + " INTEGER DEFAULT 0); ";

        db.execSQL(SQL_CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME);
        onCreate(db);
    }
}
