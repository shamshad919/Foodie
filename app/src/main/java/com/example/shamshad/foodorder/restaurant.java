package com.example.shamshad.foodorder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shamshad.foodorder.Interface.ItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class restaurant extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    BottomNavigationView bottomNavigationView;
    FirebaseRecyclerAdapter<restaurant_details, restaurantviewHolder> firebaseRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);
        getSupportActionBar().hide();



        recyclerView = (RecyclerView) findViewById(R.id.recycler_restaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mRef = FirebaseDatabase.getInstance().getReference("restaurants");



        bottomNavigationView= (BottomNavigationView) findViewById(R.id.navigation_view);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_explore :
                        return true;
                    case R.id.nav_cart:
                        startActivity(new Intent(restaurant.this,cart.class));
                        return true;
                    case R.id.nav_acccount:
                        startActivity(new Intent(restaurant.this,account_details.class));
                        return true;
                    default:
                        return  true;
                }

            }
        });

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<restaurant_details, restaurantviewHolder>(restaurant_details.class,
                R.layout.restaurant_listrow,
                restaurantviewHolder.class,
                mRef) {
            @Override
            protected void populateViewHolder(final restaurantviewHolder viewHolder, restaurant_details model, int position) {
                viewHolder.textView.setText(model.name);
                Glide.with(restaurant.this).load(model.image).into(viewHolder.imageView);
                viewHolder.rating.setText(model.rating);
                final restaurant_details restaurant_details = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onCLick(View view, int position, boolean isLongCLick) {
                        Intent intent = new Intent(restaurant.this, foodList.class);
                        intent.putExtra("restaurant_name", firebaseRecyclerAdapter.getRef(position).getKey());
                        Log.d("TAG", "" + firebaseRecyclerAdapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
