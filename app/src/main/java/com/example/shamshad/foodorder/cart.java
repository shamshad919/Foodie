package com.example.shamshad.foodorder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        final ListView cartlistview=(ListView)findViewById(R.id.cartlistview);

        ArrayList<String> foodid_order=new ArrayList<>();
        ArrayList<String> price_order=new ArrayList<>();
        ArrayList<String> food_names_order=new ArrayList<>();


        if (getIntent() != null) {
            Intent intent = getIntent();
            Bundle args = intent.getBundleExtra("BUNDLE");
            foodid_order = (ArrayList<String>) args.getSerializable("foodid_order");
            price_order = (ArrayList<String>) args.getSerializable("price_order");
            food_names_order=(ArrayList<String>) args.getSerializable("food_names_order");
        }

        final int itemcount=foodid_order.size();
        String[] foodsincart=new String[itemcount];
        String[] priceincart=new String[itemcount];
        priceincart=price_order.toArray(priceincart);
        foodsincart=food_names_order.toArray(foodsincart);
        /*foodsincart=foodid_order.toArray(foodsincart);*/

        /*DatabaseReference foodlistdatabase=FirebaseDatabase.getInstance().getReference("foodlist");
        int i;
        for(i=0;i<itemcount;i++){
            final int finalI = i;
            foodlistdatabase.child(foodid_order.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    foodsincart[finalI]= (String) dataSnapshot.child("text").getValue();

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }*/


        ListAdapter mycartlistadapter=new cartlistadapter(this,foodsincart,priceincart);
        cartlistview.setAdapter(mycartlistadapter);


    }
}
