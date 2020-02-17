package com.example.persistencedemo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {User.class})
public abstract class AppDatabase extends RoomDatabase {
    abstract UserDao userDao();
}
