package com.example.shamshad.foodorder;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shamshad.foodorder.R;


public class restaurantviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView textView;
    ImageView imageView;
    Context mcontext;

    public restaurantviewHolder(View itemView) {
        super(itemView);
        textView=(TextView) itemView.findViewById(R.id.restaurant_textview);
        imageView=(ImageView)itemView.findViewById(R.id.restaurant_imageview);
        mcontext=itemView.getContext();
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mcontext, foodList.class);
        intent.putExtra("resname",textView.getText());
        mcontext.startActivity(intent);
    }
}
