package com.example.shamshad.foodorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class sign_in extends AppCompatActivity  implements View.OnClickListener{

    private Button signin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView signup;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        progressDialog=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        signin=(Button)findViewById(R.id.signin1);
        editTextEmail=(EditText)findViewById(R.id.email_signin);
        editTextPassword=(EditText)findViewById(R.id.password_signin);
        signup=(TextView) findViewById(R.id.create_signin);

        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    public void userLogin(){
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email field is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Loging in..");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(sign_in.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(sign_in.this,foodlist.class));
                }
                if(!task.isSuccessful()){
                    Toast.makeText(sign_in.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==signin){
            userLogin();
        }
        if(v==signup){
            startActivity(new Intent(this,sign_up.class));
        }

    }
}
