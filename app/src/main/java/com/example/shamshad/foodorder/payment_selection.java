package com.example.shamshad.foodorder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import static android.R.attr.process;
import static com.example.shamshad.foodorder.R.id.total_price;

public class payment_selection extends AppCompatActivity {

    private Button paypal;
    public static  final int PAYPAL_REQUEST_CODE=7171;
    private static PayPalConfiguration Config=new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)//Use sandbox
            .clientId(config.PAYPAL_CLIENT_ID);

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }
    private String total_price="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_selection);
        getSupportActionBar().hide();

        //start paypal service
        Intent intent=new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,Config);
        startService(intent);
        paypal= (Button) findViewById(R.id.pay_with_paypal);
        total_price =getIntent().getStringExtra("Amount");
        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processpayment();
            }
        });
    }

    private void processpayment() {
        PayPalPayment paypalpayment=new PayPalPayment((new BigDecimal(String.valueOf(total_price))),"USD", "Payment",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent=new Intent(payment_selection.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,Config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,paypalpayment);
        startActivity(intent);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PAYPAL_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                PaymentConfirmation confirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    try{
                        String  paymentdetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this,PaymentDetails.class)
                                .putExtra("PaymentDetails",paymentdetails)
                                .putExtra("PaymentAmount",total_price));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(resultCode== Activity.RESULT_CANCELED){
                Toast.makeText(this,"Cancelled",Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode==PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT).show();;
        }
    }
}
