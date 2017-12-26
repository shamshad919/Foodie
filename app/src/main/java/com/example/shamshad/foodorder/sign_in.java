package com.example.shamshad.foodorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class sign_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
    }

    public void signin(View view) {
        Toast toast=Toast.makeText(getApplicationContext(),"Created successfully", Toast.LENGTH_LONG);
        toast .show();
    }
}
