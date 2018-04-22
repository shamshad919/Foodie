package com.example.shamshad.foodorder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.shamshad.foodorder.Interface.ItemClickListener;

import org.w3c.dom.Text;


public class order_viewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    TextView textView2;
    TextView priceview;


    public order_viewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.order_text);
        textView2 = (TextView) itemView.findViewById(R.id.order_quantity);
        priceview = (TextView) itemView.findViewById(R.id.order_price);

    }



}
