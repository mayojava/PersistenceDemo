package com.example.persistencedemo;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {
    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(this, AppDatabase.class, "userdb").build();
        }
    }

    public AppDatabase getDB() {
        return appDatabase;
    }
}
