package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.models.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "register";

    private EditText emailEditText, passwordEditText;
    private Button createAccountButton;
    private TextView goLoginActivityTv;

    private ProgressDialog progressDialog;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        // inititalize firebase instances
        firebaseAuth = FirebaseAuth.getInstance();
        rootReference = FirebaseDatabase.getInstance().getReference().child("Users");

        // initialize views
        emailEditText = findViewById(R.id.email_et_id);
        passwordEditText = findViewById(R.id.password_et_id);
        createAccountButton = findViewById(R.id.create_account_btn_id);
        goLoginActivityTv = findViewById(R.id.go_to_login_activity_tv_id);


        progressDialog = new ProgressDialog(this);

        goLoginActivityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });


    }

    private void createAccount() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            emailEditText.setError("Enter your email");
            emailEditText.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Enter your password");
            passwordEditText.requestFocus();
        } else {
            showProgressDialog();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                UserProfile userProfile = new UserProfile("","","","");
                                String currentUserId = firebaseAuth.getCurrentUser().getUid();
                                rootReference.child(currentUserId).setValue(userProfile);
                                Log.d(TAG, "createUserWithEmail: success");
                                progressDialog.dismiss();
                                sendUserToMainActivity();
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void sendUserToMainActivity() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showProgressDialog() {
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage("Wait untill finish....");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

}
