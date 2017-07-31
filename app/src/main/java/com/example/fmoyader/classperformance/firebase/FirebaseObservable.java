package com.example.fmoyader.classperformance.firebase;

import com.google.firebase.database.DataSnapshot;

import java.util.Map;

/**
 * Created by fmoyader on 31/7/17.
 */

public interface FirebaseObservable {
    Map<String, Object> toMap();
    void initialize(DataSnapshot dataSnapshot);
}
