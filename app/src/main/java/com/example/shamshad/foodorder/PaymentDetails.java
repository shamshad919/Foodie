package com.example.shamshad.foodorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {

    TextView textId,textAmount,textStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_details);

        textId= (TextView) findViewById(R.id.textId);
        textAmount= (TextView) findViewById(R.id.textAmount);
        textStatus= (TextView) findViewById(R.id.textStatus);


        //Get Intent
        Intent intent=getIntent();

        try{
            JSONObject jsonObject=new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            textId.setText(response.getString("id"));
            textAmount.setText(response.getString("id"));
            textStatus.setText(response.getString(String.format("$%s",paymentAmount)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
