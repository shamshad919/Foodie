package com.example.shamshad.foodorder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Zoro on 14-Feb-18.
 */

public class cartlistadapter extends ArrayAdapter<String> {

    Context context;
    String[] foodsincart;
    String[] priceincart;
    String[] qtys;
    cartlistadapter(Context context,String[] foodsincart,String[] priceincart,String[] qtys ){
        super(context,R.layout.cartlistrow,foodsincart);
        this.context=context;
        this.foodsincart=foodsincart;
        this.priceincart=priceincart;
        this.qtys=qtys;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myinflater=LayoutInflater.from(getContext());
        View customView=myinflater.inflate(R.layout.cartlistrow,parent,false);
        TextView food_in_cartlist=(TextView)customView.findViewById(R.id.food_in_cartlist);
        TextView price_in_cartlist=(TextView)customView.findViewById(R.id.price_in_cartlist);
        TextView qty_in_cartlist=(TextView)customView.findViewById(R.id.qty_textview);
        food_in_cartlist.setText(foodsincart[position]);
        price_in_cartlist.setText(priceincart[position]);
        qty_in_cartlist.setText(qtys[position]);

        return customView;
    }
}
