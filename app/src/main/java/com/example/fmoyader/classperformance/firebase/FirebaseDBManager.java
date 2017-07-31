package com.example.fmoyader.classperformance.firebase;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmoyader on 31/7/17.
 */

public class FirebaseDBManager {

    public static final String URL_FIREBASE = "https://classperformance-56191.firebaseio.com/";
    private static final Uri URI_SCHEME = Uri.parse(URL_FIREBASE);
    private DatabaseReference reference;
    private List<FirebaseObserver> observers;

    public FirebaseDBManager(String path) {
        DatabaseReference reference = FirebaseDatabase.getInstance(URI_SCHEME.toString()).getReference();
        this.reference = reference.child(path);
    }

    public void addObserver(FirebaseObserver observer) {
        if (observers == null) { observers = new ArrayList<>(); }
        observers.add(observer);
    }

    public void query(String where, String value, final Class<? extends FirebaseObservable> responseClass) {
        Query query = reference.orderByChild(where).equalTo(value);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<FirebaseObservable> results = mapResponse(dataSnapshot, responseClass);
                post(results);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void post(List<FirebaseObservable> results) {
        for (FirebaseObserver observer : observers) {
            observer.onResult(results);
        }
    }

    private List<FirebaseObservable> mapResponse(DataSnapshot dataSnapshot, Class<? extends FirebaseObservable> responseClass) {
        if (dataSnapshot.exists()) {
            List<FirebaseObservable> results = new ArrayList<>();
            for (DataSnapshot value : dataSnapshot.getChildren()) {
                try {
                    FirebaseObservable result = responseClass.newInstance();
                    result.initialize(value);
                    results.add(result);
                } catch (Exception exception) {
                    Log.d("","");
                }
            }

            return results;
        }

        return null;
    }
}
