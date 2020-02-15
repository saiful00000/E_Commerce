 package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

 public class UpdateProfileActivity extends AppCompatActivity {

     private EditText nameEt, phoneEt, mailEt, addressEt;
     private Button updateProfileBtn;

     private DatabaseReference profileReference;
     private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        profileReference = FirebaseDatabase.getInstance().getReference().child("Users");

        // initialize fields
        nameEt = findViewById(R.id.name_et_id);
        phoneEt = findViewById(R.id.phone_et_id);
        mailEt = findViewById(R.id.mail_et_id);
        addressEt = findViewById(R.id.address_et_id);
        updateProfileBtn = findViewById(R.id.update_btn_id);


        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

    }

     private void updateUserProfile() {

     }
 }
