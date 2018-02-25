package com.example.shamshad.foodorder;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.shamshad.foodorder.Interface.ItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class foodList extends AppCompatActivity {


    private RecyclerView recyclerView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private DatabaseReference mRef;
    String restaurant_name = "";
    Button quantity;
    ImageView restaurant_image_foodlist;
    TextView restaurant_name_foodlist;
    FirebaseRecyclerAdapter<food_list_details, food_viewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodview);

        
        restaurant_name_foodlist = (TextView) findViewById(R.id.name_restaurant_foodlist);
        restaurant_image_foodlist = (ImageView) findViewById(R.id.image_restaurant_foodlist);
        mRef = FirebaseDatabase.getInstance().getReference("restaurants");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_foodview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_foodlist);
        collapsingToolbarLayout.setExpandedTitleColor(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.Collapsedappbar);



        if (getIntent() != null) {
            restaurant_name = getIntent().getStringExtra("restaurant_name");
        }
        if (!restaurant_name.isEmpty() && restaurant_name != null) {
            loadlistfood(restaurant_name);

        }

    }

    private void loadlistfood(final String restaurant_name) {

        mRef.child(restaurant_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final restaurant_details restaurant = dataSnapshot.getValue(restaurant_details.class);
                restaurant_name_foodlist.setText(restaurant.getName());
                Glide.with(getBaseContext()).load(restaurant.getImage()).into(restaurant_image_foodlist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new FirebaseRecyclerAdapter<food_list_details, food_viewHolder>(food_list_details.class,
                R.layout.food_listrow,
                food_viewHolder.class,
                mRef.child(restaurant_name).child("foodlist")) {
            @Override
            protected void populateViewHolder(final food_viewHolder viewHolder, final food_list_details model, int position) {
                viewHolder.textView.setText(model.text);
                viewHolder.priceview.setText(model.price);
                Glide.with(foodList.this).load(model.image).into(viewHolder.imageView);
                final food_list_details food_details = model;
                viewHolder.add_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.add_button.setVisibility(View.GONE);
                        viewHolder.numberButton_foodlist.setVisibility(View.VISIBLE);
                    }
                });
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onCLick(View view, int position, boolean isLongCLick) {
                        Intent fooddetails = new Intent(foodList.this, fooddetails.class);
                        fooddetails.putExtra("Foodid", adapter.getRef(position).getKey());
                        startActivity(fooddetails);
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }

}
