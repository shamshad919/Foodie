package com.example.shamshad.foodorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class sign_up extends AppCompatActivity implements View.OnClickListener {

    private Button signup;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRe_password;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };


        signup = (Button) findViewById(R.id.signup);
        editTextName = (EditText) findViewById(R.id.name_signup);
        editTextEmail = (EditText) findViewById(R.id.email_signup);
        editTextPassword = (EditText) findViewById(R.id.password_signup);
        editTextRe_password = (EditText) findViewById(R.id.repassword_signup);
        textViewSignin = (TextView) findViewById(R.id.signin_signup);

        signup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }


    public void registerUser() {
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        String re_password = editTextRe_password.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter the name field", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter the email field", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter the pasword field", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(re_password)) {
            Toast.makeText(this, "Enter the confirm password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(re_password)) {
            Toast.makeText(this, "Password is not match", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering.....");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if Registration is not completed then Toast will work
                        if (!task.isSuccessful()) {
                            Toast.makeText(sign_up.this, "Registration not Completed",
                                    Toast.LENGTH_SHORT).show();

                        }
                        //if Registration is completed then Toast will work
                        if (task.isSuccessful()) {
                            Toast.makeText(sign_up.this, "Registration Completed",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                String uid = user.getUid();
                                // Write a message to the database
                                User u=new User(editTextEmail.getText().toString(),editTextName.getText().toString(),editTextPassword.getText().toString());
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("user").child(uid);
                                myRef.setValue(u);
                            }


                        }

                    }
                });

    }
    @Override
    public void onClick(View v) {
        if (v == signup) {
            registerUser();
        }
        if (v == textViewSignin) {
            Intent intent = new Intent(getApplicationContext(), sign_in.class);
            startActivity(intent);
        }
    }
}
