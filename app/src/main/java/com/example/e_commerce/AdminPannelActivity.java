package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminPannelActivity extends AppCompatActivity {

    private Button allProductBtn, addProductBtn;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pannel);

        firebaseAuth = FirebaseAuth.getInstance();

        allProductBtn = findViewById(R.id.all_products_btn_id);
        addProductBtn = findViewById(R.id.add_product_btn_id);
        toolbar = findViewById(R.id.admn_toolbar_id);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin Pannel");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_pannel_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.admin_menu_log_out_id:
                userSignIn();
            case R.id.admin_menu_setting_id:
                //Todo
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void userSignIn() {
        firebaseAuth.signOut();
        startActivity(new Intent(AdminPannelActivity.this, StartingActivity.class));
        finish();
    }
}
