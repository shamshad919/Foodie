package com.firefistace.shamshad.foodorder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


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
