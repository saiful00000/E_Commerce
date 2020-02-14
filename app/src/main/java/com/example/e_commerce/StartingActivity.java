package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartingActivity extends AppCompatActivity {

    private Button loginButton, createAcButton, skipButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);



        // initialize fields
        loginButton = findViewById(R.id.sign_in_btn_id);
        createAcButton = findViewById(R.id.create_account_btn_id);
        skipButton = findViewById(R.id.skip_btn_id);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartingActivity.this, LoginActivity.class));
            }
        });

        createAcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartingActivity.this, RegisterActivity.class));
            }
        });

        skipButton.setVisibility(View.INVISIBLE);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartingActivity.this, MainActivity.class));
            }
        });

    }
}
