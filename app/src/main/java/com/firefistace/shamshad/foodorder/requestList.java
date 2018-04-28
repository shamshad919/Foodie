package com.firefistace.shamshad.foodorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class requestList extends AppCompatActivity {


    private String uid;
    private RecyclerView request_recyclerView;
    private TextView textViewaddr;
    private ImageView deliverdimage;
    FirebaseRecyclerAdapter <requestOrder ,requestViewHolder> requestadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_list);

        textViewaddr= (TextView) findViewById(R.id.request_addr_textview);
        deliverdimage= (ImageView) findViewById(R.id.delivered_button);
        deliverdimage.setVisibility(View.INVISIBLE);
        textViewaddr.setText("No Orders Pending");
        request_recyclerView= (RecyclerView) findViewById(R.id.request_recyclerview);
        request_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        request_recyclerView.setHasFixedSize(true);

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user").child(uid);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String reqaddr= (String) dataSnapshot.child("single_addr").getValue();
                if(reqaddr!=null) {
                    textViewaddr.setText(reqaddr);
                    textViewaddr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(requestList.this, order.class);
                            intent.putExtra("isRequest", 1);
                            startActivity(intent);
                        }
                    });
                    deliverdimage.setVisibility(View.VISIBLE);
                    deliverdimage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            userRef.child("single_addr").removeValue();
                            userRef.child("items").removeValue();
                            textViewaddr.setText("No Orders Pending");
                            deliverdimage.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /*requestadapter=new FirebaseRecyclerAdapter<requestOrder, requestViewHolder>(requestOrder.class,
                android.R.layout.simple_list_item_1,
                requestViewHolder.class,
                userRef.child("adreses")) {
            @Override
            protected void populateViewHolder(requestViewHolder viewHolder, requestOrder model, int position) {
                viewHolder.textView.setText(model.Adres);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onCLick(View view, int position, boolean isLongCLick) {
                        Intent intent = new Intent(requestList.this, foodList.class);
                        intent.putExtra("restaurant_name", requestadapter.getRef(position).getKey());
                        startActivity(intent);
                    }

            });
            }
        };
        request_recyclerView.setAdapter(requestadapter);*/

        /*userRef.child("requests").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> addresses = null;
                for (DataSnapshot reqd : dataSnapshot.getChildren()){
                    addresses.add((String) reqd.child("Address").getValue());
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        /*userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("requests")) {
                    FirebaseRecyclerAdapter<requestOrder,requestViewHolder> requestadapter=new FirebaseRecyclerAdapter<requestOrder, requestViewHolder>(requestOrder.class,
                            R.layout.requests_row,
                            requestViewHolder.class,
                            userRef.child("requests")) {
                        @Override
                        protected void populateViewHolder(requestViewHolder viewHolder, requestOrder model, int position) {
                            viewHolder.textView.setText(model.address);

                        }
                    };
                    request_recyclerView.setAdapter(requestadapter);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }
}
