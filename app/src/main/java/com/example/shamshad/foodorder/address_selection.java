package com.example.shamshad.foodorder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class address_selection extends AppCompatActivity implements View.OnClickListener {

    private TextView address_select;
    private TextView continue_address_selection;
    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    BottomNavigationView bottomNavigationView;
    FirebaseRecyclerAdapter<addressDetails, addressViewHolder> firebaseRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_selection);
        getSupportActionBar().hide();

       final String total= getIntent().getStringExtra("Total");
        final String quantity=getIntent().getStringExtra("Quantity");
        continue_address_selection = (Button) findViewById(R.id.addnewaddress_selection);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_address_selection);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        continue_address_selection.setOnClickListener(this);

        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("user").child(user.getUid()).child("address");


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<addressDetails, addressViewHolder>(addressDetails.class,
                R.layout.address_selection_listrow,
                addressViewHolder.class,
                mRef) {
            @Override
            protected void populateViewHolder(final addressViewHolder viewHolder, final addressDetails model, final int position) {
                viewHolder.textView.setText(model.name);
                viewHolder.deletebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference addref=FirebaseDatabase.getInstance().getReference("user").child(user.getUid()).child("address").child(firebaseRecyclerAdapter.getRef(position).getKey());
                        addref.removeValue();
                    }
                });
                viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(address_selection.this,order_details.class);
                        intent.putExtra("delivery address",model.name);
                        intent.putExtra("Total",total);
                        intent.putExtra("Quantity",quantity);
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onClick(View v) {

        if(v== continue_address_selection){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(address_selection.this);
            alertDialog.setTitle("New Addresss");
            alertDialog.setMessage("Enter New Address");
            final EditText input = new EditText(address_selection.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            alertDialog.setView(input);

            alertDialog.setPositiveButton("ADD",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final String address =((input.getText().toString()));
                            if(!(address.isEmpty())) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                final DatabaseReference cartref = FirebaseDatabase.getInstance().getReference("user").child(user.getUid()).child("address").push();
                                cartref.child("name").setValue(address);
                            }
                        }
                    });

            alertDialog.show();
        }
    }

    /**
     * Created by shamshad on 25/3/18.
     */

    public static class addressDetails {
        String name;
        String image;
        String rating;

        public addressDetails() {
        }

        public addressDetails(String name, String image, String rating) {
            this.name = name;
            this.image = image;
            this.rating = rating;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }
    }

    public static class addressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cardView;
        TextView textView;
        ImageView deletebutton;
        public addressViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.cardview_address_selection);
            deletebutton= (ImageView) itemView.findViewById(R.id.address_selection_delete);
            textView=(TextView) itemView.findViewById(R.id.address_selection_textview_recycler);

        }


        @Override
        public void onClick(View v) {

        }
    }
}
