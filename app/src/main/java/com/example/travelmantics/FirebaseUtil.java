package com.example.travelmantics;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseUtil {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    public static ArrayList<TravelDeal> mDeals;
    private static FirebaseUtil firebaseUtil;

    private FirebaseUtil() {
    }

    public static void openFbReference(String ref) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDeals = new ArrayList<>();
        }
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }
}