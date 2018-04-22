package com.example.shamshad.foodorder.Services;

import android.util.Log;

import com.example.shamshad.foodorder.Common.Common;
import com.example.shamshad.foodorder.Model.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;



/**
 * Created by Zoro on 21-Apr-18.
 */

public class MyFirebaseInstanceId extends FirebaseInstanceIdService {

    private String TAG ="tagidon";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        if(FirebaseAuth.getInstance().getCurrentUser().getUid()!=null)
        updateTokenToFirebase(refreshedToken);
    }

    private void updateTokenToFirebase(String refreshedToken) {
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference tokenref=db.getReference("Tokens");
        Token token =new Token(refreshedToken,false);
        tokenref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);

    }


}
