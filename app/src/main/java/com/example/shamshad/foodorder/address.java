package com.example.shamshad.foodorder;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class address extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    BottomNavigationView bottomNavigationView;
    FirebaseRecyclerAdapter<addressDetails, addressViewHolder> firebaseRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);
        getSupportActionBar().hide();



        recyclerView = (RecyclerView) findViewById(R.id.recycler_restaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("user").child(user.getUid()).child("address");

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<addressDetails, addressViewHolder>(addressDetails.class,
                R.layout.address_listrow,
                addressViewHolder.class,
                mRef) {
            @Override
            protected void populateViewHolder(final addressViewHolder viewHolder, addressDetails model, int position) {
                viewHolder.textView.setText(model.name);

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    /**
     * Created by shamshad on 25/3/18.
     */

    public static class addressDetails {
        String name;
        String image;
        String rating;

        public addressDetails() {
        }

        public addressDetails(String name, String image, String rating) {
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

    /**
     * Created by shamshad on 25/3/18.
     */

    public static class addressViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public addressViewHolder(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.address_textview_recycler);
        }
    }
}
