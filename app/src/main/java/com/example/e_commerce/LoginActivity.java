package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView createAcButton;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //initialize firebase instances
        firebaseAuth = FirebaseAuth.getInstance();


        //initialize fields
        emailEditText = findViewById(R.id.email_et_id);
        passwordEditText = findViewById(R.id.password_et_id);
        loginButton = findViewById(R.id.login_btn_id);
        createAcButton = findViewById(R.id.create_account_btn_id);

        progressDialog = new ProgressDialog(this);



        createAcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        sentUserToMainActivity();
                                    } else {
                                        String errorMsg = Objects.requireNonNull(task.getException()).toString();
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Error : "+ errorMsg, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    private void showProgressDialog() {
        progressDialog.setTitle("Login in process....");
        progressDialog.setMessage("Wait until finished...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }


    private void sentUserToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

}
