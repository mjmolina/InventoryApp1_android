package com.example.mariajosemolina.bookstore1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Boolean dbInsert(SQLiteDatabase db, BookRegistry book) {

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

    private Boolean dbPopulate(SQLiteDatabase db) {

        // Create five book registry to populate our table
        BookRegistry b1 = new BookRegistry("The name of the wind", 9.4, 50, "Thalia BookStore", "+49234201112");
        BookRegistry b2 = new BookRegistry("The wise Man's Fear", 12.9, 25, "Thalia BookStore", "+49234201112");
        BookRegistry b3 = new BookRegistry("The three body problem", 11.0, 45, "Thalia BookStore", "+49234201112");
        BookRegistry b4 = new BookRegistry("The dark forest", 15.2, 15, "Karstadt", "+49211113112");
        BookRegistry b5 = new BookRegistry("Death's End", 25.2, 15, "Karstadt", "+49211113112");

        // We insert all the book registry
        Boolean b1Result = dbInsert(db, b1);
        Boolean b2Result = dbInsert(db, b2);
        Boolean b3Result = dbInsert(db, b3);
        Boolean b4Result = dbInsert(db, b4);
        Boolean b5Result = dbInsert(db, b5);

        // We check if all the insert succeeded
        if (b1Result && b2Result && b3Result && b4Result && b5Result) {
            return true;
        }
        return false;
    }

    private void dbInit() {
        // Data base creation

        // This will instantiate the Helper class we wrote
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);
        // This will get a reference to the db, and if it does not exist, the db will
        // be created.
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Check if the table has data, if there is nothing, we populate the db
        Cursor cursor = db.rawQuery("SELECT * FROM "+DataBaseContract.Supplies.TABLE_NAME, null);
        if (cursor == null || cursor.getCount() <= 0 ) {
            // Adding entries to the db if there was no
            Boolean result = dbPopulate(db);

            if (!result) {
                Toast.makeText(getApplicationContext(),
                        "An error occurred during insertion to the DB",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "The DB insert was executed successful",
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Closing the connection to the db since it was in Writing mode
        db.close();
    }

    // Method to get the items that come from the same supplier
    private Cursor dbQuerySupplier (SQLiteDatabase db, String supplier) {
        // Querying the database
        String query = "SELECT * FROM "+ DataBaseContract.Supplies.TABLE_NAME+" WHERE "+ DataBaseContract.Supplies.SUPPLIER_NAME+" = '"+supplier+"'";
        Log.d("Query", query);

        return db.rawQuery(query, null);

    }

    // Method to get the items that contains a certain name
    private Cursor dbQueryProductName (SQLiteDatabase db, String name) {
        // Querying the database
        String query = "SELECT * FROM "+ DataBaseContract.Supplies.TABLE_NAME+" WHERE "+ DataBaseContract.Supplies.PRODUCT_NAME+" LIKE '%"+name+"%'";
        Log.d("Query", query);

        return db.rawQuery(query, null);
    }


    private void printQueryResult(Cursor cursor) {
        Log.d("Cursor columns", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()){
            do {
                // Passing values
                Integer id = cursor.getInt(0);
                String productName = cursor.getString(1);
                double price = cursor.getDouble(2);
                double quantity = cursor.getDouble(3);
                String supplierName = cursor.getString(4);
                String supplierPhoneNumber = cursor.getString(5);

                Log.d("Result", id+", "+productName+", "+price+", "+quantity+", "+supplierName+", "+supplierPhoneNumber);

            } while(cursor.moveToNext());
        }
    }

    private void dbQuery() {
        // Getting reference to the Helper
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);

        // Getting a reference to the db for just for read
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        Cursor cursor;

        // Querying supplier
        // Getting all the supplier names that are exactly "Thalia BookStore"
        cursor = dbQuerySupplier(db, "Thalia BookStore");
        printQueryResult(cursor);
        cursor.close();

        // Querying product name
        // Getting all the product names that contain the word "forest"
        cursor = dbQueryProductName(db, "forest");
        printQueryResult(cursor);
        cursor.close();

        db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This line will remove the db completely
        // Using it just for testing the creation
        //this.deleteDatabase(DataBaseContract.DATABASE_NAME);

        // db initialization
        dbInit();

        // Starting query using two different methods  over our testing data
        dbQuery();
    }
}
