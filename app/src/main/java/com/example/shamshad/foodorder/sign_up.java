package com.example.shamshad.foodorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class sign_up extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
    }

    public void sign_up(View view) {
        Toast t=Toast.makeText(getApplicationContext(),"Created successfully", Toast.LENGTH_LONG);
        t .show();
    }
}
