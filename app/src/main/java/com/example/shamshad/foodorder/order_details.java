package com.example.shamshad.foodorder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shamshad.foodorder.Common.Common;
import com.example.shamshad.foodorder.Model.MyResponse;
import com.example.shamshad.foodorder.Model.Notification;
import com.example.shamshad.foodorder.Model.Sender;
import com.example.shamshad.foodorder.Model.Token;
import com.example.shamshad.foodorder.Remote.APIService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.RemoteMessage;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.data;
import static android.R.attr.process;
import static com.example.shamshad.foodorder.R.id.add;
import static com.example.shamshad.foodorder.R.id.start;
import static com.example.shamshad.foodorder.R.id.total_price;
import static com.example.shamshad.foodorder.R.id.total_price_cart_value;
import static com.example.shamshad.foodorder.payment_selection.PAYPAL_REQUEST_CODE;
import static com.paypal.android.sdk.fb.c;

public class order_details extends AppCompatActivity {

    private TextView address_order;
    private Button place_order;
    private TextView total;

    private DatabaseReference cart_count;
    String total_price;


    APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        getSupportActionBar().hide();

        mService= Common.getFCMClient();

        final String delivery_address = getIntent().getStringExtra("delivery address");

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference address=FirebaseDatabase.getInstance().getReference("user").child(user.getUid()).child("cart");
        address.child("address").setValue(delivery_address);
        total_price=getIntent().getStringExtra("Total");
        address_order= (TextView) findViewById(R.id.address_orderdetails);
        total= (TextView) findViewById(R.id.total_text_orderdetails);

        address_order.setText(delivery_address);
        place_order= (Button) findViewById(R.id.placeorder_orderdetails);
        total.setText("Rs "+total_price);
        cart_count=FirebaseDatabase.getInstance().getReference("user").child(user.getUid()).child("cart");
        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String localkey="Localkey";
                sendOrderStatusToUser(localkey);

               Intent intent=new Intent(order_details.this,payment_selection.class);
               intent.putExtra("Amount",total_price);
                intent.putExtra("delivery address",delivery_address);
               startActivity(intent);
            }
        });


    }

    private void sendOrderStatusToUser(final String key) {
        DatabaseReference tokenref=FirebaseDatabase.getInstance().getReference("Tokens");
        tokenref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Token token=postSnapshot.getValue(Token.class);
                    Notification notification=new Notification("ShaunDEV","New order "+key);
                    Sender content=new Sender(token.getToken(),notification);
                    mService.sendNotification(content)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if(response.body().success==1)
                                    {
                                        Toast.makeText(order_details.this,"Order updated",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(order_details.this,"failed to send notification!!!",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                   Log.e("Error noti",t.getMessage());
                                }
                            });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
