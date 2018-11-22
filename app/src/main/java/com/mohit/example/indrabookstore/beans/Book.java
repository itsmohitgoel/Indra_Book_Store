package com.mohit.example.indrabookstore.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohit Goel on 20-Nov-18.
 */

public class Book implements Parcelable {
    private String mId;
    private String mName;
    private String mPrice;
    private String mQuantity;
    private String mSupplierName;
    private String mSupplierPhoneNumber;

    public Book() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

    public String getSupplierName() {
        return mSupplierName;
    }

    public void setSupplierName(String supplierName) {
        mSupplierName = supplierName;
    }

    public String getSupplierPhoneNumber() {
        return mSupplierPhoneNumber;
    }

    public void setSupplierPhoneNumber(String supplierPhoneNumber) {
        mSupplierPhoneNumber = supplierPhoneNumber;
    }

    @Override
    public String toString() {
        return "Book{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mPrice='" + mPrice + '\'' +
                ", mQuantity='" + mQuantity + '\'' +
                ", mSupplierName='" + mSupplierName + '\'' +
                ", mSupplierPhoneNumber='" + mSupplierPhoneNumber + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mId);
        parcel.writeString(mName);
        parcel.writeString(mPrice);
        parcel.writeString(mQuantity);
        parcel.writeString(mSupplierName);
        parcel.writeString(mSupplierPhoneNumber);
    }

    private Book(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mPrice = in.readString();
        mQuantity = in.readString();
        mSupplierName = in.readString();
        mSupplierPhoneNumber = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
