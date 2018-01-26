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

public class food_viewHolder extends RecyclerView.ViewHolder{
    TextView textView;
    ImageView imageView;

    public food_viewHolder(View itemView) {
        super(itemView);
        textView=(TextView) itemView.findViewById(R.id.food_textview);
        imageView=(ImageView)itemView.findViewById(R.id.food_imageview);
    }

}
