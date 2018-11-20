package com.mohit.example.indrabookstore.beans;

/**
 * Created by Mohit Goel on 20-Nov-18.
 */

public class Book {
    private String mId;
    private String mName;
    private String mPrice;
    private String mQuantity;
    private String mSupplierName;
    private String mSupplierPhoneNumber;

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
}
