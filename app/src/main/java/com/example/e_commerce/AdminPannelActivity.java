package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;

public class AdminPannelActivity extends AppCompatActivity {

    private Button allProductBtn, addProductBtn, orderBtn;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pannel);

        firebaseAuth = FirebaseAuth.getInstance();

        allProductBtn = findViewById(R.id.all_products_btn_id);
        addProductBtn = findViewById(R.id.add_product_btn_id);
        orderBtn = findViewById(R.id.orders_btn_id);
        toolbar = findViewById(R.id.admn_toolbar_id);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin Pannel");

        allProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPannelActivity.this, AllProductsActivity.class));
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPannelActivity.this, AddANewProductActivity.class));
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPannelActivity.this, OrdersInAdminSideActivity.class));
            }
        });

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
                return true;
            case R.id.admin_menu_setting_id:
                //Todo
                return true;
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
