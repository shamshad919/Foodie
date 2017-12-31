package com.example.shamshad.foodorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Zoro on 27-Dec-17.
 */

public class foodlist extends AppCompatActivity {
    ListView storeList;
    String[] stores = {"KFC", "McDonalds","Pizza Hut","Domino's Pizza","Burger King"};
    Integer[] images={R.drawable.kfc,R.drawable.mcdonalds,R.drawable.pizzahut,R.drawable.dominos,R.drawable.burgerking};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlist);



        /*ListAdapter storeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stores);*/
        ListAdapter storeAdapter = new listadapter(this,stores,images);

        storeList = (ListView) findViewById(R.id.storelist);
        storeList.setAdapter(storeAdapter);
    }

}
