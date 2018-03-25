package com.example.shamshad.foodorder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shamshad on 25/3/18.
 */

public class addressViewHolder extends RecyclerView.ViewHolder{

    TextView textView;
    public addressViewHolder(View itemView) {
        super(itemView);
        textView=(TextView) itemView.findViewById(R.id.address_textview_recycler);
    }
}
