package com.example.shamshad.foodorder;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


public class foodList extends AppCompatActivity implements View.OnClickListener {
    private  TextView cart_count;
    private  TextView total_price;
    private TextView min_price;
    private  Button add_cart;
    private Button view_cart;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    String restaurant_name = "";
    Button quantity;
    ImageView restaurant_image_foodlist;
    TextView restaurant_name_foodlist;
    FirebaseRecyclerAdapter<food_list_details, food_viewHolder> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodview);
        getSupportActionBar().hide();

        min_price= (TextView) findViewById(R.id.min_order);
        add_cart= (Button) findViewById(R.id.add_cart_button);
        bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottom_navigation_cart);
        restaurant_name_foodlist = (TextView) findViewById(R.id.name_restaurant_foodlist);
        restaurant_image_foodlist = (ImageView) findViewById(R.id.image_restaurant_foodlist);
        mRef = FirebaseDatabase.getInstance().getReference("restaurants");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_foodview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cart_count= (TextView) findViewById(R.id.cart_count);
        total_price= (TextView) findViewById(R.id.total_price);
        view_cart=(Button) findViewById(R.id.add_cart_button);

        view_cart.setOnClickListener(this);

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
                min_price.setText("Min order:"+restaurant.getMin_order());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user =mAuth.getCurrentUser();
        final String uid=user.getUid();
        adapter = new FirebaseRecyclerAdapter<food_list_details, food_viewHolder>(food_list_details.class,
                R.layout.food_listrow,
                food_viewHolder.class,
                mRef.child(restaurant_name).child("foodlist")) {
            @Override
            protected void populateViewHolder(final food_viewHolder viewHolder, final food_list_details model, final int position) {
                viewHolder.textView.setText(model.text);
                viewHolder.priceview.setText(model.price);
                Glide.with(foodList.this).load(model.image).into(viewHolder.imageView);
                final food_list_details food_details = model;

                viewHolder.add_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.add_button.setVisibility(View.GONE);
                        viewHolder.numberButton_foodlist.setVisibility(View.VISIBLE);
                        viewHolder.numberButton_foodlist.setNumber(String.valueOf(1));
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        final String count=viewHolder.numberButton_foodlist.getNumber();
                        final String foodidkey=adapter.getRef(position).getKey();
                        cart_count.setText(viewHolder.numberButton_foodlist.getNumber()+" Items");
                        final DatabaseReference cartref=FirebaseDatabase.getInstance().getReference("user").child(uid).child("cart").child(foodidkey);
                        cartref.child("food_id").setValue(adapter.getRef(position).getKey());
                        cartref.child("quantity").setValue(count);
                        cartref.child("text").setValue(model.text);

                        DatabaseReference priceref=FirebaseDatabase.getInstance().getReference("food_list").child(foodidkey);
                        priceref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String price= (String) dataSnapshot.child("price").getValue();
                                String foodname= (String) dataSnapshot.child("text").getValue();
                                int total=Integer.parseInt(price)*Integer.parseInt(count);
                                total_price.setText("Price: "+total);
                                cartref.child("price").setValue(price);



                                /*if(foodidorder.contains(foodidkey)){
                                    priceorder.set(foodidorder.indexOf(foodidkey), String.valueOf(total));
                                    qty_orders.set(foodidorder.indexOf(foodidkey),String.valueOf(count));

                                }
                                else{
                                    foodidorder.add(foodidkey);
                                    priceorder.add(String.valueOf(total));
                                    foodnametext.add(foodname);
                                    qty_orders.add(count);

                                }*/

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        Toast.makeText(foodList.this, "Added to Cart Successfullly", Toast.LENGTH_SHORT).show();
                    }
                });
             viewHolder.numberButton_foodlist.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                 @Override
                 public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                     if(viewHolder.numberButton_foodlist.getNumber()==String.valueOf(0)){
                         final String foodidkey=adapter.getRef(position).getKey();
                         viewHolder.add_button.setVisibility(View.VISIBLE);
                         viewHolder.numberButton_foodlist.setVisibility(View.GONE);
                         bottomNavigationView.setVisibility(View.GONE);
                         DatabaseReference cartref=FirebaseDatabase.getInstance().getReference("user").child(uid).child("cart").child(adapter.getRef(position).getKey());
                         cartref.removeValue();

                         /*priceorder.remove(foodidorder.indexOf(foodidkey));
                         foodnametext.remove(foodidorder.indexOf(foodidkey));
                         qty_orders.remove(foodidorder.indexOf(foodidkey));
                         foodidorder.remove(foodidkey);*/

                     }
                     else{
                         final String foodidkey=adapter.getRef(position).getKey();
                         final String count=viewHolder.numberButton_foodlist.getNumber();
                         cart_count.setText(viewHolder.numberButton_foodlist.getNumber()+" Items");
                         final DatabaseReference cartref=FirebaseDatabase.getInstance().getReference("user").child(uid).child("cart").child(foodidkey);
                         cartref.child("food_id").setValue(adapter.getRef(position).getKey());
                         cartref.child("quantity").setValue(count);
                         cartref.child("text").setValue(model.text);


                         DatabaseReference priceref=FirebaseDatabase.getInstance().getReference("food_list").child(foodidkey);
                         priceref.addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(DataSnapshot dataSnapshot) {
                                String price= (String) dataSnapshot.child("price").getValue();
                                 String foodname= (String) dataSnapshot.child("text").getValue();
                                 cartref.child("price").setValue(model.price);
                                 int total=Integer.parseInt(price)*Integer.parseInt(count);
                                 total_price.setText("Price: "+total);
                                 cartref.child("price").setValue(price);



                                 /*if(foodidorder.contains(foodidkey)){
                                     priceorder.set(foodidorder.indexOf(foodidkey), String.valueOf(total));
                                     qty_orders.set(foodidorder.indexOf(foodidkey),String.valueOf(count));
                                 }
                                 else{
                                     foodidorder.add(foodidkey);
                                     priceorder.add(String.valueOf(total));
                                     foodnametext.add(foodname);
                                     qty_orders.add(String.valueOf(count));
                                 }*/


                             }

                             @Override
                             public void onCancelled(DatabaseError databaseError) {

                             }
                         });

                         Toast.makeText(foodList.this, "Added to Cart Successfullly", Toast.LENGTH_SHORT).show();
                     }
                 }
             });


                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent fooddetails = new Intent(foodList.this, fooddetails.class);
                        fooddetails.putExtra("Foodid", adapter.getRef(position).getKey());
                        startActivity(fooddetails);
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v==view_cart){
            Intent cart=new Intent(foodList.this,cart.class);
            startActivity(cart);
        }
    }
}
