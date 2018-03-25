package com.example.shamshad.foodorder;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shamshad.foodorder.Interface.ItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class address extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference addressRef;
    private FirebaseRecyclerAdapter <addressDetails,addressViewHolder> firebaseRecyclerAdapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_address);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();
        addressRef= FirebaseDatabase.getInstance().getReference("user").child(uid).child("address");
        firebaseRecyclerAdapter1 = new FirebaseRecyclerAdapter<addressDetails, addressViewHolder>(addressDetails.class,
                R.layout.address_listrow,
                addressViewHolder.class,
                addressRef) {
            @Override
            protected void populateViewHolder(addressViewHolder viewHolder, addressDetails model, int position) {
                viewHolder.textView.setText(model.text);
            }


        };
        recyclerView.setAdapter(firebaseRecyclerAdapter1);
    }
}
