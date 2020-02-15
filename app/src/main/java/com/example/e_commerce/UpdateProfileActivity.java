 package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_commerce.models.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

 public class UpdateProfileActivity extends AppCompatActivity {

     private EditText nameEt, phoneEt, mailEt, addressEt;
     private Button updateProfileBtn;
     private ProgressDialog progressDialog;

     private DatabaseReference profileReference;
     private FirebaseAuth firebaseAuth;
     String currentUserId;


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

        progressDialog = new ProgressDialog(this);


        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

    }

     private void updateUserProfile() {
        String name = nameEt.getText().toString().trim();
        String phone = phoneEt.getText().toString().trim();
        String mail = mailEt.getText().toString().trim();
        String address = addressEt.getText().toString().trim();

         if (name.equals("")) {
             nameEt.setError("Enter your name");
             nameEt.requestFocus();
         } else if (phone.equals("")) {
             phoneEt.setError("Enter your phone number");
             phoneEt.requestFocus();
         } else if (mail.equals("")) {
             mailEt.setError("Enter your main");
             mailEt.requestFocus();
         } else if (address.equals("")) {
             addressEt.setError("Enter your address");
             addressEt.requestFocus();
         } else {
             showProgressDialog();
             UserProfile userProfile = new UserProfile(name, phone, mail, address);

             currentUserId = firebaseAuth.getCurrentUser().getUid();
             profileReference.child(currentUserId).setValue(userProfile)
                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()) {
                                 progressDialog.dismiss();
                                 Toast.makeText(UpdateProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                                 startActivity(new Intent(UpdateProfileActivity.this, MainActivity.class));
                             } else {
                                 progressDialog.dismiss();
                                 Toast.makeText(UpdateProfileActivity.this, "Error While Updating profile", Toast.LENGTH_SHORT).show();
                                 Log.e("profileUpdateError", task.getException().toString());
                             }
                         }
                     });
         }
     }

     private void showProgressDialog() {
         progressDialog.setTitle("Login in process....");
         progressDialog.setMessage("Wait until finished...");
         progressDialog.setCanceledOnTouchOutside(true);
         progressDialog.show();
     }
 }


























