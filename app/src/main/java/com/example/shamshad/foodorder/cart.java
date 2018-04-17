package com.example.shamshad.foodorder;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class cart extends AppCompatActivity implements View.OnClickListener{

    int totalPrice=0;
    private Context context=cart.this;
    private TextView totprice_value;
    private Button place_order;
    private RecyclerView cartlistView;
    private BottomNavigationView bottomNavigationView;
    FirebaseRecyclerAdapter<food_list_details,cartViewHolder> cartfirebaseadapter;
    int quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        getSupportActionBar().hide();

        bottomNavigationView= (BottomNavigationView) findViewById(R.id.navigation_view);
        totprice_value = (TextView) findViewById(R.id.total_price_cart_value);
        cartlistView= (RecyclerView) findViewById(R.id.cartlistview);
        cartlistView.setLayoutManager(new LinearLayoutManager(this));
        cartlistView.setHasFixedSize(true);
        place_order = (Button) findViewById(R.id.place_order_btn);

        place_order.setOnClickListener(this);

        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);
        navigationview_helper.enableNavigationView(context,bottomNavigationView);

        FirebaseAuth mAuth =FirebaseAuth.getInstance();
        final FirebaseUser user=mAuth.getCurrentUser();
        String uid=user.getUid();
        DatabaseReference cartlistref=FirebaseDatabase.getInstance().getReference("user").child(uid).child("cart");
        DatabaseReference foodref=FirebaseDatabase.getInstance().getReference("food_list");


        cartfirebaseadapter=new FirebaseRecyclerAdapter<food_list_details, cartViewHolder>(food_list_details.class,
                R.layout.cartlistrow,
                cartViewHolder.class,
                cartlistref) {
            @Override
            protected void populateViewHolder(cartViewHolder viewHolder, food_list_details model, final int position) {
                viewHolder.foodnametextView.setText(model.text);
                viewHolder.quantitytextView.setText(model.quantity);
                int addedprice=Integer.parseInt(model.price)*Integer.parseInt(model.quantity);
                viewHolder.pricetextView.setText(String .valueOf(addedprice));
                viewHolder.cart_delete_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference mRef=FirebaseDatabase.getInstance().getReference("user").child(user.getUid()).child("cart").child(cartfirebaseadapter.getRef(position).getKey());
                        mRef.removeValue();
                    }
                });
            }
        };
        cartlistView.setAdapter(cartfirebaseadapter);


        cartlistref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    food_list_details ordr=postSnapshot.getValue(food_list_details.class);
                    int qty= Integer.parseInt(ordr.quantity);
                    int price= Integer.parseInt(ordr.price);
                    int addedprice=qty*price;
                    totalPrice=totalPrice+addedprice;

                }
                totprice_value.setText(String.valueOf(totalPrice));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v==place_order){
           Intent intent=new Intent(cart.this,address_selection.class);
            intent.putExtra("Total",totprice_value.getText());
            intent.putExtra("Quantity",quantity);
            startActivity(intent);
        }
    }

    public static class cartViewHolder extends RecyclerView.ViewHolder {
        ImageView cart_delete_button;
        TextView foodnametextView;
        TextView quantitytextView;
        TextView pricetextView;
        public cartViewHolder(View itemView) {
            super(itemView);
            foodnametextView=itemView.findViewById(R.id.food_in_cartlist);
            quantitytextView=itemView.findViewById(R.id.qty_textview);
            pricetextView=itemView.findViewById(R.id.price_in_cartlist);
            cart_delete_button=itemView.findViewById(R.id.cart_delete_button);
        }
    }
}


