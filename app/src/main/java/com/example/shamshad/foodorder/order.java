package com.example.shamshad.foodorder;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.System.load;

public class order extends AppCompatActivity {

    private Context context=order.this;
    private DatabaseReference mRef;
    private RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    private FirebaseRecyclerAdapter<food_list_details, order_viewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        getSupportActionBar().hide();

        bottomNavigationView= (BottomNavigationView) findViewById(R.id.navigation_view);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(2);
        menuItem.setChecked(true);
        navigationview_helper.enableNavigationView(context,bottomNavigationView);

        int isReq = 1;
        if (getIntent() != null) {
            isReq = getIntent().getIntExtra("isRequest",1);
        }

        if(isReq==0) {
            recyclerView = (RecyclerView) findViewById(R.id.recyclerview_order);
            recyclerView.hasFixedSize();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mRef = FirebaseDatabase.getInstance().getReference("user").child(user.getUid()).child("order");
            adapter = new FirebaseRecyclerAdapter<food_list_details, order_viewHolder>(food_list_details.class,
                    R.layout.order_listrow,
                    order_viewHolder.class,
                    mRef) {
                @Override
                protected void populateViewHolder(order_viewHolder viewHolder, food_list_details model, int position) {
                    viewHolder.textView.setText(model.text);
                    viewHolder.priceview.setText(model.price);
                    viewHolder.textView2.setText(model.getQuantity());
                }
            };

            recyclerView.setAdapter(adapter);

        }
        if(isReq==1){
            recyclerView = (RecyclerView) findViewById(R.id.recyclerview_order);
            recyclerView.hasFixedSize();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mRef = FirebaseDatabase.getInstance().getReference("user").child(user.getUid()).child("items");
            adapter = new FirebaseRecyclerAdapter<food_list_details, order_viewHolder>(food_list_details.class,
                    R.layout.order_listrow,
                    order_viewHolder.class,
                    mRef) {
                @Override
                protected void populateViewHolder(order_viewHolder viewHolder, food_list_details model, int position) {
                    viewHolder.textView.setText(model.text);
                    viewHolder.priceview.setText(model.price);
                    viewHolder.textView2.setText(model.getQuantity());
                }
            };

            recyclerView.setAdapter(adapter);
        }

    }
}
