package com.example.shamshad.foodorder;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Zoro on 28-Dec-17.
 */

public class listadapter extends ArrayAdapter<String>{


    private String [] stores;
    private Integer[] images;
    private Activity context;

    public listadapter(Activity context, String[] stores,Integer[] images) {
        super(context,R.layout.listrow,stores);

        this.context=context;
        this.stores=stores;
        this.images=images;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater foodinflater=LayoutInflater.from(getContext());
        View newview=foodinflater.inflate(R.layout.listrow,parent,false);


        TextView restaurantname=(TextView) newview.findViewById(R.id.restaurant_name);
        ImageView photo=(ImageView)newview.findViewById(R.id.imagesRestaurant);

        restaurantname.setText(stores[position]);
        photo.setImageResource(images[position]);
        return newview;
    }
}
