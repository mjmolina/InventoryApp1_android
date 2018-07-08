package com.example.mariajosemolina.bookstore1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView items;
    BookRegistryAdapter adapter;
    ArrayList<BookRegistry> books = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);

        // This line will remove the db completely
        // Using it just for testing the creation
        this.deleteDatabase(DataBaseContract.DATABASE_NAME);

        // db initialization
        dbHelper.dbInit();

        items = (ListView) findViewById(R.id.list);
        items.setEmptyView(findViewById(R.id.empty_list_item));

        books = dbHelper.dbQueryAll();
        adapter  = new BookRegistryAdapter(this, books);
        items.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);
        Log.d("nResume", "Resuming");
        books = dbHelper.dbQueryAll();
        adapter  = new BookRegistryAdapter(this, books);
        items.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_book_catalog, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.add_book:
                Intent intent = new Intent(this, DetailView.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", new BookRegistry());
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.delete_books:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete all the books?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
                        dbHelper.removeAll();
                        Toast.makeText(getApplicationContext(),
                                "Books Registries deleted",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        onResume();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
