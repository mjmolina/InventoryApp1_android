package com.example.mariajosemolina.bookstore1;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DetailView extends AppCompatActivity {

    private EditText bookEditText;
    private EditText priceEditText;
    private EditText quantityEditText;
    private EditText supplierNameEditText;
    private EditText supplierPhoneEditText;
    private Button buttonSave;
    private Button decreaseNumberOfBooksEditText;
    private Button increaseNumberOfBooksEditText;
    private FloatingActionButton deleteButton;
    private FloatingActionButton editButton;
    private FloatingActionButton callButton;
    private LinearLayout main_layout;
    private LinearLayout frame_delete;
    private LinearLayout frame_edit;
    private LinearLayout frame_call;

    Boolean editStatus = false;
    String phone = "0";
    Integer bookID;

    BookRegistry book;

    private void setEnabled(Boolean tmpBool) {

        if (tmpBool)
            buttonSave.setVisibility(View.VISIBLE);
        else
            buttonSave.setVisibility(View.INVISIBLE);

        buttonSave.setEnabled(tmpBool);
        bookEditText.setEnabled(tmpBool);
        priceEditText.setEnabled(tmpBool);
        quantityEditText.setEnabled(tmpBool);
        supplierNameEditText.setEnabled(tmpBool);
        supplierPhoneEditText.setEnabled(tmpBool);
        decreaseNumberOfBooksEditText.setEnabled(tmpBool);
        increaseNumberOfBooksEditText.setEnabled(tmpBool);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_editor);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                book = null;
            } else {
                book = (BookRegistry) extras.getSerializable("book");
            }

        } else {
            book = (BookRegistry) savedInstanceState.getSerializable("book");

        }

        // Getting the ID of the book, so if we want to edit,
        // we can find the element with the ID.
        bookID = book.id;

        // Find all relevant views that we will need to read user input from
        bookEditText= (EditText) findViewById(R.id.edit_book_title);
        priceEditText = (EditText) findViewById(R.id.price);
        quantityEditText = (EditText) findViewById(R.id.number);
        supplierNameEditText = (EditText) findViewById(R.id.supplier_name);
        supplierPhoneEditText = (EditText) findViewById(R.id.supplier_phone);
        buttonSave = (Button) findViewById(R.id.buttonSale);
        decreaseNumberOfBooksEditText = (Button) findViewById(R.id.buttonMinus);
        increaseNumberOfBooksEditText = (Button) findViewById(R.id.buttonPlus);
        deleteButton = (FloatingActionButton) findViewById(R.id.fab_delete);
        editButton = (FloatingActionButton) findViewById(R.id.fab_modify);
        callButton = (FloatingActionButton) findViewById(R.id.fab_phone);
        frame_delete = (LinearLayout) findViewById(R.id.frame_delete);
        frame_edit = (LinearLayout) findViewById(R.id.frame_edit);
        frame_call = (LinearLayout) findViewById(R.id.frame_call);

        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(bookEditText, InputMethodManager.SHOW_IMPLICIT);
        imm.showSoftInput(priceEditText, InputMethodManager.SHOW_IMPLICIT);
        imm.showSoftInput(supplierNameEditText, InputMethodManager.SHOW_IMPLICIT);
        imm.showSoftInput(supplierPhoneEditText, InputMethodManager.SHOW_IMPLICIT);

        bookEditText.setText(book.productName);
        priceEditText.setText(String.valueOf(book.price));
        quantityEditText.setText(String.valueOf(book.quantity));
        supplierNameEditText.setText(book.supplierName);
        supplierPhoneEditText.setText(book.supplierPhoneNumber);
        setEnabled(false);
        phone = supplierPhoneEditText.getText().toString();

        if (bookID == -1) {
            setEnabled(true);
            callButton.setEnabled(false);
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);

            frame_delete.setVisibility(View.INVISIBLE);
            frame_edit.setVisibility(View.INVISIBLE);
            frame_call.setVisibility(View.INVISIBLE);
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("Save", String.valueOf(bookID));
                Boolean valid = false;

                String bookTitle = bookEditText.getText().toString();
                Integer bookQuantity = Integer.parseInt(quantityEditText.getText().toString());
                Double bookPrice = Double.parseDouble(priceEditText.getText().toString());
                String supplierName = supplierNameEditText.getText().toString();
                String supplierNumber = supplierPhoneEditText.getText().toString();


                // Validating the data
                if (bookTitle.length() > 0) {
                    if (bookQuantity >= 0) {
                        if (bookPrice > 0) {
                            if (supplierName.length() > 0) {
                                if (supplierNumber.length() > 0) {
                                    valid = true;
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Invalid Supplier Phone number",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Invalid Supplier name",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Invalid Book price",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Invalid Books quantity",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Invalid Book name",
                            Toast.LENGTH_SHORT).show();
                }

                // If the validation passed, we update the DB entry
                if (valid) {
                    if (bookID != -1) {
                        DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
                        dbHelper.updateItemByID(book.id, bookTitle, bookQuantity, bookPrice, supplierName, supplierNumber);
                        Toast.makeText(getApplicationContext(),
                                "Data saved",
                                Toast.LENGTH_SHORT).show();

                        editStatus = false;
                        setEnabled(editStatus);
                    } else {
                        DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
                        Boolean result = dbHelper.addItem(book.id, bookTitle, bookQuantity, bookPrice, supplierName, supplierNumber);
                        if (result) {
                            Toast.makeText(getApplicationContext(),
                                    "Book registry added",
                                    Toast.LENGTH_SHORT).show();

                            editStatus = false;
                            setEnabled(editStatus);
                            callButton.setEnabled(true);
                            editButton.setEnabled(true);
                            deleteButton.setEnabled(true);

                            frame_delete.setVisibility(View.VISIBLE);
                            frame_edit.setVisibility(View.VISIBLE);
                            frame_call.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Error adding the new registry",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });

        decreaseNumberOfBooksEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("Minus", "menos");
                int quantity = Integer.parseInt(quantityEditText.getText().toString());
                quantity = quantity - 1;
                if (quantity < 0) {
                    quantity = 0;
                }
                quantityEditText.setText(String.valueOf(quantity));
            }
        });

        increaseNumberOfBooksEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("Plus", "mas");
                int quantity = Integer.parseInt(quantityEditText.getText().toString());
                quantity = quantity + 1;
                // Hopefully we will not have more than 100000 books.
                if (quantity > 100000) {
                    quantity = 100000;
                }
                quantityEditText.setText(String.valueOf(quantity));
            }
        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editStatus) {
                    editStatus = false;
                    Log.d("edit", "Deactivating");
                } else {
                    editStatus = true;
                    Log.d("edit", "Activating");
                }

               setEnabled(editStatus);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailView.this);


                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete this book?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
                        dbHelper.removeItemByID(book.id);
                        setEnabled(false);

                        editButton.setEnabled(false);
                        Toast.makeText(getApplicationContext(),
                                "Book Registry deleted",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
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

            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCall();
            }
        });

    }
    // The following code was extracted from
    // https://stackoverflow.com/questions/36990098/intent-call-action-doesnt-works-on-marshmallow
    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+phone)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }

}



