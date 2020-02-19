package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize all firebase instances
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        // initialize views
        toolbar = findViewById(R.id.toolbarId);
        drawerLayout = findViewById(R.id.drawer_layout_id);
        navigationView = findViewById(R.id.nav_view_id);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("E Commerce");

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.nav_drawer_open,
                R.string.nav_drawer_close
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // check user existence
        checkCurrentUserLogin();

    }

    private void checkCurrentUserLogin() {
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, StartingActivity.class));
        } else {
            verifyUserExistance();
        }
    }

    private void verifyUserExistance() {
        String currentUserId = firebaseAuth.getCurrentUser().getUid();

        if (currentUserId.equals("dXjBngp0PkNiMedYhOU7v5mFxhK2")) {
            // check if the user is a admin or not
            startActivity(new Intent(MainActivity.this, AdminPannelActivity.class));
            finish();
            return;
        } else {
            rootReference = FirebaseDatabase.getInstance().getReference();
            rootReference.child("Users").child(currentUserId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // if profile information is empty then send user to update profile activity
                            if ((!dataSnapshot.child("name").getValue().equals(""))) {
                                Toast.makeText(MainActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                            } else {
                                sendUserToUpdateProfileActivity();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }

    }

    private void sendUserToUpdateProfileActivity() {
        startActivity(new Intent(MainActivity.this, UpdateProfileActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_log_out_id:
                userSignOut();
            case R.id.menu_setting_id:
                //TODO setting
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void userSignOut() {
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, StartingActivity.class));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.d_menu_cart_id:
                toast("cart");
                return true;
            case R.id.d_menu_category_id:
                toast("category");
                return true;
            case R.id.d_menu_order_id:
                toast("order");
                return true;
            case R.id.d_menu_settings_id:
                toast("settings");
                return true;
            case R.id.d_menu_logout_id:
                toast("logout");
                return true;
            default:
                return false;
        }

    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
