package com.example.persistencedemo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface UserDao {
    @Insert
    Single<Long> saveUser(User user);

    @Query("SELECT * FROM users")
    Flowable<List<User>> getUsers();

    @Delete
    Completable deleteUser(User user);
}
