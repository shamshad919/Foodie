package com.example.shamshad.foodorder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;





public class cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        ListView cartlistview=(ListView)findViewById(R.id.cartlistview);
        String[] foodsincart={"burger","pizza","chicken fry"};
        String[] priceincart={"50$","100$","2$"};

        ListAdapter mycartlistadapter=new cartlistadapter(this,foodsincart,priceincart);
        cartlistview.setAdapter(mycartlistadapter);


    }
}
