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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class sign_up extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    private Button signin_google_button;
    private Button signup;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRe_password;
    private TextView textViewSignin;
    private final static int RC_SIGN_IN =2;
    private GoogleApiClient mGoogleApiClient;


    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        getSupportActionBar().hide();


        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    startActivity(new Intent(sign_up.this,restaurant.class));
                }
            }
        };


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        signin_google_button= (Button) findViewById(R.id.signup_google_button);
        signup = (Button) findViewById(R.id.signup);
        editTextName = (EditText) findViewById(R.id.name_signup);
        editTextEmail = (EditText) findViewById(R.id.email_signup);
        editTextPassword = (EditText) findViewById(R.id.password_signup);
        editTextRe_password = (EditText) findViewById(R.id.repassword_signup);
        textViewSignin = (TextView) findViewById(R.id.signin_signup);

        signup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
        signin_google_button.setOnClickListener(this);

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
        if(v==signin_google_button){
            googleSignin();
        }
    }
    private void googleSignin() {
        Intent signInIntent= Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account=result.getSignInAccount();
                Toast.makeText(sign_up.this,"Signup successfully",Toast.LENGTH_LONG).show();
                firebaseAuthWithGoogle(account);
            }
            else{
                Toast.makeText(sign_up.this,"Task unsuccess",Toast.LENGTH_LONG).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            final String uid=user.getUid();
                            final DatabaseReference myRef =FirebaseDatabase.getInstance().getReference("user");
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(uid)){

                                    }
                                    else{
                                        User u=new User(user.getEmail(),user.getDisplayName());
                                        myRef.setValue(u);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG" , "signInWithCredential:failure", task.getException());
                            Toast.makeText(sign_up.this,"Failed",Toast.LENGTH_LONG).show();
                            //updateUI(null);
                        }
                    }
                });
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}
