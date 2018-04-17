package com.example.shamshad.foodorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class order_details extends AppCompatActivity {

    private TextView address_order;
    private Button place_order;
    private TextView total;
    private TextView quantity;
    private DatabaseReference cart_count;

    private static  final int PAYPAL_REQUEST_CODE=7171;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        getSupportActionBar().hide();

        String delivery_address = getIntent().getStringExtra("delivery address");
        String total_price=getIntent().getStringExtra("Total");
        address_order= (TextView) findViewById(R.id.address_orderdetails);
        total= (TextView) findViewById(R.id.total_text_orderdetails);
        quantity= (TextView) findViewById(R.id.quantity_text_orderdetails);
        address_order.setText(delivery_address);
        place_order= (Button) findViewById(R.id.placeorder_orderdetails);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        total.setText(total_price);
        cart_count=FirebaseDatabase.getInstance().getReference("user").child(user.getUid()).child("cart");


    }
}
