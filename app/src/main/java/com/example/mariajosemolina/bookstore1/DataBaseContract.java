package com.example.mariajosemolina.bookstore1;

import android.provider.BaseColumns;

public class DataBaseContract {

    public static final String DATABASE_NAME = "inventory.db";
    public static final int DATABASE_VERSION = 1;

    private DataBaseContract() {
        // This constructor is empty because even though a Contract should
        // not be instantiated, in case of any mistake, the constructor will do nothing.
    }

    public static final class Supplies implements BaseColumns {

        // Columns information
        public final static String TABLE_NAME = "supplies";
        public final static String ID = BaseColumns._ID;
        public final static String PRODUCT_NAME = "productname";
        public final static String PRICE = "price";
        public final static String QUANTITY = "quantity";
        public final static String SUPPLIER_NAME = "suppliername";
        public final static String SUPPLIER_PHONE_NUMBER = "suppliernumber";

        // Command to create the table
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCT_NAME + " STRING, "
                + PRICE + " REAL, "
                + QUANTITY + " INTEGER, "
                + SUPPLIER_NAME + " STRING,"
                + SUPPLIER_PHONE_NUMBER + " STRING"
                + ")";

        // Command to delete the table
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}