package com.example.shamshad.foodorder;

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




    public listadapter(@NonNull Context context, String[] stores) {
        super(context,R.layout.listrow,stores);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater foodinflater=LayoutInflater.from(getContext());
        View newview=foodinflater.inflate(R.layout.listrow,parent,false);

        String restaurant=getItem(position);
        TextView restaurantname=(TextView) newview.findViewById(R.id.restaurant_name);
        ImageView photo=(ImageView)newview.findViewById(R.id.girlpic);

        restaurantname.setText(restaurant);
        photo.setImageResource(R.drawable.b6eb5c90231c8aecb1cc75e4f11adbaa);
        return newview;
    }
}
