package com.example.shamshad.foodorder;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shamshad.foodorder.Interface.ItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class restaurant extends AppCompatActivity {

    private Context context=restaurant.this;

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
        navigationview_helper.enableNavigationView(context,bottomNavigationView);

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

    public static class restaurant_details {
        String name;
        String image;
        String rating;

        public restaurant_details() {
        }

        public restaurant_details(String name, String image, String rating) {
            this.name = name;
            this.image = image;
            this.rating = rating;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }
    }

    public static class restaurantviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        ImageView imageView;
        TextView rating;

        ItemClickListener itemClickListener;

        public restaurantviewHolder(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.restaurant_textview);
            imageView=(ImageView)itemView.findViewById(R.id.restaurant_imageview);
            rating=(TextView)itemView.findViewById(R.id.rating_restaurant);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onCLick(view,getAdapterPosition(),false);
        }
    }
}
