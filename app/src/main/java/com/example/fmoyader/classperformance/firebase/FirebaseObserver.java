package com.example.fmoyader.classperformance.firebase;

import java.util.List;

/**
 * Created by fmoyader on 31/7/17.
 */

public interface FirebaseObserver <T extends FirebaseObservable> {
    void onResult(List<T> results);
}
