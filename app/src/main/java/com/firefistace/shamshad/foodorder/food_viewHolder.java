package com.firefistace.shamshad.foodorder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firefistace.shamshad.foodorder.Interface.ItemClickListener;


public class food_viewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    ImageView imageView;
    TextView priceview;
    ItemClickListener itemClickListener;
    ElegantNumberButton numberButton_foodlist;
    Button add_button;



    public food_viewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.food_textview);
        imageView = (ImageView) itemView.findViewById(R.id.food_imageview);
        priceview = (TextView) itemView.findViewById(R.id.food_price);
        numberButton_foodlist= (ElegantNumberButton) itemView.findViewById(R.id.quantity_button_foodlist);
        add_button=(Button)itemView.findViewById(R.id.add_button);

    }



}
