package com.example.mariajosemolina.tourguideapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class BookRegestryAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;

    Context context;
    ArrayList<Location> loc;
    ImageView image;
    TextView title;
    TextView subtitle;
    View itemView;

    public BookRegestryAdapter(Context myContext, ArrayList<Location> myLocation) {
        this.context = myContext;
        this.loc = myLocation;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            itemView = inflater.inflate(R.layout.viewitem, null);
        }
        else {
            itemView = view;
        }

        image = itemView.findViewById(R.id.photo);
        title = itemView.findViewById(R.id.title);

        image.setImageResource(loc.get(i).Image);
        title.setText(loc.get(i).Name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LocationView.class);
                intent.putExtra("locationName", loc.get(i).Name);
                context.startActivity(intent);

            }
        });

        return itemView;
    }

    @Override
    public int getCount() {
        return loc.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
