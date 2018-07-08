package com.example.mariajosemolina.bookstore1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;



public class BookRegistryAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;

        Context context;
        TextView title;
        TextView author;
        TextView quantity;
        TextView price;
        Button button_sale;
        View itemView;
        ArrayList<BookRegistry> info;

        public BookRegistryAdapter(Context myContext, ArrayList<BookRegistry> myinformation) {
            this.info = myinformation;
            this.context = myContext;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (view == null) {
                itemView = inflater.inflate(R.layout.books_item, null);
            }
            else {
                itemView = view;
            }

            title = (TextView) itemView.findViewById(R.id.book_Title);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            price = (TextView) itemView.findViewById(R.id.price);
            button_sale = (Button)itemView.findViewById(R.id.button_sale);

            final BookRegistry book = this.info.get(i);
            title.setText(book.productName);
            price.setText(String.valueOf(book.price));
            quantity.setText(String.valueOf(book.quantity));

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailView.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("book", book);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            button_sale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("SALE", "Eliminando libro:"+book.productName);
                    if(context instanceof MainActivity){
                        int qty = book.quantity;
                        qty = qty - 1;
                        if (qty < 0)
                            qty = 0;
                        DataBaseHelper dbHelper = new DataBaseHelper(context);
                        dbHelper.updateItemQuantitybyID(book.id, qty);
                        ((MainActivity)context).onResume();
                    }

                }
            });

            return itemView;
        }

        @Override
        public int getCount() {
            return info.size();
        }

        @Override
        public Object getItem(int i) {
            return info.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
    }


