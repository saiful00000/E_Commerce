package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {



    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize all firebase instances
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();


        // check user existence
        checkCurrentUserLogin();

    }

    private void checkCurrentUserLogin() {
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, StartingActivity.class));
        } else {
            //TODO is current user loged in
        }
    }
}
