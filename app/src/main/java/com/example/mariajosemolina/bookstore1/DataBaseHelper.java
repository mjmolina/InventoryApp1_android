package com.example.mariajosemolina.bookstore1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    Context context;

    public DataBaseHelper(Context context) {
        super(context, DataBaseContract.DATABASE_NAME, null, DataBaseContract.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("onCreate", "executing...");
        Log.d("create_table", DataBaseContract.Supplies.CREATE_TABLE);
        db.execSQL(DataBaseContract.Supplies.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("onUpgrade", "executing...");
        Log.d("delete_table", DataBaseContract.Supplies.DELETE_TABLE);
        db.execSQL(DataBaseContract.Supplies.DELETE_TABLE);
        onCreate(db);
    }

    public void updateItemByID(int id, String productName, int quantity, double price, String supplierName, String supplierPhone) {
        // Getting a reference to the db for just for read
        SQLiteDatabase db = this.getReadableDatabase();

        //Cursor cursor;
        Cursor cursor;

        // Escape the string to avoid strange characters
        productName = android.database.DatabaseUtils.sqlEscapeString(productName);
        supplierName = android.database.DatabaseUtils.sqlEscapeString(supplierName);
        supplierPhone = android.database.DatabaseUtils.sqlEscapeString(supplierPhone);

        // Querying the database
        String query = "UPDATE " + DataBaseContract.Supplies.TABLE_NAME + " SET " +
                "productname=" + productName + ", " +
                "price=" + String.valueOf(price) + ", " +
                "quantity=" + String.valueOf(quantity) + ", " +
                "suppliername=" + supplierName + ", " +
                "suppliernumber=" + supplierPhone + " " +
                " WHERE _id=" + String.valueOf(id) + ";";
        Log.d("Query", query);
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();
        db.close();
    }

    public void printQueryResult(Cursor cursor) {
        Log.d("Cursor columns", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {
            do {
                // Passing values
                Integer id = cursor.getInt(0);
                String productName = cursor.getString(1);
                double price = cursor.getDouble(2);
                double quantity = cursor.getInt(3);
                String supplierName = cursor.getString(4);
                String supplierPhoneNumber = cursor.getString(5);

                Log.d("Result", id + ", " + productName + ", " + price + ", " + quantity + ", " + supplierName + ", " + supplierPhoneNumber);

            } while (cursor.moveToNext());
        }
    }

    public void dbInit() {
        // Data base creation
        // This will get a reference to the db, and if it does not exist, the db will
        // be created.
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the table has data, if there is nothing, we populate the db
        Cursor cursor = db.rawQuery("SELECT * FROM " + DataBaseContract.Supplies.TABLE_NAME, null);
        if (cursor == null || cursor.getCount() <= 0) {
            // Adding entries to the db if there was no
            Boolean result = dbPopulate(db);

            if (!result) {
                Toast.makeText(context,
                        "An error occurred during insertion to the DB",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context,
                        "The DB insert was executed successful",
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Closing the connection to the db since it was in Writing mode
        db.close();
    }

    public Boolean dbPopulate(SQLiteDatabase db) {

        // Create five book registry to populate our table
        BookRegistry b1 = new BookRegistry("The name of the wind", 9.4, 50, "Thalia BookStore", "+49234201112");
        BookRegistry b2 = new BookRegistry("The wise Man\'s Fear", 12.9, 25, "Thalia BookStore", "+49234201112");
        BookRegistry b3 = new BookRegistry("The three body problem", 11.0, 45, "Thalia BookStore", "+49234201112");
        BookRegistry b4 = new BookRegistry("The dark forest", 15.2, 15, "Karstadt", "+49211113112");
        BookRegistry b5 = new BookRegistry("Death\'s End", 25.2, 15, "Karstadt", "+49211113112");
        BookRegistry b6 = new BookRegistry("A Games of Thrones", 15.8, 10, "Libros Pepito", "+49000011003");
        BookRegistry b7 = new BookRegistry("A Clash of Kings", 15.8, 10, "Libros Pepito", "+49000011003");
        BookRegistry b8 = new BookRegistry("A Storm of Swords", 15.8, 10, "Libros Pepito", "+49000011003");
        BookRegistry b9 = new BookRegistry("A Feast for Crows", 15.8, 10, "Libros Pepito", "+49000011003");
        BookRegistry b10 = new BookRegistry("A Dance with Dragons", 15.8, 10, "Libros Pepito", "+49000011003");

        // We insert all the book registry
        Boolean b1Result = dbInsert(db, b1);
        Boolean b2Result = dbInsert(db, b2);
        Boolean b3Result = dbInsert(db, b3);
        Boolean b4Result = dbInsert(db, b4);
        Boolean b5Result = dbInsert(db, b5);
        Boolean b6Result = dbInsert(db, b6);
        Boolean b7Result = dbInsert(db, b7);
        Boolean b8Result = dbInsert(db, b8);
        Boolean b9Result = dbInsert(db, b9);
        Boolean b10Result = dbInsert(db, b10);

        // We check if all the insert succeeded
        if (b1Result && b2Result && b3Result && b4Result && b5Result && b6Result && b7Result && b8Result && b9Result && b10Result) {
            return true;
        }
        return false;
    }

    public Boolean dbInsert(SQLiteDatabase db, BookRegistry book) {

        // Creating a first entry instance, to apply it to the db
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.Supplies.PRODUCT_NAME, book.productName);
        values.put(DataBaseContract.Supplies.PRICE, book.price);
        values.put(DataBaseContract.Supplies.QUANTITY, book.quantity);
        values.put(DataBaseContract.Supplies.SUPPLIER_NAME, book.supplierName);
        values.put(DataBaseContract.Supplies.SUPPLIER_PHONE_NUMBER, book.supplierPhoneNumber);

        // Inserting the values into the db
        // this returns: "the row ID of the newly inserted row, or -1 if an error occurred"
        long newRowId = db.insert(DataBaseContract.Supplies.TABLE_NAME, null, values);

        if (newRowId == -1) {
            return false;
        }

        return true;
    }

    public ArrayList<BookRegistry> queryToArray(Cursor cursor) {
        ArrayList<BookRegistry> books = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                // Parsing values
                Integer id = cursor.getInt(0);
                String productName = cursor.getString(1);
                double price = cursor.getDouble(2);
                int quantity = cursor.getInt(3);
                String supplierName = cursor.getString(4);
                String supplierPhoneNumber = cursor.getString(5);
                BookRegistry book = new BookRegistry(productName, price, quantity, supplierName, supplierPhoneNumber);
                book.id = id;
                books.add(book);
            } while (cursor.moveToNext());
        }

        return books;
    }

    public void updateItemQuantitybyID(int id, int quantity) {
        // Getting a reference to the db for just for read
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor cursor;
        Cursor cursor;

        // Querying the database
        String query = "UPDATE " + DataBaseContract.Supplies.TABLE_NAME + " SET quantity=" + String.valueOf(quantity) + " WHERE _id=" + String.valueOf(id) + ";";
        Log.d("Query", query);
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();
        db.close();
    }

    public void removeItemByID(int id) {

        // Getting a reference to the db for just for read
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor cursor;
        Cursor cursor;

        // Querying the database
        String query = "DELETE FROM " + DataBaseContract.Supplies.TABLE_NAME + " WHERE _id=" + String.valueOf(id) + ";";
        Log.d("Query", query);
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();
        db.close();
    }

    public void removeAll() {

        // Getting a reference to the db for just for read
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor cursor;
        Cursor cursor;

        // Querying the database
        String query = "DELETE FROM " + DataBaseContract.Supplies.TABLE_NAME + ";";
        Log.d("Query", query);
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();
        db.close();
    }

    public Boolean addItem(int id, String productName, int quantity, double price, String supplierName, String supplierPhone) {

        SQLiteDatabase db = this.getWritableDatabase();
        // Create five book registry to populate our table
        BookRegistry book = new BookRegistry(productName, price, quantity, supplierName, supplierPhone);

        // We insert all the book registry
        Boolean result = dbInsert(db, book);

        db.close();

        // We check if all the insert succeeded
        return result;
    }

    public ArrayList<BookRegistry> dbQueryAll() {

        // Getting a reference to the db for just for read
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        // Querying the database
        String query = "SELECT * FROM "+ DataBaseContract.Supplies.TABLE_NAME + " ORDER BY productname ASC;";
        Log.d("Query", query);
        cursor =  db.rawQuery(query, null);
        ArrayList<BookRegistry> data = this.queryToArray(cursor);
        cursor.close();
        db.close();

        return data;
    }
}