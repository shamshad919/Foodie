package com.example.shamshad.foodorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/*public class foodList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_restaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRef= FirebaseDatabase.getInstance().getReference("restaurants");

        FirebaseRecyclerAdapter<restaurant_details,restaurantviewHolder> ffirebaseRecyclerAdapter=new FirebaseRecyclerAdapter<restaurant_details, restaurantviewHolder>(restaurant_details.class,
                R.layout.restaurant_listrow,
                restaurantviewHolder.class,
                mRef) {
            @Override
            protected void populateViewHolder(final restaurantviewHolder viewHolder, restaurant_details model, int position) {
                viewHolder.textView.setText(model.name);
                Picasso.with(foodList.this).load(model.image).into(viewHolder.imageView);


            }
        };
        recyclerView.setAdapter(ffirebaseRecyclerAdapter);
    }
}*/

public class foodList extends AppCompatActivity{

    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodview);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        String childname="Dominos";

        if(b!=null)
        {
            childname =(String) b.getString("resname");
        }



        recyclerView= (RecyclerView) findViewById(R.id.recycler_foodview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRef= FirebaseDatabase.getInstance().getReference("restaurants").child(childname).child("foodlist");
        /*mRef= FirebaseDatabase.getInstance().getReference("restaurants");*/

        FirebaseRecyclerAdapter<food_details,food_viewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<food_details, food_viewHolder>(food_details.class,
                R.layout.foodrow,
                food_viewHolder.class,
                mRef) {
            @Override
            protected void populateViewHolder(final food_viewHolder viewHolder, food_details model, int position) {

                viewHolder.textView.setText(model.text);
                Picasso.with(foodList.this).load(model.image).into(viewHolder.imageView);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


}
