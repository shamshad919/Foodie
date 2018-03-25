package com.example.shamshad.foodorder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Zoro on 12-Mar-18.
 */

public class cartViewHolder extends RecyclerView.ViewHolder {

    TextView foodnametextView;
    TextView quantitytextView;
    TextView pricetextView;
    public cartViewHolder(View itemView) {
        super(itemView);
        foodnametextView=itemView.findViewById(R.id.food_in_cartlist);
        quantitytextView=itemView.findViewById(R.id.qty_textview);
        pricetextView=itemView.findViewById(R.id.price_in_cartlist);
    }


}
