package com.firefistace.shamshad.foodorder;

import android.content.Context;
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

    private Button address;
    private Button signout;
    private Button cart;
    private Button order;
    private TextView name;
    private TextView email;
    private Button requests;

    private Context context=account_details.this;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        getSupportActionBar().hide();

        address= (Button) findViewById(R.id.account_details_address);
        signout= (Button) findViewById(R.id.account_details_signout);
        cart= (Button) findViewById(R.id.account_details_cart);
        name= (TextView) findViewById(R.id.account_details_name);
        email= (TextView) findViewById(R.id.account_details_email);
        order= (Button) findViewById(R.id.account_details_order);
        requests= (Button) findViewById(R.id.account_details_requests);

        signout.setOnClickListener(this);
        cart.setOnClickListener(this);
        address.setOnClickListener(this);
        order.setOnClickListener(this);
        requests.setOnClickListener(this);

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
        navigationview_helper.enableNavigationView(context,bottomNavigationView);

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
        if(v==address){
            startActivity(new Intent(account_details.this,address.class));
        }
        if(v==order){
            Intent intent=new Intent(account_details.this,order.class);
            intent.putExtra("isRequest",0);
            startActivity(intent);
        }
        if(v==requests){
            startActivity(new Intent(account_details.this,requestList.class));
        }
    }

    private void signout() {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
    }
}
