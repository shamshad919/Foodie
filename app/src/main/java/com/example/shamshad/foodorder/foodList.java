package com.example.shamshad.foodorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class foodList extends AppCompatActivity{

    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodview);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();
        String childname="Dominos";

        if(b!=null)
        {
            childname =(String) b.getString("resname");
        }

        recyclerView= (RecyclerView) findViewById(R.id.recycler_foodview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRef= FirebaseDatabase.getInstance().getReference("restaurants").child(childname).child("foodlist");
       
        FirebaseRecyclerAdapter<food_details,food_viewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<food_details, food_viewHolder>(food_details.class,
                R.layout.food_listrow,
                food_viewHolder.class,
                mRef) {
            @Override
            protected void populateViewHolder(final food_viewHolder viewHolder, food_details model, int position) {
                viewHolder.priceview.setText("Price:$"+model.getPrice());
                viewHolder.textView.setText(model.getText());
                Glide.with(foodList.this).load(model.getImage()).into(viewHolder.imageView);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
