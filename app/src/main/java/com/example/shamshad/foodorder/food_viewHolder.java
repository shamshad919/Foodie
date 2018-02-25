package com.example.shamshad.foodorder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.shamshad.foodorder.Interface.ItemClickListener;
import com.example.shamshad.foodorder.R;


public class food_viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView textView;
    ImageView imageView;
    TextView priceview;
    ItemClickListener itemClickListener;
    ElegantNumberButton numberButton_foodlist;
    Button add_button;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public food_viewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.food_textview);
        imageView = (ImageView) itemView.findViewById(R.id.food_imageview);
        priceview = (TextView) itemView.findViewById(R.id.food_price);
        numberButton_foodlist= (ElegantNumberButton) itemView.findViewById(R.id.quantity_button_foodlist);
        add_button=(Button)itemView.findViewById(R.id.add_button);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onCLick(view, getAdapterPosition(), false);
    }

}
