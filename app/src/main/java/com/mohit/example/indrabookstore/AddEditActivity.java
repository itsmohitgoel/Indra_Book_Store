package com.mohit.example.indrabookstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mohit.example.indrabookstore.data.BookContract;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_NAME;
import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_PRICE;
import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_QUANTITY;
import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_SUPPLIER_NAME;
import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER;

public class AddEditActivity extends AppCompatActivity {
    @BindView(R.id.title_text_box)
    EditText mTitleTextBox;
    @BindView(R.id.price_text_box)
    EditText mPriceTextBox;
    @BindView(R.id.increment_button)
    Button mIncrementButton;
    @BindView(R.id.quantity_text_view)
    TextView mQuantityTextView;
    @BindView(R.id.decrement_button)
    Button mDecrementButton;
    @BindView(R.id.supplier_name_text_box)
    EditText mSupplierNameTextBox;
    @BindView(R.id.supplier_contact_num_text_box)
    EditText mSupplierContactNumTextBox;
    @BindView(R.id.save_button)
    Button mSaveButton;
    @BindView(R.id.delete_button)
    Button mDeleteButton;

    private int mQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        ButterKnife.bind(this);

        setUpClickListeners();
    }

    private void setUpClickListeners() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mTitleTextBox.getText().toString();
                String price = mPriceTextBox.getText().toString();
                String supplierName = mSupplierNameTextBox.getText().toString();
                String supplierPhoneNumber = mSupplierContactNumTextBox.getText().toString();

                if (TextUtils.isEmpty(title)
                        || TextUtils.isEmpty(price)
                        || TextUtils.isEmpty(supplierName)
                        || TextUtils.isEmpty(supplierPhoneNumber)) {
                    Toast.makeText(AddEditActivity.this, "Some input field is empty. Please fill them before" +
                            " proceeding furhter!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_NAME, title);
                contentValues.put(COLUMN_PRICE, price);
                contentValues.put(COLUMN_QUANTITY, mQuantity);
                contentValues.put(COLUMN_SUPPLIER_NAME, supplierName);
                contentValues.put(COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumber);

                Uri bookUri = getContentResolver().insert(BookContract.BookEntry.CONTENT_URI,
                        contentValues);
                long bookRowId = ContentUris.parseId(bookUri);

                if (bookRowId > -1) {
                    Snackbar.make(v, "Book Created", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });

        mIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuantity++;
                mQuantityTextView.setText(mQuantity + "");
            }
        });

        mDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuantity > 0) {
                    mQuantity--;
                }
                mQuantityTextView.setText(mQuantity + "");
            }
        });
    }
}
