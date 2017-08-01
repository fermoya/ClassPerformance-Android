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

    public <T extends FirebaseObservable> void query(String where, String value, final Class<T> responseClass) {
        Query query = reference.orderByChild(where).equalTo(value);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<T> results = mapResponse(dataSnapshot, responseClass);
                post(results);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private <T extends FirebaseObservable> void post(List<T> results) {
        for (FirebaseObserver observer : observers) {
            observer.onResult(results);
        }
    }

    private <T extends FirebaseObservable> List<T> mapResponse(DataSnapshot dataSnapshot, Class<T> responseClass) {
        if (dataSnapshot.exists()) {
            List<T> results = new ArrayList<>();
            for (DataSnapshot value : dataSnapshot.getChildren()) {
                try {
                    T result = responseClass.newInstance();
                    result.initialize(value);
                    results.add(result);
                } catch (Exception exception) { }
            }

            return results;
        }

        return null;
    }

    public <T extends FirebaseObservable> void save(T t) {
        reference.child(t.getChildName()).setValue(t);
    }
}
