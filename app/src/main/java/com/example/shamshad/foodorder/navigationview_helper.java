package com.example.shamshad.foodorder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

/**
 * Created by shamshad on 12/3/18.
 */

public class navigationview_helper {
    public static void enableNavigationView(final Context context, BottomNavigationView view) {
        view .setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_explore :
                        context.startActivity(new Intent(context,restaurant.class));
                        return true;
                    case R.id.nav_cart:
                        context.startActivity(new Intent(context,cart.class));
                        return true;
                    case R.id.nav_acccount:
                        context.startActivity(new Intent(context,account_details.class));
                        return true;
                    default:
                        return  true;
                }
            }
        });
    }

}
