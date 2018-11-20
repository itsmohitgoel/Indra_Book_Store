package com.mohit.example.indrabookstore.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.mohit.example.indrabookstore.beans.Book;

/**
 * Created by Mohit Goel on 20-Nov-18.
 */

public class BookCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public BookCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Book getBook() {
        String id = getString(getColumnIndex(BookContract.BookEntry._ID));
        String name = getString(getColumnIndex(BookContract.BookEntry.COLUMN_NAME));
        String price = getString(getColumnIndex(BookContract.BookEntry.COLUMN_PRICE));
        String quantity = getString(getColumnIndex(BookContract.BookEntry.COLUMN_QUANTITY));
        String supplierName = getString(getColumnIndex(BookContract.BookEntry.COLUMN_SUPPLIER_NAME));
        String supplierPhoneNumber = getString(getColumnIndex(BookContract.BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER));

        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setPrice(price);
        book.setQuantity(quantity);
        book.setSupplierName(supplierName);
        book.setSupplierPhoneNumber(supplierPhoneNumber);

        return book;
    }
}
