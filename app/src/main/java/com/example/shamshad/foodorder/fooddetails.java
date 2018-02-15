package com.example.shamshad.foodorder;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.mode;

public class fooddetails extends AppCompatActivity {

    TextView foodname, foodprice, fooddescription;
    ImageView foodimage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ElegantNumberButton numberButton;

    String foodid = "";
    DatabaseReference food_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooddetails);

        food_list = FirebaseDatabase.getInstance().getReference("food_list");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        foodname = (TextView) findViewById(R.id.food_name);
        foodprice = (TextView) findViewById(R.id.food_price);
        fooddescription = (TextView) findViewById(R.id.food_description);
        foodimage = (ImageView) findViewById(R.id.image_food);

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

    private void getFoodDetails(String foodid) {

        food_list.child(foodid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                food_list_details food = dataSnapshot.getValue(food_list_details.class);

                foodprice.setText(food.getPrice());
                foodname.setText(food.getText());
                Glide.with(getBaseContext()).load(food.getImage()).into(foodimage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
