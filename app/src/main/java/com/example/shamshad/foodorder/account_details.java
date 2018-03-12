package com.example.shamshad.foodorder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class account_details extends AppCompatActivity implements View.OnClickListener{

    private Button signout;
    private Button cart;
    private TextView name;
    private TextView email;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        getSupportActionBar().hide();

        signout= (Button) findViewById(R.id.account_details_signout);
        cart= (Button) findViewById(R.id.account_details_cart);
        name= (TextView) findViewById(R.id.account_details_name);
        email= (TextView) findViewById(R.id.account_details_email);

        signout.setOnClickListener(this);
        cart.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    startActivity(new Intent(account_details.this,MainActivity.class));
                }
            }
        };

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());

        BottomNavigationView bottomNavigationView= (BottomNavigationView) findViewById(R.id.navigation_view);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_explore :
                        startActivity(new Intent(account_details.this,restaurant.class));
                        return true;
                    case R.id.nav_cart:
                        startActivity(new Intent(account_details.this,cart.class));
                        return true;
                    case R.id.nav_acccount:

                        return true;
                    default:
                        return  true;
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
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==signout){
            signout();
        }
        if(v==cart){
            startActivity(new Intent(account_details.this,cart.class));
        }
    }

    private void signout() {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
    }
}
