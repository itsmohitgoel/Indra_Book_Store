package com.mohit.example.indrabookstore.data;

import android.provider.BaseColumns;

/**
 * Created by Mohit Goel on 20-Nov-18.
 */

public class BookContract {

    /* Inner class that defines the table contents of the books table */
    public static final class BookEntry implements BaseColumns {

        public static final String TABLE_NAME = "book";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";

    }


}
