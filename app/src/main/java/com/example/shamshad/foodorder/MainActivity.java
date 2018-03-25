package com.example.shamshad.foodorder;

import android.content.Context;
import android.content.Intent;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.name;
import static android.R.attr.start;
import static android.R.attr.text;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signin;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        signin= (Button) findViewById(R.id.signin_main);
        signup=(TextView)findViewById(R.id.signup_main);

        signin.setOnClickListener(this);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==signin){
            startActivity(new Intent(this,sign_in.class));
        }
        if(v==signup){
            startActivity(new Intent(this,sign_up.class));
        }
    }
}
