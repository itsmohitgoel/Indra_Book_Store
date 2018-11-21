package com.mohit.example.indrabookstore.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mohit.example.indrabookstore.R;
import com.mohit.example.indrabookstore.beans.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohit Goel on 21-Nov-18.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private List<Book> mBooksList = new ArrayList<>();

    public BooksAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.book_list_item, parent, false);

        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.bindBook(mBooksList.get(position));
    }

    @Override
    public int getItemCount() {
        return mBooksList != null ? mBooksList.size() : 0;
    }

    public void resetData() {
        mBooksList.clear();
    }

    public void setBooks(List<Book> books) {
        mBooksList.clear();
        mBooksList = books;
        notifyDataSetChanged();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_text_view)
        TextView titleTextView;
        @BindView(R.id.price_text_view)
        TextView priceTextView;
        @BindView(R.id.supplier_name_text_view)
        TextView supplierNameTextView;
        @BindView(R.id.supplier_contact_text_view)
        TextView supplierContactTextView;
        @BindView(R.id.quantity_text_view)
        TextView quantityTextView;


        public BookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindBook(final Book book) {
            titleTextView.setText(book.getName());
            priceTextView.setText(book.getPrice());
            quantityTextView.setText(book.getQuantity());
            supplierNameTextView.setText(book.getSupplierName());
            supplierContactTextView.setText(book.getSupplierPhoneNumber());
        }

    }
}