package com.example.shamshad.foodorder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.name;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void signin(View view) {
        Intent intent = new Intent(getApplicationContext(), sign_in.class);
        startActivity(intent);
    }

    public void signup(View view) {
        Intent intent=new Intent(getApplicationContext(),sign_up.class);
        startActivity(intent);
    }



}
