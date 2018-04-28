package com.firefistace.shamshad.foodorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class paymentDetails_cod extends AppCompatActivity {

    private Button home;
    private TextView sample;
    private DatabaseReference databaseReference;
    private DatabaseReference order;
    private DatabaseReference restaurant_rid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_details_cod);
        getSupportActionBar().hide();

        sample= (TextView) findViewById(R.id.order_successful);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        final String uid=user.getUid();
        final Object[] res = new Object[1];
        final String[] restaurant_name = new String[1];

        final String[] restid = {null};
        DatabaseReference db=FirebaseDatabase.getInstance().getReference();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String restname= (String) dataSnapshot.child("user").child(uid).child("cart").child("restaurant").getValue();
                String address= (String) dataSnapshot.child("user").child(uid).child("cart").child("address").getValue();
                String rid= (String) dataSnapshot.child("restaurants").child(restname).child("rid").getValue();
                //final DatabaseReference rest=FirebaseDatabase.getInstance().getReference("user").child(rid).child("requests").push();
                //DatabaseReference addr=FirebaseDatabase.getInstance().getReference("user").child(rid).child("adreses").push();
                DatabaseReference singleaddr=FirebaseDatabase.getInstance().getReference("user").child(rid);
                singleaddr.child("single_addr").setValue(address);
                //rest.child("Adres").setValue(address);
                //addr.child("Adres").setValue(address);
                //addr.child("pushidkey").setValue(rest.getKey());

                /*rest.child("items").setValue(dataSnapshot.child("user").child(uid).child("cart").child("items").getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            System.out.println("Success");
                        }

                    }
                });*/
                singleaddr.child("items").setValue(dataSnapshot.child("user").child(uid).child("cart").child("items").getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            System.out.println("Success");
                        }

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference= FirebaseDatabase.getInstance().getReference("user").child(user.getUid()).child("cart").child("items");
        order=FirebaseDatabase.getInstance().getReference("user").child(user.getUid() ).child("order");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                order.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            System.out.println("Success");

                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        home= (Button) findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.removeValue();
                startActivity(new Intent(paymentDetails_cod.this,restaurant.class));
            }
        });
    }
}
