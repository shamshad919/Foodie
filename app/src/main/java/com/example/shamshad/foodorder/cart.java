package com.example.shamshad.foodorder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class cart extends AppCompatActivity {

    private Button place_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        getSupportActionBar().hide();

        TextView totprice_value = (TextView) findViewById(R.id.total_price_cart_value);
        place_order = (Button) findViewById(R.id.place_order_btn);
        final ListView cartlistview = (ListView) findViewById(R.id.cartlistview);


        ArrayList<String> foodid_order = new ArrayList<>();
        ArrayList<String> price_order = new ArrayList<>();
        ArrayList<String> food_names_order = new ArrayList<>();
        ArrayList<String> qty_order = new ArrayList<>();



        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        foodid_order = (ArrayList<String>) args.getSerializable("foodid_order");
        price_order = (ArrayList<String>) args.getSerializable("price_order");
        food_names_order = (ArrayList<String>) args.getSerializable("food_names_order");
        qty_order = (ArrayList<String>) args.getSerializable("qty_order");


        final int itemcount = foodid_order.size();
        String[] foodsincart = new String[itemcount];
        String[] priceincart = new String[itemcount];
        String[] qtys = new String[itemcount];
        qtys = qty_order.toArray(qtys);
        priceincart = price_order.toArray(priceincart);
        foodsincart = food_names_order.toArray(foodsincart);

        place_order.setOnClickListener(new BlakesClickListener(priceincart,this));

        int i, totprice = 0;
        for (i = 0; i < itemcount; i++) {
            totprice = totprice + (Integer.parseInt(priceincart[i]));
        }
        totprice_value.setText(String.valueOf(totprice));


        ListAdapter mycartlistadapter = new cartlistadapter(this, foodsincart, priceincart, qtys);
        cartlistview.setAdapter(mycartlistadapter);


    }


    public class BlakesClickListener implements View.OnClickListener {

        String[] priceincart;
        Context context;

        public BlakesClickListener(String[] priceincart,Context context) {
            this.priceincart = priceincart;
            this.context=context;
        }

        @Override
        public void onClick(View view) {
            if (view == place_order) {
                Toast.makeText(context,"YAYA"+priceincart[0],Toast.LENGTH_SHORT).show();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final DatabaseReference orderref = FirebaseDatabase.getInstance().getReference("requests").child(user.getUid());
                orderref.child("name").setValue(priceincart[0]);


            }
        }

    }
}


