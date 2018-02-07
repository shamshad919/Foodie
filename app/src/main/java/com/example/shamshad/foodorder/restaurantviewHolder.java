package com.example.shamshad.foodorder;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shamshad.foodorder.Interface.ItemClickListener;
import com.example.shamshad.foodorder.R;

import static android.R.attr.id;


public class restaurantviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView textView;
    ImageView imageView;
    TextView rating;

    ItemClickListener itemClickListener;

    public restaurantviewHolder(View itemView) {
        super(itemView);
        textView=(TextView) itemView.findViewById(R.id.restaurant_textview);
        imageView=(ImageView)itemView.findViewById(R.id.restaurant_imageview);
        rating=(TextView)itemView.findViewById(R.id.rating_restaurant);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onCLick(view,getAdapterPosition(),false);
    }
}
