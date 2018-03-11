package com.example.shamshad.foodorder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class account_details extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        getSupportActionBar().hide();



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
}
