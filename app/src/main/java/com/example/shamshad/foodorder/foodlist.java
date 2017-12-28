package com.example.shamshad.foodorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Zoro on 27-Dec-17.
 */

public class foodlist extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlist);


        String[] stores = {"kfc", "pizzahut", "usmans", "shamz", "blake"};
        ListAdapter storeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stores);

        ListView storeList = (ListView) findViewById(R.id.storelist);
        storeList.setAdapter(storeAdapter);
    }

}
