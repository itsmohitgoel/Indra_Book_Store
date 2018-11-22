package com.mohit.example.indrabookstore;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.mohit.example.indrabookstore.adapters.BooksAdapter;
import com.mohit.example.indrabookstore.beans.Book;
import com.mohit.example.indrabookstore.data.BookContract;
import com.mohit.example.indrabookstore.data.BookCursorWrapper;
import com.mohit.example.indrabookstore.data.BookDbHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_NAME;
import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_PRICE;
import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_QUANTITY;
import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_SUPPLIER_NAME;
import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER;
import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.TABLE_NAME;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.add_book_button)
    FloatingActionButton mAddBookButton;
    @BindView(R.id.books_recycler_view)
    RecyclerView mBooksRecyclerView;
    @BindView(R.id.empty_list_text_view)
    TextView mEmptyListTextView;

    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        mBooksRecyclerView.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));

        mAddBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddEditActivity.class);
                startActivity(intent);
            }
        });


        // used to debug the database values
        Stetho.initializeWithDefaults(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        BookDbHelper dbHelper = new BookDbHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        List<Book> books = getBooks();
        if (books != null && books.size() > 0) {
            BooksAdapter adapter = new BooksAdapter(this);
            adapter.setBooks(books);
            mBooksRecyclerView.setAdapter(adapter);
            mBooksRecyclerView.setVisibility(View.VISIBLE);
            mEmptyListTextView.setVisibility(View.GONE);

        } else {
            mBooksRecyclerView.setVisibility(View.GONE);
            mEmptyListTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.insert_menu_item) {
            insertDummyData();
        } else if (item.getItemId() == R.id.read_menu_item) {
            showBookData();
        }
        return true;
    }

    /**
     * Inserts a single Book in database via toolbar menu
     */
    private void insertDummyData() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, "Operating Systems");
        contentValues.put(COLUMN_PRICE, 455.50);
        contentValues.put(COLUMN_QUANTITY, 8);
        contentValues.put(COLUMN_SUPPLIER_NAME, "Pearson Publicaitons");
        contentValues.put(COLUMN_SUPPLIER_PHONE_NUMBER, "9663262419");

        mDatabase.insert(TABLE_NAME, null, contentValues);
    }

    /**
     * Wraps a Cursor I received from database query and add new
     * methods on top of it.
     */
    private BookCursorWrapper queryBooks(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        return new BookCursorWrapper(cursor);
    }

    /**
     * Queries for all the books, walks the cursor and return
     * a Book list.
     */
    private List<Book> getBooks() {
        List<Book> books = new ArrayList<>();

        BookCursorWrapper cursorWrapper = queryBooks(null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                books.add(cursorWrapper.getBook());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return books;
    }

    /**
     * This Method to be used in stage 2 of project
     */
    private Book getBook(String id) {
        BookCursorWrapper cursor = queryBooks(
                BookContract.BookEntry._ID + " = ?",
                new String[]{id}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getBook();
        } finally {
            cursor.close();
        }
    }

    /**
     * Displays Book data, when Toolbar Menu is used
     */
    private void showBookData() {
        List<Book> books = getBooks();

        if (books != null && !books.isEmpty()) {
            Book book = books.get(0);
        } else {
            Toast.makeText(this, getString(R.string.toast_text_no_data_in_db),
                    Toast.LENGTH_LONG).show();
        }
    }

}
