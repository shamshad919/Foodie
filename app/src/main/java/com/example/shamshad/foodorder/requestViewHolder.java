package com.example.shamshad.foodorder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.shamshad.foodorder.Interface.ItemClickListener;

/**
 * Created by Zoro on 23-Apr-18.
 */

public class requestViewHolder extends RecyclerView.ViewHolder implements ItemClickListener{

    TextView textView;
    ItemClickListener itemClickListener;
    public requestViewHolder(View itemView) {
        super(itemView);
        textView=(TextView) itemView.findViewById(android.R.id.text1);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }
    @Override
    public void onCLick(View view, int position, boolean isLongCLick) {
        itemClickListener.onCLick(view,getAdapterPosition(),false);

    }
}
