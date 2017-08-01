package com.example.fmoyader.classperformance.model;

import com.example.fmoyader.classperformance.firebase.FirebaseObservable;
import com.google.firebase.database.DataSnapshot;

import java.util.Map;

/**
 * Created by fmoyader on 31/7/17.
 */

public class Course implements FirebaseObservable {

    private String name;
    private String description;
    private String user;

    public Course() { }

    public Course(String name, String description, String user) {
        this.name = name;
        this.description = description;
        this.user = user;
    }

    @Override
    public Map<String, Object> toMap() {
        return null;
    }

    @Override
    public void initialize(DataSnapshot dataSnapshot) {
        this.name = (String) dataSnapshot.child("name").getValue();
        this.description = (String) dataSnapshot.child("description").getValue();
        this.user = (String) dataSnapshot.child("user").getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
