package com.example.shamshad.foodorder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shamshad.foodorder.R;

/**
 * Created by shamshad on 16/1/18.
 */

public class restaurantviewHolder extends RecyclerView.ViewHolder{
    TextView textView;
    ImageView imageView;

    public restaurantviewHolder(View itemView) {
        super(itemView);
        textView=(TextView) itemView.findViewById(R.id.restaurant_textview);
        imageView=(ImageView)itemView.findViewById(R.id.restaurant_imageview);
    }
}
