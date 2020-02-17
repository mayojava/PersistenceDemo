package com.example.persistencedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";

    private Disposable disposable;

    AppDatabase db;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDB();

        final EditText fname = findViewById(R.id.editTextFirstname);
        final EditText lname = findViewById(R.id.editTextLastname);
        final EditText emailtext = findViewById(R.id.editTextEmail);
        final EditText ageText = findViewById(R.id.editTextAge);


        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser(
                        fname.getText().toString(),
                        lname.getText().toString(),
                        emailtext.getText().toString(),
                        ageText.getText().toString());

                fname.setText("");

            }
        });

        fetchUsers();
    }

    private void fetchUsers() {
        disposable = userDao.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        Toast.makeText(getBaseContext(), users.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void initDB() {
        db = ((App) getApplication()).getDB();
        userDao = db.userDao();
    }

    private void saveUser(final String fname, String lname, String email, String age) {
        int intAge = Integer.parseInt(age);
        User user = new User();
        user.fname = fname; user.lname = lname; user.email = email; user.age = intAge;

        disposable = userDao.saveUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Toast.makeText(getBaseContext(), "Inserted Id: " + aLong, Toast.LENGTH_LONG).show();

                    }
                });

    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}
