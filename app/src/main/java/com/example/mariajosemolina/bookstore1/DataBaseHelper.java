package com.example.mariajosemolina.bookstore1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {


    public DataBaseHelper(Context context) {
        super(context, DataBaseContract.DATABASE_NAME, null, DataBaseContract.DATABASE_VERSION);
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
}