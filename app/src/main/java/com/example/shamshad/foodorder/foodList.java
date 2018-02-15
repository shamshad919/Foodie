package com.example.shamshad.foodorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.shamshad.foodorder.Interface.ItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class foodList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    String restaurant_name="";
    Button quantity;
    FirebaseRecyclerAdapter<food_list_details,food_viewHolder>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodview);

        mRef=FirebaseDatabase.getInstance().getReference("restaurants");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_foodview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(getIntent() != null){
            restaurant_name=getIntent().getStringExtra("restaurant_name");
        }
        if(!restaurant_name.isEmpty() && restaurant_name != null){
            loadlistfood(restaurant_name);
        }
        loadlistfood(restaurant_name);
    }

    private void loadlistfood(final String restaurant_name) {
        adapter=new FirebaseRecyclerAdapter<food_list_details, food_viewHolder>(food_list_details.class,
                R.layout.food_listrow,
                food_viewHolder.class,
                mRef.child(restaurant_name).child("foodlist")) {
            @Override
            protected void populateViewHolder(food_viewHolder viewHolder, final food_list_details model, int position) {
                viewHolder.textView.setText(model.text);
                viewHolder.priceview.setText(model.price);
                Glide.with(foodList.this).load(model.image).into(viewHolder.imageView);
                final food_list_details food_details=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onCLick(View view, int position, boolean isLongCLick) {
                        Intent fooddetails=new Intent(foodList.this,fooddetails.class);
                        fooddetails.putExtra("Foodid",adapter.getRef(position).getKey());
                        startActivity(fooddetails);
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }

}
