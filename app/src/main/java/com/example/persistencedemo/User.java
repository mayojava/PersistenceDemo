package com.example.persistencedemo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String fname;
    public String lname;
    public String email;
    public int age;

    public String toString() {
        return String.format("%s %s %d\n", fname, lname, age);
    }
}
