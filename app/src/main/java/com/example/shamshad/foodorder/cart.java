package com.example.shamshad.foodorder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class cart extends AppCompatActivity {

    int totalPrice=0;

    private TextView totprice_value;
    private Button place_order;
    private RecyclerView cartlistView;
    FirebaseRecyclerAdapter<food_list_details,cartViewHolder> cartfirebaseadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        getSupportActionBar().hide();

        totprice_value = (TextView) findViewById(R.id.total_price_cart_value);
        cartlistView= (RecyclerView) findViewById(R.id.cartlistview);
        cartlistView.setLayoutManager(new LinearLayoutManager(this));
        cartlistView.setHasFixedSize(true);
        place_order = (Button) findViewById(R.id.place_order_btn);

        FirebaseAuth mAuth =FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        String uid=user.getUid();
        DatabaseReference cartlistref=FirebaseDatabase.getInstance().getReference("user").child(uid).child("cart");
        DatabaseReference foodref=FirebaseDatabase.getInstance().getReference("food_list");


        cartfirebaseadapter=new FirebaseRecyclerAdapter<food_list_details, cartViewHolder>(food_list_details.class,
                R.layout.cartlistrow,
                cartViewHolder.class,
                cartlistref) {
            @Override
            protected void populateViewHolder(cartViewHolder viewHolder, food_list_details model, int position) {
                viewHolder.foodnametextView.setText(model.text);
                viewHolder.quantitytextView.setText(model.quantity);
                int addedprice=Integer.parseInt(model.price)*Integer.parseInt(model.quantity);
                viewHolder.pricetextView.setText(String .valueOf(addedprice));
            }
        };
        cartlistView.setAdapter(cartfirebaseadapter);

        /*String[] blakearray={"blakes"};
        place_order.setOnClickListener(new BlakesClickListener(blakearray,this));*/

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



        /*ArrayList<String> foodid_order = new ArrayList<>();
        ArrayList<String> price_order = new ArrayList<>();
        ArrayList<String> food_names_order = new ArrayList<>();
        ArrayList<String> qty_order = new ArrayList<>();



        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        foodid_order = (ArrayList<String>) args.getSerializable("foodid_order");
        price_order = (ArrayList<String>) args.getSerializable("price_order");
        food_names_order = (ArrayList<String>) args.getSerializable("food_names_order");
        qty_order = (ArrayList<String>) args.getSerializable("qty_order");


        final int itemcount = foodid_order.size();
        String[] foodsincart = new String[itemcount];
        String[] priceincart = new String[itemcount];
        String[] qtys = new String[itemcount];
        qtys = qty_order.toArray(qtys);
        priceincart = price_order.toArray(priceincart);
        foodsincart = food_names_order.toArray(foodsincart);



        /*DatabaseReference foodlistdatabase=FirebaseDatabase.getInstance().getReference("foodlist");
        int i;
        for(i=0;i<itemcount;i++){
            final int finalI = i;
            foodlistdatabase.child(foodid_order.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    foodsincart[finalI]= (String) dataSnapshot.child("text").getValue();

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }*/

        /*int i, totprice = 0;
        for (i = 0; i < itemcount; i++) {
            totprice = totprice + (Integer.parseInt(priceincart[i]));
        }
        totprice_value.setText(String.valueOf(totprice));*/





        /*final Context context=this;

        cartlistref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int itemcount = (int) dataSnapshot.getChildrenCount();

                String[] foodsincart=new String[itemcount];
                String[] priceincart=new String[itemcount];
                String[] qtys=new String[itemcount];

                int i=0;
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    foodsincart[i]= String.valueOf(snap.child("foodid").getValue());
                    priceincart[i]= String.valueOf(snap.child("price").getValue());
                    qtys[i]= String.valueOf(snap.child("quantity").getValue())
                    i++;
                }

                ListAdapter mycartlistadapter = new cartlistadapter(context, foodsincart, priceincart, qtys);
                cartlistview.setAdapter(mycartlistadapter);

                place_order.setOnClickListener(new BlakesClickListener(priceincart,context));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/




    }


    public class BlakesClickListener implements View.OnClickListener {

        String[] priceincart;
        Context context;

        public BlakesClickListener(String[] priceincart,Context context) {
            this.priceincart = priceincart;
            this.context=context;
        }

        @Override
        public void onClick(View view) {
            if (view == place_order) {
                Toast.makeText(context,"YAYA"+priceincart[0],Toast.LENGTH_SHORT).show();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final DatabaseReference orderref = FirebaseDatabase.getInstance().getReference("requests").child(user.getUid());
                orderref.child("name").setValue(priceincart[0]);


            }
        }

    }
}


