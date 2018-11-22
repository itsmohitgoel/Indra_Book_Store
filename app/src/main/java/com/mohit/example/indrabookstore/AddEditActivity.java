package com.mohit.example.indrabookstore;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mohit.example.indrabookstore.beans.Book;
import com.mohit.example.indrabookstore.data.BookContract.BookEntry;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_NAME;
import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_PRICE;
import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_QUANTITY;
import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_SUPPLIER_NAME;
import static com.mohit.example.indrabookstore.data.BookContract.BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER;

public class AddEditActivity extends AppCompatActivity {
    private static final String TAG = AddEditActivity.class.getSimpleName();
    private static final String EXTRA_BOOK_PARCELABLE = "book";

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
    @BindView(R.id.call_for_order_button)
    ImageButton mCallForOrderButton;

    private int mQuantity;
    private boolean mShouldEdit;
    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        ButterKnife.bind(this);

        initialize();
        setUpClickListeners();
    }

    public static Intent newIntent(Context context, Book book) {
        Intent intent = new Intent(context, AddEditActivity.class);
        intent.putExtra(EXTRA_BOOK_PARCELABLE, book);
        return intent;
    }


    private void initialize() {
        mBook = getIntent().getParcelableExtra(EXTRA_BOOK_PARCELABLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mShouldEdit = (mBook != null);

        if (mShouldEdit) {
            mTitleTextBox.setText(mBook.getName());
            mPriceTextBox.setText(mBook.getPrice());
            mQuantity = Integer.parseInt(mBook.getQuantity());
            mQuantityTextView.setText(mBook.getQuantity());
            mSupplierNameTextBox.setText(mBook.getSupplierName());
            mSupplierContactNumTextBox.setText(mBook.getSupplierPhoneNumber());

            mDeleteButton.setEnabled(true);
            mCallForOrderButton.setEnabled(true);
        } else {
            mDeleteButton.setEnabled(false);
            mCallForOrderButton.setEnabled(false);
        }
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
                            " proceeding further!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_NAME, title);
                contentValues.put(COLUMN_PRICE, price);
                contentValues.put(COLUMN_QUANTITY, mQuantity);
                contentValues.put(COLUMN_SUPPLIER_NAME, supplierName);
                contentValues.put(COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumber);


                if (mShouldEdit) {
                    int updateCount = 0;
                    updateCount = getContentResolver().update(BookEntry.CONTENT_URI,
                            contentValues,
                            BookEntry._ID + " = ?",
                            new String[]{mBook.getId()});

                    if (updateCount > 0) {
                        Snackbar.make(v, "Book details Updated", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                } else {
                    Uri bookUri = getContentResolver().insert(BookEntry.CONTENT_URI,
                            contentValues);
                    long bookRowId = ContentUris.parseId(bookUri);

                    if (bookRowId > -1) {
                        Snackbar.make(v, "Book Created", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }

                    AddEditActivity.this.finish();
                }
            }
        });

        mIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuantity++;
                mQuantityTextView.setText(mQuantity + getString(R.string.empty_string_literal));
            }
        });

        mDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuantity < 1) {
                    Snackbar.make(v, "Cannot have negative quantity", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                mQuantity--;
                mQuantityTextView.setText(mQuantity + getString(R.string.empty_string_literal));
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayDialog();
            }
        });

        mCallForOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = String.format("tel: %s",
                        mBook.getSupplierPhoneNumber());

                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse(phoneNumber));
                if (dialIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(dialIntent);
                } else {
                    Log.e(TAG, "Can't resolve app for ACTION_DIAL Intent.");
                }
            }
        });
    }

    private void deleteBookFromDB() {
        int deleteCount = 0;
        deleteCount = getContentResolver().delete(BookEntry.CONTENT_URI,
                BookEntry._ID + " = ?",
                new String[]{mBook.getId()});

        if (deleteCount > 0) {
            Toast.makeText(AddEditActivity.this, "Book deleted Successfully",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void displayDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_yes_no);

        TextView tvMessage = dialog.findViewById(R.id.textDialogYesNoMessage);
        tvMessage.setText(R.string.dialog_header_delete_book);

        Button btnYes = dialog.findViewById(R.id.btnDialogYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteBookFromDB();
                AddEditActivity.this.finish();
            }
        });

        Button btnNo = dialog.findViewById(R.id.btnDialogNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
