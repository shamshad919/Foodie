package com.firefistace.shamshad.foodorder;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fooddetails extends AppCompatActivity {

    TextView foodname, foodprice, fooddescription;
    ImageView foodimage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private ElegantNumberButton numberButton;
    Button cart;

    String foodid = "";
    DatabaseReference food_list;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooddetails);
        getSupportActionBar().hide();

        food_list = FirebaseDatabase.getInstance().getReference("food_list");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        foodname = (TextView) findViewById(R.id.food_name);
        foodprice = (TextView) findViewById(R.id.food_price);
        fooddescription = (TextView) findViewById(R.id.food_description);
        foodimage = (ImageView) findViewById(R.id.image_food);
        cart = (Button) findViewById(R.id.cart_button);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleColor(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.Collapsedappbar);

        if (getIntent() != null) {
            foodid = getIntent().getStringExtra("Foodid");
        }
        if (!foodid.isEmpty()) {
            getFoodDetails(foodid);
        }

    }

    private void getFoodDetails(final String foodid) {

        food_list.child(foodid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final food_list_details food = dataSnapshot.getValue(food_list_details.class);


                foodprice.setText(food.getPrice());
                foodname.setText(food.getText());
                Glide.with(getBaseContext()).load(food.getImage()).into(foodimage);
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user =mAuth.getCurrentUser();
                final String uid=user.getUid();
                cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String quantity = numberButton.getNumber();
                        final DatabaseReference cartref=FirebaseDatabase.getInstance().getReference("user").child(uid).child("cart").push();
                        cartref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                cartref.child("foodid").setValue(foodid);
                                cartref.child("quantity").setValue(quantity);
                                Toast.makeText(fooddetails.this, "Added to Cart Successfullly", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
